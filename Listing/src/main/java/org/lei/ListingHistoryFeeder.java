package org.lei;

import org.apache.flink.api.common.eventtime.Watermark;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.util.Collector;
import org.lei.function.*;


import java.util.Base64;
import java.util.List;

/**
 * ClassName: ListingHistoryFeeder
 * Package: org.lei
 * Description:
 *
 * @Author Lei
 * @Create 27/6/2024 4:43 pm
 * @Version 1.0
 */
public class ListingHistoryFeeder {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<List<String>> apiSource = env.addSource(new LPAPISourceFunction());
//        SingleOutputStreamOperator<String> splitDS = apiSource.flatMap(new FlatMapFunction<List<String>, String>() {
//            @Override
//            public void flatMap(List<String> value, Collector<String> out) throws Exception {
//                for (String s : value) {
//                    out.collect(s);
//                    System.out.println(s.length());
//
//                }
//            }
//        });

        apiSource.addSink(new BQSinkFunctionBatch("scd-pipeline", "lei", "raw_listing_publisher_changes_flink_sink"));
//        apiSource.addSink(new BQSinkFunction("scd-pipeline", "lei", "raw_listing_publisher_changes_flink_sink"));
        //splitDS.addSink(new BQSinkFunctionAtLeastOnce("scd-pipeline", "lei", "raw_listing_publisher_changes_flink_sink"));
        //splitDS.addSink(new BQSinkFunctionLegacy("scd-pipeline", "lei", "raw_listing_publisher_changes_flink_sink"));
        env.execute();
    }
}
