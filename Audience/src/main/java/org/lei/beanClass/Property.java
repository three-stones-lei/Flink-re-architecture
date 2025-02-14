package org.lei.beanClass;

import java.util.List;

/**
 * ClassName: Property
 * Package: org.lei.beanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 12:38 pm
 * @Version 1.0
 */
public class Property {
    private String property_id;
    private String state;
    private String suburb;
    private String postcode;
    private String region;
    private String bedrooms;
    private int bathrooms;
    private int car_spaces;
    private int year_built;
    private String property_type;
    private String marketing_region;
    private int bedrooms_2;
    private String listing_id;
    private String country;
    private String[] secondary_property_types;
    private float land_size_sq_metres;
    private String[] features;
    private String active_rea_listing;
    private String commercial_land_size;
    private String floor_area;
    private String tracked_relationship_type;
    private int avm_estimated_value;
    private int avm_low_range;
    private int avm_high_range;
    private String avm_confidence;
    private String avm_last_updated_date;
    private boolean premiere_plus;
    private String pod_relationship_type;

    public Property() {
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getCar_spaces() {
        return car_spaces;
    }

    public void setCar_spaces(int car_spaces) {
        this.car_spaces = car_spaces;
    }

    public int getYear_built() {
        return year_built;
    }

    public void setYear_built(int year_built) {
        this.year_built = year_built;
    }

    public String getProperty_type() {
        return property_type;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public String getMarketing_region() {
        return marketing_region;
    }

    public void setMarketing_region(String marketing_region) {
        this.marketing_region = marketing_region;
    }

    public int getBedrooms_2() {
        return bedrooms_2;
    }

    public void setBedrooms_2(int bedrooms_2) {
        this.bedrooms_2 = bedrooms_2;
    }

    public String getListing_id() {
        return listing_id;
    }

    public void setListing_id(String listing_id) {
        this.listing_id = listing_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String[] getSecondary_property_types() {
        return secondary_property_types;
    }

    public void setSecondary_property_types(String[] secondary_property_types) {
        this.secondary_property_types = secondary_property_types;
    }

    public float getLand_size_sq_metres() {
        return land_size_sq_metres;
    }

    public void setLand_size_sq_metres(float land_size_sq_metres) {
        this.land_size_sq_metres = land_size_sq_metres;
    }

    public String[] getFeatures() {
        return features;
    }

    public void setFeatures(String[] features) {
        this.features = features;
    }

    public String getActive_rea_listing() {
        return active_rea_listing;
    }

    public void setActive_rea_listing(String active_rea_listing) {
        this.active_rea_listing = active_rea_listing;
    }

    public String getCommercial_land_size() {
        return commercial_land_size;
    }

    public void setCommercial_land_size(String commercial_land_size) {
        this.commercial_land_size = commercial_land_size;
    }

    public String getFloor_area() {
        return floor_area;
    }

    public void setFloor_area(String floor_area) {
        this.floor_area = floor_area;
    }

    public String getTracked_relationship_type() {
        return tracked_relationship_type;
    }

    public void setTracked_relationship_type(String tracked_relationship_type) {
        this.tracked_relationship_type = tracked_relationship_type;
    }

    public int getAvm_estimated_value() {
        return avm_estimated_value;
    }

    public void setAvm_estimated_value(int avm_estimated_value) {
        this.avm_estimated_value = avm_estimated_value;
    }

    public int getAvm_low_range() {
        return avm_low_range;
    }

    public void setAvm_low_range(int avm_low_range) {
        this.avm_low_range = avm_low_range;
    }

    public int getAvm_high_range() {
        return avm_high_range;
    }

    public void setAvm_high_range(int avm_high_range) {
        this.avm_high_range = avm_high_range;
    }

    public String getAvm_confidence() {
        return avm_confidence;
    }

    public void setAvm_confidence(String avm_confidence) {
        this.avm_confidence = avm_confidence;
    }

    public String getAvm_last_updated_date() {
        return avm_last_updated_date;
    }

    public void setAvm_last_updated_date(String avm_last_updated_date) {
        this.avm_last_updated_date = avm_last_updated_date;
    }

    public boolean isPremiere_plus() {
        return premiere_plus;
    }

    public void setPremiere_plus(boolean premiere_plus) {
        this.premiere_plus = premiere_plus;
    }

    public String getPod_relationship_type() {
        return pod_relationship_type;
    }

    public void setPod_relationship_type(String pod_relationship_type) {
        this.pod_relationship_type = pod_relationship_type;
    }

    @Override
    public String toString() {
        return "Property{" +
                "property_id='" + property_id + '\'' +
                ", state='" + state + '\'' +
                ", suburb='" + suburb + '\'' +
                ", postcode='" + postcode + '\'' +
                ", region='" + region + '\'' +
                ", bedrooms='" + bedrooms + '\'' +
                ", bathrooms=" + bathrooms +
                ", car_spaces=" + car_spaces +
                ", year_built=" + year_built +
                ", property_type='" + property_type + '\'' +
                ", marketing_region='" + marketing_region + '\'' +
                ", bedrooms_2=" + bedrooms_2 +
                ", listing_id='" + listing_id + '\'' +
                ", country='" + country + '\'' +
                ", secondary_property_types='" + secondary_property_types + '\'' +
                ", land_size_sq_metres=" + land_size_sq_metres +
                ", features='" + features + '\'' +
                ", active_rea_listing='" + active_rea_listing + '\'' +
                ", commercial_land_size='" + commercial_land_size + '\'' +
                ", floor_area='" + floor_area + '\'' +
                ", tracked_relationship_type='" + tracked_relationship_type + '\'' +
                ", avm_estimated_value=" + avm_estimated_value +
                ", avm_low_range=" + avm_low_range +
                ", avm_high_range=" + avm_high_range +
                ", avm_confidence='" + avm_confidence + '\'' +
                ", avm_last_updated_date='" + avm_last_updated_date + '\'' +
                ", premiere_plus=" + premiere_plus +
                ", pod_relationship_type='" + pod_relationship_type + '\'' +
                '}';
    }
}
