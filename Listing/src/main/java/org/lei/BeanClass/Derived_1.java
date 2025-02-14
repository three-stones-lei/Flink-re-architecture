package org.lei.BeanClass;

/**
 * ClassName: Derived_1
 * Package: org.lei.BeanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 10:33 am
 * @Version 1.0
 */
public class Derived_1 {
    private Listing listing;
    private String ingestedAt;

    public Derived_1() {
    }

    public Derived_1(Listing listing, String ingestedAt) {
        this.listing = listing;
        this.ingestedAt = ingestedAt;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public String getIngestedAt() {
        return ingestedAt;
    }

    public void setIngestedAt(String ingestedAt) {
        this.ingestedAt = ingestedAt;
    }

    @Override
    public String toString() {
        return "Derived_1{" +
                "listing=" + listing +
                ", ingestedAt='" + ingestedAt + '\'' +
                '}';
    }
}
