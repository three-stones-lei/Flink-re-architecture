package org.lei.function;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.retrying.RetrySettings;
import com.google.cloud.bigquery.storage.v1.*;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.Descriptors;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.threeten.bp.Duration;

import javax.annotation.concurrent.GuardedBy;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Phaser;

/**
 * ClassName: BQSinkFunction
 * Package: org.lei.function
 * Description:
 *
 * @Author Lei
 * @Create 27/6/2024 5:31 pm
 * @Version 1.0
 */
public class BQSinkFunctionBatch extends RichSinkFunction<List<String>> {

    private String projectId;
    private String datasetName;
    private String tableName;

    private DataWriter writer;
    BigQueryWriteClient client;
    TableName parentTable;


    @Override
    public void open(Configuration parameters) throws Exception {
        client = BigQueryWriteClient.create();
        parentTable = TableName.of(projectId, datasetName, tableName);

        writer = new DataWriter();

    }

    @Override
    public void close() throws Exception {

    }

    public BQSinkFunctionBatch(String projectId, String datasetName, String tableName) {
        this.projectId = projectId;
        this.datasetName = datasetName;
        this.tableName = tableName;
    }

    @Override
    public void invoke(List<String> value, Context context) throws Exception {
        long offset = 0L;
        try {
            // Write two batches of fake data to the stream, each with 10 JSON records.  Data may be
            // batched up to the maximum request size:
            // https://cloud.google.com/bigquery/quotas#write-api-limits
            // One time initialization.
            writer.initialize(parentTable, client);
            // Create a JSON object that is compatible with the table schema.

            for (String s : value) {
                JSONArray jsonArr = new JSONArray();
                JSONObject jsonObject = new JSONObject(s);
                jsonArr.put(jsonObject);
                writer.append(jsonArr, offset);
                offset++;
            }


        } catch (Exception e) {
            // If the wrapped exception is a StatusRuntimeException, check the state of the operation.
            // If the state is INTERNAL, CANCELLED, or ABORTED, you can retry. For more information, see:
            // https://grpc.github.io/grpc-java/javadoc/io/grpc/StatusRuntimeException.html
            System.out.println("Failed to append records. \n" + e);
            System.out.println("The reocrd is : \n" + value.get((int) offset));
        }

        // Final cleanup for the stream.
        writer.cleanup(client);
        System.out.println("Appended records successfully.");

        // Once all streams are done, if all writes were successful, commit all of them in one request.
        // This example only has the one stream. If any streams failed, their workload may be
        // retried on a new stream, and then only the successful stream should be included in the
        // commit.
        BatchCommitWriteStreamsRequest commitRequest =
                BatchCommitWriteStreamsRequest.newBuilder()
                        .setParent(parentTable.toString())
                        .addWriteStreams(writer.getStreamName())
                        .build();
        BatchCommitWriteStreamsResponse commitResponse = client.batchCommitWriteStreams(commitRequest);
        // If the response does not have a commit time, it means the commit operation failed.
        if (commitResponse.hasCommitTime() == false) {
            for (StorageError err : commitResponse.getStreamErrorsList()) {
                System.out.println(err.getErrorMessage());
            }
            throw new RuntimeException("Error committing the streams");
        }
        System.out.println("Appended and committed records successfully.");
    }

    // A simple wrapper object showing how the stateful stream writer should be used.
    private static class DataWriter {

        private JsonStreamWriter streamWriter;
        // Track the number of in-flight requests to wait for all responses before shutting down.
        private final Phaser inflightRequestCount = new Phaser(1);

        private final Object lock = new Object();

        @GuardedBy("lock")
        private RuntimeException error = null;

        void initialize(TableName parentTable, BigQueryWriteClient client)
                throws IOException, Descriptors.DescriptorValidationException, InterruptedException {
            // Initialize a write stream for the specified table.
            // For more information on WriteStream.Type, see:
            // https://googleapis.dev/java/google-cloud-bigquerystorage/latest/com/google/cloud/bigquery/storage/v1/WriteStream.Type.html
            WriteStream stream = WriteStream.newBuilder().setType(WriteStream.Type.PENDING).build();

            // Configure in-stream automatic retry settings.
            // Error codes that are immediately retried:
            // * ABORTED, UNAVAILABLE, CANCELLED, INTERNAL, DEADLINE_EXCEEDED
            // Error codes that are retried with exponential backoff:
            // * RESOURCE_EXHAUSTED
            RetrySettings retrySettings =
                    RetrySettings.newBuilder()
                            .setInitialRetryDelay(Duration.ofMillis(500))
                            .setRetryDelayMultiplier(1.1)
                            .setMaxAttempts(5)
                            .setMaxRetryDelay(Duration.ofMinutes(1))
                            .build();

            CreateWriteStreamRequest createWriteStreamRequest =
                    CreateWriteStreamRequest.newBuilder()
                            .setParent(parentTable.toString())
                            .setWriteStream(stream)
                            .build();
            WriteStream writeStream = client.createWriteStream(createWriteStreamRequest);

            // Use the JSON stream writer to send records in JSON format.
            // For more information about JsonStreamWriter, see:
            // https://googleapis.dev/java/google-cloud-bigquerystorage/latest/com/google/cloud/bigquery/storage/v1beta2/JsonStreamWriter.html
            streamWriter =
                    JsonStreamWriter.newBuilder(writeStream.getName(), writeStream.getTableSchema())
                            .setRetrySettings(retrySettings)
                            .build();
        }

        public void append(JSONArray data, long offset)
                throws Descriptors.DescriptorValidationException, IOException, ExecutionException {
            synchronized (this.lock) {
                // If earlier appends have failed, we need to reset before continuing.
                if (this.error != null) {
                    throw this.error;
                }
            }
            // Append asynchronously for increased throughput.
            ApiFuture<AppendRowsResponse> future = streamWriter.append(data, offset);
            ApiFutures.addCallback(
                    future, new AppendCompleteCallback(this), MoreExecutors.directExecutor());
            // Increase the count of in-flight requests.
            inflightRequestCount.register();
        }

        public void cleanup(BigQueryWriteClient client) {
            // Wait for all in-flight requests to complete.
            inflightRequestCount.arriveAndAwaitAdvance();

            // Close the connection to the server.
            streamWriter.close();

            // Verify that no error occurred in the stream.
            synchronized (this.lock) {
                if (this.error != null) {
                    throw this.error;
                }
            }

            // Finalize the stream.
            FinalizeWriteStreamResponse finalizeResponse =
                    client.finalizeWriteStream(streamWriter.getStreamName());
            System.out.println("Rows written: " + finalizeResponse.getRowCount());
        }

        public String getStreamName() {
            return streamWriter.getStreamName();
        }

        static class AppendCompleteCallback implements ApiFutureCallback<AppendRowsResponse> {

            private final DataWriter parent;

            public AppendCompleteCallback(DataWriter parent) {
                this.parent = parent;
            }

            public void onSuccess(AppendRowsResponse response) {
                System.out.format("Append %d success\n", response.getAppendResult().getOffset().getValue());
                done();
            }

            public void onFailure(Throwable throwable) {
                synchronized (this.parent.lock) {
                    if (this.parent.error == null) {
                        Exceptions.StorageException storageException = Exceptions.toStorageException(throwable);
                        this.parent.error =
                                (storageException != null) ? storageException : new RuntimeException(throwable);
                    }
                }
                System.out.format("Error: %s\n", throwable.toString());
                done();
            }

            private void done() {
                // Reduce the count of in-flight requests.
                this.parent.inflightRequestCount.arriveAndDeregister();
            }
        }
    }



}
