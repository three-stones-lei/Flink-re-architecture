package org.lei.beanClass;

/**
 * ClassName: User
 * Package: org.lei.beanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 12:16 pm
 * @Version 1.0
 */
public class User {
    private String user_login_status;
    private String my_rea_id;
    private String my_rea_anon_id;
    private String reauid;
    private String my_rca_id;
    private String pcauid;
    private String rcauid;
    private String locke_id;
    private String ptuid;
    private String email_verified;
    private String country_code;
    private String mc_brokerplatform_id;

    public User() {
    }

    public String getUser_login_status() {
        return user_login_status;
    }

    public void setUser_login_status(String user_login_status) {
        this.user_login_status = user_login_status;
    }

    public String getMy_rea_id() {
        return my_rea_id;
    }

    public void setMy_rea_id(String my_rea_id) {
        this.my_rea_id = my_rea_id;
    }

    public String getMy_rea_anon_id() {
        return my_rea_anon_id;
    }

    public void setMy_rea_anon_id(String my_rea_anon_id) {
        this.my_rea_anon_id = my_rea_anon_id;
    }

    public String getReauid() {
        return reauid;
    }

    public void setReauid(String reauid) {
        this.reauid = reauid;
    }

    public String getMy_rca_id() {
        return my_rca_id;
    }

    public void setMy_rca_id(String my_rca_id) {
        this.my_rca_id = my_rca_id;
    }

    public String getPcauid() {
        return pcauid;
    }

    public void setPcauid(String pcauid) {
        this.pcauid = pcauid;
    }

    public String getRcauid() {
        return rcauid;
    }

    public void setRcauid(String rcauid) {
        this.rcauid = rcauid;
    }

    public String getLocke_id() {
        return locke_id;
    }

    public void setLocke_id(String locke_id) {
        this.locke_id = locke_id;
    }

    public String getPtuid() {
        return ptuid;
    }

    public void setPtuid(String ptuid) {
        this.ptuid = ptuid;
    }

    public String getEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(String email_verified) {
        this.email_verified = email_verified;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getMc_brokerplatform_id() {
        return mc_brokerplatform_id;
    }

    public void setMc_brokerplatform_id(String mc_brokerplatform_id) {
        this.mc_brokerplatform_id = mc_brokerplatform_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_login_status='" + user_login_status + '\'' +
                ", my_rea_id='" + my_rea_id + '\'' +
                ", my_rea_anon_id='" + my_rea_anon_id + '\'' +
                ", reauid='" + reauid + '\'' +
                ", my_rca_id='" + my_rca_id + '\'' +
                ", pcauid='" + pcauid + '\'' +
                ", rcauid='" + rcauid + '\'' +
                ", locke_id='" + locke_id + '\'' +
                ", ptuid='" + ptuid + '\'' +
                ", email_verified='" + email_verified + '\'' +
                ", country_code='" + country_code + '\'' +
                ", mc_brokerplatform_id='" + mc_brokerplatform_id + '\'' +
                '}';
    }
}
