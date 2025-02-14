package org.lei.function;

import org.apache.flink.api.common.functions.FilterFunction;
import org.lei.beanClass.Events_v1;

import java.util.Arrays;

/**
 * ClassName: Filter
 * Package: org.lei.function
 * Description:
 *
 * @Author Lei
 * @Create 16/4/2024 3:43 pm
 * @Version 1.0
 */
public class Filter implements FilterFunction<Events_v1> {
    @Override
    public boolean filter(Events_v1 value) throws Exception {

        String site = "";
        if(value.getPage() != null){
            if(value.getPage().getSite() != null){
                site = value.getPage().getSite().toLowerCase();
            }
        }
        String eventName1 = value.getEvent_name();

        String eventName2 = "";
        if(value.get_3d_tour_played() != null){
            if(value.get_3d_tour_played().getEvent_name() != null){
                eventName2 = value.get_3d_tour_played().getEvent_name();
            }
        }


        return (Arrays.asList("rea","rca").contains(site)
                && ("page_view".equals(eventName1)
                || "3d_tour_played".equals(eventName2)));
    }
}
