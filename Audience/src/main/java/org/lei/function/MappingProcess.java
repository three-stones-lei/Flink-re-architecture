package org.lei.function;

import org.apache.flink.api.java.tuple.Tuple6;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.lei.beanClass.Agent;
import org.lei.beanClass.Events_v1;
import org.lei.utility.TimeProcessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MappingProcess
 * Package: org.lei.function
 * Description:
 *
 * @Author Lei
 * @Create 16/4/2024 4:48 pm
 * @Version 1.0
 */
public class MappingProcess extends ProcessFunction<Events_v1, Map<String, Object>> {
    @Override
    public void processElement(Events_v1 value, ProcessFunction<Events_v1, Map<String, Object>>.Context ctx, Collector<Map<String, Object>> out) throws Exception {
        String eventName1 = "";
        if (value.getEvent_name() != null) {
            eventName1 = value.getEvent_name();
        }

        String eventName2 = "";
        if (value.get_3d_tour_played() != null) {
            if (value.get_3d_tour_played().getEvent_name() != null) {
                eventName2 = value.get_3d_tour_played().getEvent_name();
            }
        }

        String eventName = "";
        if (!eventName1.equals("")) {
            eventName = eventName1;
        } else if (!eventName2.equals("")) {
            eventName = eventName2;
        }

        String listing_id1 = "";
        if (value.get_3d_tour_played() != null) {
            if (value.get_3d_tour_played().getListing_id() != null) {
                listing_id1 = value.get_3d_tour_played().getListing_id();
            }
        }
        String listing_id2 = "";
        if (value.getProperty() != null) {
            if (value.getProperty().getListing_id() != null) {
                listing_id2 = value.getProperty().getListing_id();
            }
        }
        String listing_id3 = "";
        if (value.getListing() != null) {
            if (value.getListing().getListing_id() != null) {
                listing_id3 = value.getListing().getListing_id();
            }
        }
        String listing_id = "";
        if (!listing_id1.equals("")) {
            listing_id = listing_id1;
        } else if (!listing_id2.equals("")) {
            listing_id = listing_id2;
        } else if (!listing_id3.equals("")) {
            listing_id = listing_id3;
        }

        List<Tuple6<String, String, String, Boolean, Float, Integer>> agents = new ArrayList<>();
        if (value.getAgents().getAgents() != null) {
            for (Agent agent : value.getAgents().getAgents()) {
                String agent_id = "";
                if (agent.getAgent_id() != null) {
                    agent_id = agent.getAgent_id();
                }
                String agency_id = "";
                if (agent.getAgency_id() != null) {
                    agency_id = agent.getAgency_id();
                }
                String Agent_profile_id = "";
                if (agent.getAgent_profile_id() != null) {
                    Agent_profile_id = agent.getAgent_profile_id();
                }
                Boolean isPower_profile = false;
                if (agent.isPower_profile() != null) {
                    isPower_profile = agent.isPower_profile();
                }
                Float Avg_rating = 0F;
                if (agent.getAvg_rating() != null) {
                    Avg_rating = agent.getAvg_rating();
                }
                Integer Total_reviews = 0;
                if (agent.getTotal_reviews() != null) {
                    Total_reviews = agent.getTotal_reviews();
                }

                agents.add(Tuple6.of(agent_id,
                        agency_id,
                        Agent_profile_id,
                        isPower_profile,
                        Avg_rating,
                        Total_reviews));
            }
        }


        String reauid = "";
        String my_rea_id = "";
        String rcauid = "";
        String my_rca_id = "";
        String locke_id = "";
        String user_country_code = "";
        if (value.getUser() != null) {
            if (value.getUser().getReauid() != null) {
                reauid = value.getUser().getReauid();
            }
            if (value.getUser().getMy_rea_id() != null) {
                my_rea_id = value.getUser().getMy_rea_id();
            }
            if (value.getUser().getRcauid() != null) {
                rcauid = value.getUser().getRcauid();
            }
            if (value.getUser().getMy_rca_id() != null) {
                my_rca_id = value.getUser().getMy_rca_id();
            }
            if (value.getUser().getLocke_id() != null) {
                locke_id = value.getUser().getLocke_id();
            }
            if (value.getUser().getCountry_code() != null) {
                user_country_code = value.getUser().getCountry_code();
            }
        }

        String site = "";
        String site_section = "";
        String site_sub_section = "";
        String page_name = "";
        String page_type = "";
        String platform = "";
        String rendered_on = "";
        String click_through_source_page = "";
        String click_through_source_element = "";
        if (value.getPage() != null) {
            if (value.getPage().getSite() != null) {
                site = value.getPage().getSite().toLowerCase();
            }
            site_section = value.getPage().getSite_section();
            site_sub_section = value.getPage().getSite_sub_section();
            page_name = value.getPage().getPage_name();
            page_type = value.getPage().getPage_type();
            platform = value.getPage().getPlatform();
            rendered_on = value.getPage().getRendered_on();
            if (value.getPage().getClick_through_source_2() != null) {
                if (value.getPage().getClick_through_source_2().getPage() != null) {
                    click_through_source_page = value.getPage().getClick_through_source_2().getPage();
                }
                if (value.getPage().getClick_through_source_2().getElement() != null) {
                    click_through_source_element = value.getPage().getClick_through_source_2().getElement();
                }
            }

        }

        String parent_listing_id = "";
        String listing_product_depth = "";
        if (value.getListing() != null) {
            parent_listing_id = value.getListing().getParent_listing_id();
            listing_product_depth = value.getListing().getProduct_depth();
        }

        String state = "";
        String suburb = "";
        String postcode = "";
        if (value.getProperty() != null) {
            state = value.getProperty().getState();
            suburb = value.getProperty().getSuburb();
            postcode = value.getProperty().getPostcode();
        }


        Map<String, Object> eventHashMap = new HashMap();


        eventHashMap.put("collector_tstamp", value.getCollector_tstamp());
        eventHashMap.put("collector_datetime", TimeProcessing.UtcStringToLocalDatetimeString(value.getCollector_tstamp(), "Australia/Melbourne"));
        eventHashMap.put("event_date", TimeProcessing.UtcStringToLocalDateString(value.getCollector_tstamp(), "Australia/Melbourne"));
        eventHashMap.put("reauid", reauid);
        eventHashMap.put("my_rea_id", my_rea_id);
        eventHashMap.put(("rcauid"), rcauid);
        eventHashMap.put("my_rca_id", my_rca_id);
        eventHashMap.put("locke_id", locke_id);
        eventHashMap.put("user_country_code", user_country_code);
        eventHashMap.put("site", site);
        eventHashMap.put("site_section", site_section);
        eventHashMap.put("site_sub_section", site_sub_section);
        eventHashMap.put("page_name", page_name);
        eventHashMap.put("page_type", page_type);
        eventHashMap.put("platform", platform);
        eventHashMap.put("rendered_on", rendered_on);
        eventHashMap.put("state", state);
        eventHashMap.put("suburb", suburb);
        eventHashMap.put("postcode", postcode);
        eventHashMap.put("event_name", eventName);
        eventHashMap.put("listing_id", listing_id);
        eventHashMap.put("parent_listing_id", parent_listing_id);
        eventHashMap.put("listing_product_depth", listing_product_depth);
        eventHashMap.put("agents", agents);
        eventHashMap.put("click_through_source_page", click_through_source_page);
        eventHashMap.put("click_through_source_element", click_through_source_element);

        out.collect(eventHashMap);
//                                System.out.println(DateFormatUtils.format(ctx.timestamp(),"yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
