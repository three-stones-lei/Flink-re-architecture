package org.lei;

import com.google.protobuf.Descriptors;
import com.mysql.cj.x.protobuf.MysqlxExpr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryError;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.InsertAllRequest;
import com.google.cloud.bigquery.InsertAllResponse;
import com.google.cloud.bigquery.TableId;
import com.google.common.collect.ImmutableList;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * ClassName: test
 * Package: org.lei
 * Description:
 *
 * @Author Lei
 * @Create 18/6/2024 4:21 pm
 * @Version 1.0
 */
public class test {
    public static void main(String[] args) throws Descriptors.DescriptorValidationException, IOException, InterruptedException {
        try{
            //WriteCommittedStream.runWriteCommittedStream();
            String datasetName = "lei";
            String tableName = "raw_listing_publisher_changes_flink_sink";
            tableInsertRowsWithoutRowIds(datasetName, tableName);
        }
        catch (Exception e){
            e.printStackTrace();
//            System.out.println("dirty data inserted fail: " + "....");
        }
    }

    public static void tableInsertRowsWithoutRowIds(String datasetName, String tableName) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.

            final BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
            // Create rows to insert
            Map<String, Object> rowContent1 = new HashMap<>();
            rowContent1.put("stringField", "Phred Phlyntstone");
            rowContent1.put("numericField", 32);
            Map<String, Object> rowContent2 = new HashMap<>();
            rowContent2.put("stringField", "Wylma Phlyntstone");
            rowContent2.put("numericField", 29);
            String row = "{\"eventId\":\"listing-ffa43318-8f66-4452-9937-65aeb7ff681c\",\"operation\":{\"put\":{\"id\":\"436710024\",\"title\":\"QUIET & CONVENIENT NEAR NEW  TWO BEDROOM, AIR CONDITIONED, VILLA UNIT - RENT FREE OFFERED TO APPLICANTS !!!\",\"_links\":{\"self\":{\"href\":\"436710024\"},\"http://data.realestate.com.au/doc/relations#adUrl\":null,\"http://data.realestate.com.au/doc/relations#projectWebsite\":null},\"address\":{\"state\":\"Vic\",\"suburb\":\"Werribee\",\"display\":{\"geocode\":{\"latitude\":-37.88870689,\"longitude\":144.6605067,\"reliability\":\"property\"},\"fullAddress\":\"63/22 Ventosa Way, Werribee, Vic 3030\",\"shortAddress\":\"63/22 Ventosa Way\",\"communicationAddress\":\"63/22 Ventosa Way, Werribee\"},\"isHidden\":false,\"postcode\":\"3030\",\"_embedded\":{\"atlasObject\":[{\"id\":\"5a1f0dfd-65e3-42de-bbc5-d41deadce4f7\",\"subtype\":\"suburb\"},{\"id\":\"cca27dd0-38bb-4f63-ac6e-218a2458b7f6\",\"subtype\":\"postcode\"},{\"id\":\"AU-VIC\",\"subtype\":\"state\"}]},\"streetName\":\"Ventosa Way\",\"countryCode\":\"AU\",\"streetNumber\":\"22\"},\"fencing\":null,\"dateSold\":{\"date\":null,\"display\":null},\"frontage\":null,\"landSize\":null,\"_embedded\":{\"http://data.realestate.com.au/doc/relations#child\":[],\"http://data.realestate.com.au/doc/relations#event\":[],\"http://data.realestate.com.au/doc/relations#video\":[],\"http://data.realestate.com.au/doc/relations#agency\":[{\"id\":\"CMTOUP\",\"_links\":{\"self\":{\"href\":\"../agencies/CMTOUP\"}}}],\"http://data.realestate.com.au/doc/relations#parent\":[],\"http://data.realestate.com.au/doc/relations#attachment\":[{\"id\":\"048b3f6179403fe37f2296c16c94e67624feb34a02a0eccd927c0b2bd3bba070.jpg\",\"sha\":\"048b3f6179403fe37f2296c16c94e67624feb34a02a0eccd927c0b2bd3bba070\",\"type\":\"image\",\"caption\":null,\"extension\":\"jpg\",\"orderIndex\":0,\"templatedUrl\":\"https://i2.au.reastatic.net/{size}/048b3f6179403fe37f2296c16c94e67624feb34a02a0eccd927c0b2bd3bba070/main.jpg\"}],\"http://data.realestate.com.au/doc/relations#salesperson\":[{\"id\":\"3155904\",\"_links\":{\"self\":{\"href\":\"../salespeople/3155904\"}}},{\"id\":\"2689338\",\"_links\":{\"self\":{\"href\":\"../salespeople/2689338\"}}}],\"http://data.realestate.com.au/doc/relations#listingDepth\":[],\"http://data.realestate.com.au/doc/relations#primaryUsage\":[{\"id\":\"unit\"}],\"http://data.realestate.com.au/doc/relations#listingFeature\":[],\"http://data.realestate.com.au/doc/relations#listingUpgrade\":null,\"http://data.realestate.com.au/doc/relations#secondaryUsage\":[]},\"audiences\":[\"realestate.com.au\"],\"crossover\":null,\"leftDepth\":null,\"rearDepth\":null,\"soilTypes\":null,\"soldPrice\":{\"display\":{\"fullSoldPrice\":null,\"shortSoldPrice\":null,\"advertisingRange\":null}},\"totalSize\":null,\"yearBuilt\":null,\"dateActive\":\"2023-04-27\",\"dateLeased\":\"2023-05-19\",\"highlights\":[],\"irrigation\":null,\"numToilets\":null,\"rightDepth\":null,\"description\":\"YOU MUST EXPERIENCE THIS NEAR NEW two bedroom, air conditioned (Hitachi Split System) villa unit comprising built in robes in all bedrooms, large open plan living areas, quality modern kitchen/meals area with brand new Venini Stainless Steel gas/electric appliances, large bright bathroom with separate shower and bath, separate laundry, quality fixtures and fittings, private courtyard and a lock up garage with an additional car space. Close to Werribee Village shops (Sims Supermarket)  and transport. Do not miss out on this rental opportunity. <br/><br/>Contact Dante Palermo now at!<br/>dpalermo@hallmarc.com.au<br/>0409 748 860\",\"marketFlags\":[\"leased\"],\"numBedrooms\":2,\"numEnsuites\":null,\"publication\":[],\"buildingSize\":null,\"councilRates\":null,\"improvements\":null,\"numBathrooms\":1,\"businessPhone\":null,\"dateAvailable\":{\"date\":\"2023-05-12\",\"display\":\"Fri 12 May 2023\"},\"isOnTheMarket\":false,\"isPublishable\":false,\"parkingSpaces\":{\"total\":1,\"numOpenSpaces\":null,\"numGarageSpaces\":1,\"numCarportSpaces\":null},\"annualRainfall\":null,\"dateTimeActive\":\"2023-04-27T05:35:20Z\",\"emailAddresses\":[],\"financialTerms\":{\"lease\":{\"bond\":{\"value\":1517},\"display\":{\"bond\":\"$1,517\",\"pricePerWeek\":\"$350\",\"pricePerMonth\":\"$350\",\"advertisingRange\":\"350pw_450pw\"},\"isHidden\":true,\"pricePerWeek\":{\"value\":350},\"pricePerMonth\":{}}},\"lastModifiedAt\":\"2023-05-19T05:44:26Z\",\"numLivingAreas\":null,\"listingStatuses\":[],\"newConstruction\":false,\"carryingCapacity\":null,\"datePreMarketEnd\":null,\"externalListingId\":\"rea_799_1358025\",\"propertyTypeGroup\":\"unit\",\"yearLastRenovated\":null,\"datePreMarketStart\":null,\"publicationMetaData\":{\"audiences\":[\"realestate.com.au\"],\"agencyFlags\":{\"isSuspended\":false,\"validSubscription\":true},\"marketFlags\":[\"leased\"],\"dateTimeExpiry\":null,\"parentPublicationMetaData\":null},\"listingCategoryGroup\":\"Rental\",\"energyEfficiencyRating\":null,\"dateEstimatedCompletion\":{\"date\":null,\"display\":null},\"textTotalNumberProperties\":null}},\"processedAt\":\"2023-05-19T05:44:48.816194274Z\",\"documentType\":\"listing\",\"listingSegment\":\"rea\"}";
            JSONObject jsonObject = new JSONObject(row);
            Map<String, Object> rowMap = jsonObject.toMap();

            InsertAllResponse response =
                    bigquery.insertAll(
                            InsertAllRequest.newBuilder(TableId.of(datasetName, tableName))
                                    // No row ids disable de-duplication, and also disable the retries in the Java
                                    // library.

                                    .setRows(
                                            ImmutableList.of(
                                                    InsertAllRequest.RowToInsert.of(rowMap)))
                                    .build());

            if (response.hasErrors()) {
                // If any of the insertions failed, this lets you inspect the errors
                for (Map.Entry<Long, List<BigQueryError>> entry : response.getInsertErrors().entrySet()) {
                    System.out.println("Response error: \n" + entry.getValue());
                }
            }
            System.out.println("Rows successfully inserted into table without row ids");
        } catch (BigQueryException e) {
            System.out.println("Insert operation not performed \n" + e.toString());
        }
    }
}
