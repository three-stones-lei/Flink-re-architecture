package org.lei.function;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.lei.beanClass.Events_v1;

/**
 * ClassName: JsonStringToClass
 * Package: org.lei.mapFunction
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 10:46 am
 * @Version 1.0
 */
public class JsonStringToClass implements MapFunction<String, Events_v1> {
    final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Events_v1 map(String value) throws Exception {
        Events_v1 events_v1 = objectMapper.readValue(value, Events_v1.class);
        return events_v1;

    }
}
