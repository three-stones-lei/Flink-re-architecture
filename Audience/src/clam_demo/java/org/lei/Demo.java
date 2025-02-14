package org.lei;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.junit.Test;

import java.time.Duration;

/**
 * ClassName: App
 * Package: org.lei
 * Description:
 *
 * @Author Lei
 * @Create 1/8/2024 9:12 pm
 * @Version 1.0
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        //1. setup stream execution environment
 //       StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        env.setParallelism(1);

        //2. get data source from socket, run 'nc -lk 7777' in cmd.
        DataStreamSource<String> socketDS = env.socketTextStream("localhost", 7777);

        //3. filter out dirty data
        SingleOutputStreamOperator<JSONObject> jsonObjDS = socketDS.flatMap(new FlatMapFunction<String, JSONObject>() {
            @Override
            public void flatMap(String value, Collector<JSONObject> out) throws Exception {
                try {
                    JSONObject jsonObject = JSONObject.parseObject(value);
                    String listingId = jsonObject.getString("listing_id");
                    String collectorTstamp = jsonObject.getString("collector_tstamp");
                    String eventName = jsonObject.getString("event_name");
                    if (listingId != null && collectorTstamp != null && eventName != null) {
                        out.collect(jsonObject);
                    }
                } catch (Exception e) {
                    System.out.println("filter out dirty data: " + value);
                }
            }
        });

        //4. add watermark
        SingleOutputStreamOperator<JSONObject> withWatermarkDS = jsonObjDS.assignTimestampsAndWatermarks(
                WatermarkStrategy
                .<JSONObject>forBoundedOutOfOrderness(Duration.ofSeconds(0L))
                .withTimestampAssigner(new SerializableTimestampAssigner<JSONObject>() {
                    @Override
                    public long extractTimestamp(JSONObject element, long recordTimestamp) {
                        String collectorTstamp = element.getString("collector_tstamp");
                        return DateFormatUtil.dateTimeToTs(collectorTstamp);
                    }
                }));

        //5. key by listing_id
        KeyedStream<JSONObject, String> keyedDS = withWatermarkDS.keyBy(new KeySelector<JSONObject, String>() {
            @Override
            public String getKey(JSONObject value) throws Exception {
                return value.getString("listing_id");
            }
        });

        //6.1 process metric logic transform and aggregate without windows
        SingleOutputStreamOperator<String> aggregatedDSWithoutWindows = keyedDS.process(new KeyedProcessFunction<String, JSONObject, String>() {
            ValueState<ClamBean> lastTotalMetricState;

            @Override
            public void open(Configuration parameters) throws Exception {
                super.open(parameters);
                lastTotalMetricState = getRuntimeContext().getState(new ValueStateDescriptor<ClamBean>("lastTotalMetricState", Types.POJO(ClamBean.class)));
            }

            @Override
            public void processElement(JSONObject value, KeyedProcessFunction<String, JSONObject, String>.Context ctx, Collector<String> out) throws Exception {
                ClamBean lastClam = lastTotalMetricState.value();
                ClamBean clamBean = new ClamBean();
                String eventName = value.getString("event_name");
                Long videoPlayed = 0L;
                Long expandMap = 0L;
                if ("video_played".equals(eventName)) {
                    videoPlayed = 1L;
                } else if ("expand_map".equals(eventName)) {
                    expandMap = 1L;
                }
                if (lastClam == null) {
                    clamBean.setListingId(ctx.getCurrentKey());
                    clamBean.setVideoPlayed(videoPlayed);
                    clamBean.setExpandMap(expandMap);
                    out.collect("Listing_id: " + clamBean.getListingId() + " ,video_played: " + clamBean.getVideoPlayed() + " ,expand_map: " + clamBean.getExpandMap());
                } else {

                    clamBean.setListingId(ctx.getCurrentKey());
                    clamBean.setVideoPlayed(videoPlayed + lastClam.getVideoPlayed());
                    clamBean.setExpandMap(expandMap + lastClam.getExpandMap());
                    out.collect("Listing_id: " + clamBean.getListingId() + " ,video_played: " + clamBean.getVideoPlayed() + " ,expand_map: " + clamBean.getExpandMap());
                }
                lastTotalMetricState.update(clamBean);

            }
        });

        //6.2 open windows and process metric logic transform and aggregate
        OutputTag<JSONObject> lateTag = new OutputTag<>("late-data", Types.GENERIC(JSONObject.class));
        SingleOutputStreamOperator<String> aggregatedDS = keyedDS.window(TumblingEventTimeWindows.of(Time.hours(1L)))
//                .allowedLateness(Time.seconds(5))
//                .sideOutputLateData(lateTag)
                .aggregate(
                        new AggregateFunction<JSONObject, ClamBean, ClamBean>() {
                            @Override
                            public ClamBean createAccumulator() {
                                ClamBean clamBean = new ClamBean();
                                clamBean.setVideoPlayed(0L);
                                clamBean.setExpandMap(0L);
                                return clamBean;
                            }

                            @Override
                            public ClamBean add(JSONObject value, ClamBean accumulator) {
                                String eventName = value.getString("event_name");
                                Long videoPlayed = 0L;
                                Long expandMap = 0L;
                                if ("video_played".equals(eventName)) {
                                    videoPlayed = 1L;
                                } else if ("expand_map".equals(eventName)) {
                                    expandMap = 1L;
                                }
                                return ClamBean.builder()
                                        .videoPlayed(videoPlayed + accumulator.getVideoPlayed())
                                        .expandMap(expandMap + accumulator.getExpandMap())
                                        .build();
                            }

                            @Override
                            public ClamBean getResult(ClamBean accumulator) {
                                return accumulator;
                            }

                            @Override
                            public ClamBean merge(ClamBean a, ClamBean b) {
                                return null;
                            }
                        },
                        new ProcessWindowFunction<ClamBean, String, String, TimeWindow>() {
                            @Override
                            public void process(String key, ProcessWindowFunction<ClamBean, String, String, TimeWindow>.Context context, Iterable<ClamBean> elements, Collector<String> out) throws Exception {
                                long startTs = context.window().getStart();
                                long endTs = context.window().getEnd();
                                String windowStart = DateFormatUtil.tsToDateTime(startTs);
                                String windowEnd = DateFormatUtil.tsToDateTime(endTs);
                                ClamBean output = elements.iterator().next();
                                output.setStart(windowStart);
                                output.setEnd(windowEnd);
                                output.setListingId(key);
                                out.collect("start: " + output.getStart() + " ,end: " + output.getEnd() + " ,Listing_id: " + output.getListingId() + " ,video_played: " + output.getVideoPlayed() + " ,expand_map: " + output.getExpandMap());
                            }
                        }
                );

        //8. output to sink
        aggregatedDSWithoutWindows.print();
        aggregatedDS.getSideOutput(lateTag).printToErr("late data after windows close");
        env.execute();

    }

    @Test
    public void test(){
        Long l = DateFormatUtil.dateTimeToTs("2024-03-31 13:59:59");
        System.out.println(l);

    }

}
