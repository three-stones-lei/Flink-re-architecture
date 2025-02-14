package org.lei.beanClass;

/**
 * ClassName: Listing
 * Package: org.lei.beanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 12:48 pm
 * @Version 1.0
 */
public class Listing {
    private String listing_id;
    private String agency_id;
    private String marketing_price_range;
    private String price;
    private String construction_status;
    private String product_depth;
    private String listing_type;
    private String parent_listing_id;
    private boolean is_project;
    private String customer_lob;
    private String  tenure_type;
    private String authority;
    private String building_id;
    private String provider_name;
    private String client_listing_id;
    private boolean ire_integration_exists;
    private int inspections_count;
    private int  ire_inspection_count;
    private int ofi_inspection_count;
    private String status;
    private String source;

    public Listing() {
    }

    public String getListing_id() {
        return listing_id;
    }

    public void setListing_id(String listing_id) {
        this.listing_id = listing_id;
    }

    public String getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(String agency_id) {
        this.agency_id = agency_id;
    }

    public String getMarketing_price_range() {
        return marketing_price_range;
    }

    public void setMarketing_price_range(String marketing_price_range) {
        this.marketing_price_range = marketing_price_range;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getConstruction_status() {
        return construction_status;
    }

    public void setConstruction_status(String construction_status) {
        this.construction_status = construction_status;
    }

    public String getProduct_depth() {
        return product_depth;
    }

    public void setProduct_depth(String product_depth) {
        this.product_depth = product_depth;
    }

    public String getListing_type() {
        return listing_type;
    }

    public void setListing_type(String listing_type) {
        this.listing_type = listing_type;
    }

    public String getParent_listing_id() {
        return parent_listing_id;
    }

    public void setParent_listing_id(String parent_listing_id) {
        this.parent_listing_id = parent_listing_id;
    }

    public boolean isIs_project() {
        return is_project;
    }

    public void setIs_project(boolean is_project) {
        this.is_project = is_project;
    }

    public String getCustomer_lob() {
        return customer_lob;
    }

    public void setCustomer_lob(String customer_lob) {
        this.customer_lob = customer_lob;
    }

    public String getTenure_type() {
        return tenure_type;
    }

    public void setTenure_type(String tenure_type) {
        this.tenure_type = tenure_type;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getClient_listing_id() {
        return client_listing_id;
    }

    public void setClient_listing_id(String client_listing_id) {
        this.client_listing_id = client_listing_id;
    }

    public boolean isIre_integration_exists() {
        return ire_integration_exists;
    }

    public void setIre_integration_exists(boolean ire_integration_exists) {
        this.ire_integration_exists = ire_integration_exists;
    }

    public int getInspections_count() {
        return inspections_count;
    }

    public void setInspections_count(int inspections_count) {
        this.inspections_count = inspections_count;
    }

    public int getIre_inspection_count() {
        return ire_inspection_count;
    }

    public void setIre_inspection_count(int ire_inspection_count) {
        this.ire_inspection_count = ire_inspection_count;
    }

    public int getOfi_inspection_count() {
        return ofi_inspection_count;
    }

    public void setOfi_inspection_count(int ofi_inspection_count) {
        this.ofi_inspection_count = ofi_inspection_count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "listing_id='" + listing_id + '\'' +
                ", agency_id='" + agency_id + '\'' +
                ", marketing_price_range='" + marketing_price_range + '\'' +
                ", price='" + price + '\'' +
                ", construction_status='" + construction_status + '\'' +
                ", product_depth='" + product_depth + '\'' +
                ", listing_type='" + listing_type + '\'' +
                ", parent_listing_id='" + parent_listing_id + '\'' +
                ", is_project=" + is_project +
                ", customer_lob='" + customer_lob + '\'' +
                ", tenure_type='" + tenure_type + '\'' +
                ", authority='" + authority + '\'' +
                ", building_id='" + building_id + '\'' +
                ", provider_name='" + provider_name + '\'' +
                ", client_listing_id='" + client_listing_id + '\'' +
                ", ire_integration_exists=" + ire_integration_exists +
                ", inspections_count=" + inspections_count +
                ", ire_inspection_count=" + ire_inspection_count +
                ", ofi_inspection_count=" + ofi_inspection_count +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
