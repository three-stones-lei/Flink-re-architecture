package org.lei.beanClass;

/**
 * ClassName: Agent
 * Package: org.lei.beanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 12:56 pm
 * @Version 1.0
 */
public class Agent {
    private String agent_id;
    private String agency_id;
    private String agent_profile_id;
    private Boolean power_profile;
    private Float avg_rating;
    private Integer total_reviews;
    private String agency_profile_type;

    public Agent() {
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(String agency_id) {
        this.agency_id = agency_id;
    }

    public String getAgent_profile_id() {
        return agent_profile_id;
    }

    public void setAgent_profile_id(String agent_profile_id) {
        this.agent_profile_id = agent_profile_id;
    }

    public Boolean isPower_profile() {
        return power_profile;
    }

    public void setPower_profile(Boolean power_profile) {
        this.power_profile = power_profile;
    }

    public Float getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(Float avg_rating) {
        this.avg_rating = avg_rating;
    }

    public Integer getTotal_reviews() {
        return total_reviews;
    }

    public void setTotal_reviews(Integer total_reviews) {
        this.total_reviews = total_reviews;
    }

    public String getAgency_profile_type() {
        return agency_profile_type;
    }

    public void setAgency_profile_type(String agency_profile_type) {
        this.agency_profile_type = agency_profile_type;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "agent_id='" + agent_id + '\'' +
                ", agency_id='" + agency_id + '\'' +
                ", agent_profile_id='" + agent_profile_id + '\'' +
                ", power_profile=" + power_profile +
                ", avg_rating=" + avg_rating +
                ", total_reviews=" + total_reviews +
                ", agency_profile_type='" + agency_profile_type + '\'' +
                '}';
    }
}
