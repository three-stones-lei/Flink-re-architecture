package org.lei.function;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;


import java.util.Map;

/**
 * ClassName: ProcessWindow
 * Package: org.lei.function
 * Description:
 *
 * @Author Lei
 * @Create 16/4/2024 7:02 pm
 * @Version 1.0
 */
public class ProcessWindow extends ProcessWindowFunction<Map<String, Object>, Map<String, Object>, Map<String, Object>, TimeWindow> {
    @Override
    public void process(Map<String, Object> key, ProcessWindowFunction<Map<String, Object>, Map<String, Object>, Map<String, Object>, TimeWindow>.Context context, Iterable<Map<String, Object>> elements, Collector<Map<String, Object>> out) throws Exception {
        long startTs = context.window().getStart();
        long endTs = context.window().getEnd();
        String windowStart = DateFormatUtils.format(startTs, "yyyy-MM-dd'T'HH:mm:ss");
        String windowEnd = DateFormatUtils.format(endTs, "yyyy-MM-dd'T'HH:mm:ss");
        long count = elements.spliterator().estimateSize();
        key.put("windowStart", windowStart);
        key.put("windowEnd",windowEnd);
        key.putAll(elements.iterator().next());

        out.collect(key);

    }
}
