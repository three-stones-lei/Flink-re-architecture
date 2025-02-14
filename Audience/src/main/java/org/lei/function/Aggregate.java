package org.lei.function;

import org.apache.flink.api.common.functions.AggregateFunction;

import java.util.*;

/**
 * ClassName: Aggregate
 * Package: org.lei.function
 * Description:
 *
 * @Author Lei
 * @Create 16/4/2024 6:41 pm
 * @Version 1.0
 */
public class Aggregate implements AggregateFunction<Map<String,Object>, Map<String,Object> , Map<String,Object>> {
    @Override
    public Map<String,Object> createAccumulator() {
        return new HashMap<String, Object>();
        //property_view scheme
//        {"property_view": {"total_count": 2,
//                           "property_view_source": [{"source": "", "clicks_count": 1},
//                                                    {"source": "", "clicks_count": 2}
//                                                    ]
//                          }
//        }

        //_3d_tour_played scheme
//        {"_3d_tour_played": 2}
    }

    @Override
    public Map<String,Object> add(Map<String, Object> value, Map<String,Object> accumulator) {
        String listing_id = (String) value.get("listing_id");
        String event_name = (String) value.get("event_name");
        String page_name = (String) value.get("page_name");
        String click_through_source_page = (String) value.get("click_through_source_page");
        String click_through_source_element = (String) value.get("click_through_source_element");
        String property_view_source = "";

        if(!listing_id.equals("")){
            //all cases
            if("page_view".equals(event_name)){
               if(Arrays.asList("rea:buy:retirement village details",
                    "rea:buy:project profile details",
                    "rea:buy:property details",
                    "reamob:buy:property details",
                    "rea:rent:property details",
                    "rea:sold:property details",
                    "rea:new homes:home designs:design details" ).contains(page_name.toLowerCase())) {
                   //first record, so accumulator is null
                   if (accumulator.get("property_view") == null){
                       int total_count = 0;
                       Map<String, Object> propertyViewMap = new HashMap<>();
                       propertyViewMap.put("total_count", total_count + 1);
                       accumulator.put("property_view", propertyViewMap);
                   //non-first record, accumulator has value
                   }else {
                       int total_count = (int)((Map)accumulator.get("property_view")).get("total_count");
                       ((Map)accumulator.get("property_view")).put("total_count", total_count + 1);
                   }

                   property_view_source = click_through_source_page + "-" + click_through_source_element;
                }

               //Have source case
               if(Arrays.asList("rea:buy:retirement village details",
                       "rea:buy:project profile details",
                       "rea:buy:property details",
                       "reamob:buy:property details",
                       "rea:rent:property details",
                       "rea:sold:property details",
                       "rea:new homes:home designs:design details" ).contains(page_name.toLowerCase())
                        && !click_through_source_page.equals("")
                        && !click_through_source_element.equals("")){
               //first record, but accumulator is not null, because it is updated in "all cases"
                   if (((Map)accumulator.get("property_view")).get("property_view_source") == null){
                       List<Map<String, Integer>> sourceViewList = new ArrayList<>();
                       Map<String, Integer> sourceCountMap = new HashMap<>();
                       sourceCountMap.put(property_view_source, 1);
                       sourceViewList.add(sourceCountMap);
                       ((Map)accumulator.get("property_view")).put("property_view_source", sourceViewList);
               //non-first record, accumulator has value
                   }else{
                       List propertyViewSource = (List)((Map)accumulator.get("property_view")).get("property_view_source");
                       boolean isHaveSource = false;
                       for (Object sourceCount : propertyViewSource) {
                           //source exists in accumulator
                           if (((Map) sourceCount).containsKey(property_view_source)) {
                               int clicksCount = (int) ((Map) sourceCount).get("clicks_count");
                               ((Map) sourceCount).put(property_view_source, clicksCount + 1);
                               isHaveSource = true;
                               break;
                           }
                       }
                       //source doesn't exist in accumulator
                       if(!isHaveSource){
                           Map<String, Integer> sourceCountMap = new HashMap<>();
                           sourceCountMap.put(property_view_source, 1);
                           propertyViewSource.add(sourceCountMap);
                       }


                   }
               }

            }else if("3d_tour_played".equals(event_name)){
                int count = accumulator.get("_3d_tour_played") == null ? 0 : (int) accumulator.get("_3d_tour_played");
                accumulator.put("_3d_tour_played", count + 1);

            }
        }
        return accumulator;



    }

    @Override
    public Map<String,Object> getResult(Map<String,Object> accumulator) {
        return accumulator;
    }

    @Override
    public Map<String,Object> merge(Map<String,Object> a, Map<String,Object> b) {
        return null;
    }


}
