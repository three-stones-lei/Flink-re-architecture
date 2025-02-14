package org.lei.function;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: LPAPISourceFunction
 * Package: org.lei.function
 * Description:
 *
 * @Author Lei
 * @Create 27/6/2024 5:10 pm
 * @Version 1.0
 */
public class LPAPISourceFunction implements CheckpointedFunction, SourceFunction<List<String>> {
    ListState<String> lastNextLinkState;


    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {

    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        ListStateDescriptor<String> lastNextLinkDesc = new ListStateDescriptor<>("last_next_link", String.class);
        lastNextLinkState = context.getOperatorStateStore().getListState(lastNextLinkDesc);
    }

    @Override
    public void run(SourceContext<List<String>> ctx) throws Exception {
        while (true) {
            try {
                String baseUrl = "https://listing-publisher-api-ro-prod-int.listings.realestate.com.au";
                String apiUrl = "";
                String nextLink = "";
                for (String s : lastNextLinkState.get()) {
                    nextLink = s;
                }

                if (nextLink == null || nextLink == "") {
                    apiUrl = baseUrl + "/v2/listings/-/changes";
                } else {
                    apiUrl = baseUrl + nextLink;
                }
                URL url = new URL(apiUrl);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Add custom headers
                connection.setRequestProperty("x-api-key", "XXXXXXX");
                connection.setRequestProperty("Content-Type", "application/json");

                // Handle the response here...
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                //StringBuilder response = new StringBuilder();
                ArrayList<String> responseList = new ArrayList<>();


                while ((line = reader.readLine()) != null) {
                    //response.append(line);
                    responseList.add(line);

                }
                reader.close();

                //                    System.out.println("API Response: " + response.toString());

                if(responseList.size() != 0){
                    ctx.collect(responseList);
                }

                //store nextlink to state
                List<String> nextLinkList = new ArrayList<>();
                nextLinkList.add(connection.getHeaderField("x-next-link"));
                lastNextLinkState.update(nextLinkList);
                connection.disconnect();


                //TimeUnit.SECONDS.sleep(2L);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void cancel() {

    }
}
