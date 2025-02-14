package org.lei.function;

import java.util.HashMap;
import java.util.Map;


/**
 * ClassName: KeySelector
 * Package: org.lei.function
 * Description:
 *
 * @Author Lei
 * @Create 16/4/2024 5:31 pm
 * @Version 1.0
 */
public class KeySelector implements org.apache.flink.api.java.functions.KeySelector<Map<String, Object>,Map<String, Object>> {
    @Override
    public Map<String, Object> getKey(Map<String, Object> value) throws Exception {
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("reauid", value.get("reauid"));
        keyMap.put("my_rea_id", value.get("my_rea_id"));
        keyMap.put(("rcauid"), value.get("rcauid"));
        keyMap.put("my_rca_id", value.get("my_rca_id"));
        keyMap.put("locke_id", value.get("locke_id"));
        keyMap.put("user_country_code", value.get("user_country_code"));
        keyMap.put("site", value.get("site"));
        keyMap.put("site_section", value.get("site_section"));
        keyMap.put("site_sub_section", value.get("site_sub_section"));
        keyMap.put("page_name", value.get("page_name"));
        keyMap.put("page_type", value.get("page_type"));
        keyMap.put("platform", value.get("platform"));
        keyMap.put("rendered_on", value.get("rendered_on"));
        keyMap.put("state", value.get("state"));
        keyMap.put("suburb", value.get("suburb"));
        keyMap.put("postcode", value.get("postcode"));
        keyMap.put("event_name", value.get("event_name"));
        keyMap.put("listing_id", value.get("listing_id"));
        keyMap.put("parent_listing_id", value.get("parent_listing_id"));
        return keyMap;
    }
}
