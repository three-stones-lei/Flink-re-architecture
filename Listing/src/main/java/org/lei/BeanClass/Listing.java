package org.lei.BeanClass;

/**
 * ClassName: Listing
 * Package: org.lei.BeanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 10:35 am
 * @Version 1.0
 */
public class Listing {
    private String id;
    private String isPublishable;

    public Listing() {
    }

    public Listing(String id, String isPublishable) {
        this.id = id;
        this.isPublishable = isPublishable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsPublishable() {
        return isPublishable;
    }

    public void setIsPublishable(String isPublishable) {
        this.isPublishable = isPublishable;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id='" + id + '\'' +
                ", isPublishable='" + isPublishable + '\'' +
                '}';
    }
}
