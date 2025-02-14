package org.lei;

/**
 * ClassName: WriteCommittedStream
 * Package: org.lei
 * Description:
 *
 * @Author Lei
 * @Create 21/6/2024 9:14 pm
 * @Version 1.0
 */
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.retrying.RetrySettings;
import com.google.cloud.bigquery.storage.v1.AppendRowsResponse;
import com.google.cloud.bigquery.storage.v1.BigQueryWriteClient;
import com.google.cloud.bigquery.storage.v1.CreateWriteStreamRequest;
import com.google.cloud.bigquery.storage.v1.Exceptions;
import com.google.cloud.bigquery.storage.v1.Exceptions.StorageException;
import com.google.cloud.bigquery.storage.v1.FinalizeWriteStreamResponse;
import com.google.cloud.bigquery.storage.v1.JsonStreamWriter;
import com.google.cloud.bigquery.storage.v1.TableName;
import com.google.cloud.bigquery.storage.v1.WriteStream;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Phaser;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.threeten.bp.Duration;

public class WriteCommittedStream {

    public static void runWriteCommittedStream()
            throws DescriptorValidationException, InterruptedException, IOException {
        // TODO(developer): Replace these variables before running the sample.
        String projectId = "scd-pipeline";
        String datasetName = "lei";
        String tableName = "raw_listing_publisher_changes_flink_sink";

        writeCommittedStream(projectId, datasetName, tableName);
    }

    public static void writeCommittedStream(String projectId, String datasetName, String tableName)
            throws DescriptorValidationException, InterruptedException, IOException {
        BigQueryWriteClient client = BigQueryWriteClient.create();
        TableName parentTable = TableName.of(projectId, datasetName, tableName);

        DataWriter writer = new DataWriter();
        // One time initialization.
        writer.initialize(parentTable, client);

        try {
            // Write two batches of fake data to the stream, each with 10 JSON records.  Data may be
            // batched up to the maximum request size:
            // https://cloud.google.com/bigquery/quotas#write-api-limits
//            long offset = 0;
//            for (int i = 0; i < 2; i++) {
//                // Create a JSON object that is compatible with the table schema.
//                JSONArray jsonArr = new JSONArray();
//                for (int j = 0; j < 10; j++) {
//                    JSONObject record = new JSONObject();
//                    record.put("col1", String.format("batch-record %03d-%03d", i, j));
//                    jsonArr.put(record);
//                }
//                writer.append(jsonArr, offset);
//                offset += jsonArr.length();
//            }
            JSONArray jsonArray = new JSONArray();
            //JSONObject jsonObject = new JSONObject("{\"eventId\":\"listing-aff01f47-ab39-43db-9ad3-76b5c229f7d7\",\"operation\":{\"delete\":{\"id\":202873026}},\"processedAt\":\"2023-05-19T04:00:38.634429331Z\",\"documentType\":\"delete\",\"listingSegment\":\"rea\"}");
            JSONObject jsonObject = new JSONObject("{\"eventId\":\"listing-ffa43318-8f66-4452-9937-65aeb7ff681c\",\"operation\":{\"put\":{\"id\":\"436710024\",\"title\":\"QUIET & CONVENIENT NEAR NEW  TWO BEDROOM, AIR CONDITIONED, VILLA UNIT - RENT FREE OFFERED TO APPLICANTS !!!\",\"_links\":{\"self\":{\"href\":\"436710024\"},\"http://data.realestate.com.au/doc/relations#adUrl\":null,\"http://data.realestate.com.au/doc/relations#projectWebsite\":null},\"address\":{\"state\":\"Vic\",\"suburb\":\"Werribee\",\"display\":{\"geocode\":{\"latitude\":-37.88870689,\"longitude\":144.6605067,\"reliability\":\"property\"},\"fullAddress\":\"63/22 Ventosa Way, Werribee, Vic 3030\",\"shortAddress\":\"63/22 Ventosa Way\",\"communicationAddress\":\"63/22 Ventosa Way, Werribee\"},\"isHidden\":false,\"postcode\":\"3030\",\"_embedded\":{\"http://data.realestate.com.au/doc/relations#atlasObject\":[{\"id\":\"5a1f0dfd-65e3-42de-bbc5-d41deadce4f7\",\"subtype\":\"suburb\"},{\"id\":\"cca27dd0-38bb-4f63-ac6e-218a2458b7f6\",\"subtype\":\"postcode\"},{\"id\":\"AU-VIC\",\"subtype\":\"state\"}]},\"streetName\":\"Ventosa Way\",\"countryCode\":\"AU\",\"streetNumber\":\"22\"},\"fencing\":null,\"dateSold\":{\"date\":null,\"display\":null},\"frontage\":null,\"landSize\":null,\"_embedded\":{\"http://data.realestate.com.au/doc/relations#child\":[],\"http://data.realestate.com.au/doc/relations#event\":[],\"http://data.realestate.com.au/doc/relations#video\":[],\"http://data.realestate.com.au/doc/relations#agency\":[{\"id\":\"CMTOUP\",\"_links\":{\"self\":{\"href\":\"../agencies/CMTOUP\"}}}],\"http://data.realestate.com.au/doc/relations#parent\":[],\"http://data.realestate.com.au/doc/relations#attachment\":[{\"id\":\"048b3f6179403fe37f2296c16c94e67624feb34a02a0eccd927c0b2bd3bba070.jpg\",\"sha\":\"048b3f6179403fe37f2296c16c94e67624feb34a02a0eccd927c0b2bd3bba070\",\"type\":\"image\",\"caption\":null,\"extension\":\"jpg\",\"orderIndex\":0,\"templatedUrl\":\"https://i2.au.reastatic.net/{size}/048b3f6179403fe37f2296c16c94e67624feb34a02a0eccd927c0b2bd3bba070/main.jpg\"}],\"http://data.realestate.com.au/doc/relations#salesperson\":[{\"id\":\"3155904\",\"_links\":{\"self\":{\"href\":\"../salespeople/3155904\"}}},{\"id\":\"2689338\",\"_links\":{\"self\":{\"href\":\"../salespeople/2689338\"}}}],\"http://data.realestate.com.au/doc/relations#listingDepth\":[],\"http://data.realestate.com.au/doc/relations#primaryUsage\":[{\"id\":\"unit\"}],\"http://data.realestate.com.au/doc/relations#listingFeature\":[],\"http://data.realestate.com.au/doc/relations#listingUpgrade\":null,\"http://data.realestate.com.au/doc/relations#secondaryUsage\":[]},\"audiences\":[\"realestate.com.au\"],\"crossover\":null,\"leftDepth\":null,\"rearDepth\":null,\"soilTypes\":null,\"soldPrice\":{\"display\":{\"fullSoldPrice\":null,\"shortSoldPrice\":null,\"advertisingRange\":null}},\"totalSize\":null,\"yearBuilt\":null,\"dateActive\":\"2023-04-27\",\"dateLeased\":\"2023-05-19\",\"highlights\":[],\"irrigation\":null,\"numToilets\":null,\"rightDepth\":null,\"description\":\"YOU MUST EXPERIENCE THIS NEAR NEW two bedroom, air conditioned (Hitachi Split System) villa unit comprising built in robes in all bedrooms, large open plan living areas, quality modern kitchen/meals area with brand new Venini Stainless Steel gas/electric appliances, large bright bathroom with separate shower and bath, separate laundry, quality fixtures and fittings, private courtyard and a lock up garage with an additional car space. Close to Werribee Village shops (Sims Supermarket)  and transport. Do not miss out on this rental opportunity. <br/><br/>Contact Dante Palermo now at!<br/>dpalermo@hallmarc.com.au<br/>0409 748 860\",\"marketFlags\":[\"leased\"],\"numBedrooms\":2,\"numEnsuites\":null,\"publication\":[],\"buildingSize\":null,\"councilRates\":null,\"improvements\":null,\"numBathrooms\":1,\"businessPhone\":null,\"dateAvailable\":{\"date\":\"2023-05-12\",\"display\":\"Fri 12 May 2023\"},\"isOnTheMarket\":false,\"isPublishable\":false,\"parkingSpaces\":{\"total\":1,\"numOpenSpaces\":null,\"numGarageSpaces\":1,\"numCarportSpaces\":null},\"annualRainfall\":null,\"dateTimeActive\":\"2023-04-27T05:35:20Z\",\"emailAddresses\":[],\"financialTerms\":{\"lease\":{\"bond\":{\"value\":1517},\"display\":{\"bond\":\"$1,517\",\"pricePerWeek\":\"$350\",\"pricePerMonth\":\"$350\",\"advertisingRange\":\"350pw_450pw\"},\"isHidden\":true,\"pricePerWeek\":{\"value\":350},\"pricePerMonth\":{}}},\"lastModifiedAt\":\"2023-05-19T05:44:26Z\",\"numLivingAreas\":null,\"listingStatuses\":[],\"newConstruction\":false,\"carryingCapacity\":null,\"datePreMarketEnd\":null,\"externalListingId\":\"rea_799_1358025\",\"propertyTypeGroup\":\"unit\",\"yearLastRenovated\":null,\"datePreMarketStart\":null,\"publicationMetaData\":{\"audiences\":[\"realestate.com.au\"],\"agencyFlags\":{\"isSuspended\":false,\"validSubscription\":true},\"marketFlags\":[\"leased\"],\"dateTimeExpiry\":null,\"parentPublicationMetaData\":null},\"listingCategoryGroup\":\"Rental\",\"energyEfficiencyRating\":null,\"dateEstimatedCompletion\":{\"date\":null,\"display\":null},\"textTotalNumberProperties\":null}},\"processedAt\":\"2023-05-19T05:44:48.816194274Z\",\"documentType\":\"listing\",\"listingSegment\":\"rea\"}");

            jsonArray.put(jsonObject);



            writer.append(jsonArray, 0L);


        } catch (ExecutionException e) {
            // If the wrapped exception is a StatusRuntimeException, check the state of the operation.
            // If the state is INTERNAL, CANCELLED, or ABORTED, you can retry. For more information, see:
            // https://grpc.github.io/grpc-java/javadoc/io/grpc/StatusRuntimeException.html
            System.out.println("Failed to append records. \n" + e);
        }

        // Final cleanup for the stream.
        writer.cleanup(client);
        System.out.println("Appended records successfully.");
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
                throws IOException, DescriptorValidationException, InterruptedException {
            // Initialize a write stream for the specified table.
            // For more information on WriteStream.Type, see:
            // https://googleapis.dev/java/google-cloud-bigquerystorage/latest/com/google/cloud/bigquery/storage/v1/WriteStream.Type.html
            WriteStream stream = WriteStream.newBuilder().setType(WriteStream.Type.COMMITTED).build();

            CreateWriteStreamRequest createWriteStreamRequest =
                    CreateWriteStreamRequest.newBuilder()
                            .setParent(parentTable.toString())
                            .setWriteStream(stream)
                            .build();
            WriteStream writeStream = client.createWriteStream(createWriteStreamRequest);

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

            // Use the JSON stream writer to send records in JSON format.
            // For more information about JsonStreamWriter, see:
            // https://googleapis.dev/java/google-cloud-bigquerystorage/latest/com/google/cloud/bigquery/storage/v1/JsonStreamWriter.html
            streamWriter =
                    JsonStreamWriter.newBuilder(writeStream.getName(), writeStream.getTableSchema(), client)
                            .setRetrySettings(retrySettings)
                            .build();
        }

        public void append(JSONArray data, long offset)
                throws DescriptorValidationException, IOException, ExecutionException {
            synchronized (this.lock) {
                // If earlier appends have failed, we need to reset before continuing.
                if (this.error != null) {
                    throw this.error;
                }
            }
            // Append asynchronously for increased throughput.
            ApiFuture<AppendRowsResponse> future = streamWriter.append(data, offset);
            ApiFutures.addCallback(
                    future, new DataWriter.AppendCompleteCallback(this), MoreExecutors.directExecutor());
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
                        StorageException storageException = Exceptions.toStorageException(throwable);
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
