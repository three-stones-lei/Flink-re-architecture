package org.lei.beanClass;

/**
 * ClassName: Events_v1
 * Package: org.lei.beanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 12:02 pm
 * @Version 1.0
 */
public class Events_v1 {
    private String collector_tstamp;
    private User user;
    private Page page;
    private Property property;
    private Listing listing;
    private Agents agents;

    private String event_name;
    private _3D_tour_played _3d_tour_played;

    private String datetime_mel;
    private String hour;

    public String getDatetime_mel() {
        return datetime_mel;
    }

    public void setDatetime_mel(String datetime_mel) {
        this.datetime_mel = datetime_mel;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Events_v1() {
    }

    public String getCollector_tstamp() {
        return collector_tstamp;
    }

    public void setCollector_tstamp(String collector_tstamp) {
        this.collector_tstamp = collector_tstamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public Agents getAgents() {
        return agents;
    }

    public void setAgents(Agents agents) {
        this.agents = agents;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public _3D_tour_played get_3d_tour_played() {
        return _3d_tour_played;
    }

    public void set_3d_tour_played(_3D_tour_played _3d_tour_played) {
        this._3d_tour_played = _3d_tour_played;
    }

    @Override
    public String toString() {
        return "Events_v1{" +
                "collector_tstamp='" + collector_tstamp + '\'' +
                ", user=" + user +
                ", page=" + page +
                ", property=" + property +
                ", listing=" + listing +
                ", agents=" + agents +
                ", event_name='" + event_name + '\'' +
                ", _3d_tour_played=" + _3d_tour_played +
                ", datetime_mel='" + datetime_mel + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }
}
