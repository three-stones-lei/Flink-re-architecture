package org.lei.function;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.core.FixedExecutorProvider;
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
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: BQSinkFunction
 * Package: org.lei.function
 * Description:
 *
 * @Author Lei
 * @Create 27/6/2024 5:31 pm
 * @Version 1.0
 */
public class BQSinkFunctionAtLeastOnce extends RichSinkFunction<String> {

    private String projectId;
    private String datasetName;
    private String tableName;


    @Override
    public void open(Configuration parameters) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    public BQSinkFunctionAtLeastOnce(String projectId, String datasetName, String tableName) {
        this.projectId = projectId;
        this.datasetName = datasetName;
        this.tableName = tableName;
    }


    @Override
    public void invoke(String value, Context context) throws Exception {

        // Write two batches of fake data to the stream, each with 10 JSON records.  Data may be
        // batched up to the maximum request size:
        // https://cloud.google.com/bigquery/quotas#write-api-limits
        TableName parentTable = TableName.of(projectId, datasetName, tableName);
        DataWriter writer = new DataWriter();
        // One time initialization for the worker.
        writer.initialize(parentTable);
        // Create a JSON object that is compatible with the table schema.
        JSONArray jsonArr = new JSONArray();
        JSONObject jsonObject = new JSONObject(value);
        jsonArr.put(jsonObject);
        writer.append(jsonArr);

        // Final cleanup for the stream during worker teardown.
        writer.cleanup();
        System.out.println("Appended records successfully.");
    }

    // A simple wrapper object showing how the stateful stream writer should be used.
    private static class DataWriter {

        private static final int MAX_RECREATE_COUNT = 3;

        private BigQueryWriteClient client;

        // Track the number of in-flight requests to wait for all responses before shutting down.
        private final Phaser inflightRequestCount = new Phaser(1);
        private final Object lock = new Object();
        private JsonStreamWriter streamWriter;

        @GuardedBy("lock")
        private RuntimeException error = null;

        private AtomicInteger recreateCount = new AtomicInteger(0);

        private JsonStreamWriter createStreamWriter(String tableName)
                throws Descriptors.DescriptorValidationException, IOException, InterruptedException {
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

            // Use the JSON stream writer to send records in JSON format. Specify the table name to write
            // to the default stream.
            // For more information about JsonStreamWriter, see:
            // https://googleapis.dev/java/google-cloud-bigquerystorage/latest/com/google/cloud/bigquery/storage/v1/JsonStreamWriter.html
            return JsonStreamWriter.newBuilder(tableName, client)
                    .setExecutorProvider(FixedExecutorProvider.create(Executors.newScheduledThreadPool(100)))
                    .setChannelProvider(
                            BigQueryWriteSettings.defaultGrpcTransportProviderBuilder()
                                    .setKeepAliveTime(org.threeten.bp.Duration.ofMinutes(1))
                                    .setKeepAliveTimeout(org.threeten.bp.Duration.ofMinutes(1))
                                    .setKeepAliveWithoutCalls(true)
                                    .setChannelsPerCpu(2)
                                    .build())
                    .setEnableConnectionPool(true)
                    // If value is missing in json and there is a default value configured on bigquery
                    // column, apply the default value to the missing value field.
                    .setDefaultMissingValueInterpretation(
                            AppendRowsRequest.MissingValueInterpretation.DEFAULT_VALUE)
                    .setRetrySettings(retrySettings)
                    .build();
        }

        public void initialize(TableName parentTable)
                throws Descriptors.DescriptorValidationException, IOException, InterruptedException {
            // Initialize client without settings, internally within stream writer a new client will be
            // created with full settings.
            client = BigQueryWriteClient.create();

            streamWriter = createStreamWriter(parentTable.toString());
        }

        public void append(JSONArray jsonArray)
                throws Descriptors.DescriptorValidationException, IOException, InterruptedException {
            synchronized (this.lock) {
                if (!streamWriter.isUserClosed()
                        && streamWriter.isClosed()
                        && recreateCount.getAndIncrement() < MAX_RECREATE_COUNT) {
                    streamWriter = createStreamWriter(streamWriter.getStreamName());
                    this.error = null;
                }
                // If earlier appends have failed, we need to reset before continuing.
                if (this.error != null) {
                    throw this.error;
                }
            }
            // Append asynchronously for increased throughput.
            ApiFuture<AppendRowsResponse> future = streamWriter.append(jsonArray);
            ApiFutures.addCallback(
                    future, new AppendCompleteCallback(this, jsonArray), MoreExecutors.directExecutor());

            // Increase the count of in-flight requests.
            inflightRequestCount.register();
        }

        public void cleanup() {
            // Wait for all in-flight requests to complete.
            inflightRequestCount.arriveAndAwaitAdvance();

            client.close();
            // Close the connection to the server.
            streamWriter.close();

            // Verify that no error occurred in the stream.
            synchronized (this.lock) {
                if (this.error != null) {
                    throw this.error;
                }
            }
        }

        static class AppendCompleteCallback implements ApiFutureCallback<AppendRowsResponse> {

            private final DataWriter parent;
            private final JSONArray jsonArray;

            public AppendCompleteCallback(DataWriter parent, JSONArray jsonArray) {
                this.parent = parent;
                this.jsonArray = jsonArray;
            }

            public void onSuccess(AppendRowsResponse response) {
                System.out.format("Append success\n");
                this.parent.recreateCount.set(0);
                done();
            }

            public void onFailure(Throwable throwable) {
                if (throwable instanceof Exceptions.AppendSerializationError) {
                    Exceptions.AppendSerializationError ase = (Exceptions.AppendSerializationError) throwable;
                    Map<Integer, String> rowIndexToErrorMessage = ase.getRowIndexToErrorMessage();
                    if (rowIndexToErrorMessage.size() > 0) {
                        // Omit the faulty rows
                        JSONArray dataNew = new JSONArray();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if (!rowIndexToErrorMessage.containsKey(i)) {
                                dataNew.put(jsonArray.get(i));
                            } else {
                                // process faulty rows by placing them on a dead-letter-queue, for instance
                            }
                        }

                        // Retry the remaining valid rows, but using a separate thread to
                        // avoid potentially blocking while we are in a callback.
                        if (dataNew.length() > 0) {
                            try {
                                this.parent.append(new JSONArray(dataNew));
                            } catch (Descriptors.DescriptorValidationException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        // Mark the existing attempt as done since we got a response for it
                        done();
                        return;
                    }
                }

                boolean resendRequest = false;
                if (throwable instanceof Exceptions.MaximumRequestCallbackWaitTimeExceededException) {
                    resendRequest = true;
                } else if (throwable instanceof Exceptions.StreamWriterClosedException) {
                    if (!parent.streamWriter.isUserClosed()) {
                        resendRequest = true;
                    }
                }
                if (resendRequest) {
                    // Retry this request.
                    try {
                        this.parent.append(new JSONArray(jsonArray));
                    } catch (Descriptors.DescriptorValidationException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // Mark the existing attempt as done since we got a response for it
                    done();
                    return;
                }

                synchronized (this.parent.lock) {
                    if (this.parent.error == null) {
                        Exceptions.StorageException storageException = Exceptions.toStorageException(throwable);
                        this.parent.error =
                                (storageException != null) ? storageException : new RuntimeException(throwable);
                    }
                }
                done();
            }

            private void done() {
                // Reduce the count of in-flight requests.
                this.parent.inflightRequestCount.arriveAndDeregister();
            }
        }
    }
}
