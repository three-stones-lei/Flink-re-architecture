package org.lei;


import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.lei.beanClass.Events_v1;
import org.lei.function.*;
import org.lei.utility.TimeProcessing;

import javax.lang.model.element.VariableElement;
import java.time.Duration;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        env.setParallelism(1);
        
//        DataStreamSource<String> listingDS = env.socketTextStream("localhost", 7777);
        //1. file source
        FileSource<String> fileSource = FileSource
                .forRecordStreamFormat(
                        new TextLineInputFormat(),
                        new Path("/Users/barry.wu/Documents/IDEA/Flink-re-architecture/Audience/input/input_newline.json"))
                .build();
        env
                .fromSource(fileSource, WatermarkStrategy.noWatermarks(), "filesource")
        //2. Mapping: Json string to Class
                .map(
                        new JsonStringToClass()
                )

        //3. event time watermark
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy
                                .<Events_v1>forBoundedOutOfOrderness(Duration.ofSeconds(3))
                                .withTimestampAssigner(
                                        new SerializableTimestampAssigner<Events_v1>() {
                                            @Override
                                            public long extractTimestamp(Events_v1 element, long recordTimestamp) {
                                                return TimeProcessing.UtcStringToLongType(element.getCollector_tstamp(),"Australia/Melbourne");
                                            }
                                        }
                                )
                )

        //4. Extraction, staging, staging_consolidaiton, LPAPI_enrichment
                .process(new MappingProcess())


        //5. Key by event_name
                .keyBy(new KeySelector())


        //6. one hour time tumbling windows
                .window(
                        TumblingEventTimeWindows.of(Time.hours(1))
                )

        //7. aggregating process
                .aggregate(
                        new Aggregate(),
                        new ProcessWindow()
                )

        //8. Mysql sink
                .addSink(MysqlSinkFunction.getSinkFunction());


        //9. checkpoint, savepoint

//                .print();




        env.execute();
    }
}
