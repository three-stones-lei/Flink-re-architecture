package org.lei.beanClass;

/**
 * ClassName: _3D_tour_played
 * Package: org.lei.beanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 12:58 pm
 * @Version 1.0
 */
public class _3D_tour_played {
    private String event_name;
    private String listing_id;
    private String[] secondary_agency_ids;
    private String agency_id;

    public _3D_tour_played() {
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getListing_id() {
        return listing_id;
    }

    public void setListing_id(String listing_id) {
        this.listing_id = listing_id;
    }

    public String[] getSecondary_agency_ids() {
        return secondary_agency_ids;
    }

    public void setSecondary_agency_ids(String[] secondary_agency_ids) {
        this.secondary_agency_ids = secondary_agency_ids;
    }

    public String getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(String agency_id) {
        this.agency_id = agency_id;
    }

    @Override
    public String toString() {
        return "_3D_tour_played{" +
                "event_name='" + event_name + '\'' +
                ", listing_id='" + listing_id + '\'' +
                ", secondary_agency_ids='" + secondary_agency_ids + '\'' +
                ", agency_id='" + agency_id + '\'' +
                '}';
    }
}
