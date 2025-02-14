package org.lei.beanClass;

/**
 * ClassName: Click_through_source_2
 * Package: org.lei.beanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 12:29 pm
 * @Version 1.0
 */
public class Click_through_source_2 {
    private String page;
    private String element;
    private String source_type;
    private Media media;
    private String additional_field_key;
    private String additional_field_value;

    public Click_through_source_2() {
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getAdditional_field_key() {
        return additional_field_key;
    }

    public void setAdditional_field_key(String additional_field_key) {
        this.additional_field_key = additional_field_key;
    }

    public String getAdditional_field_value() {
        return additional_field_value;
    }

    public void setAdditional_field_value(String additional_field_value) {
        this.additional_field_value = additional_field_value;
    }

    @Override
    public String toString() {
        return "Click_through_source_2{" +
                "page='" + page + '\'' +
                ", element='" + element + '\'' +
                ", source_type='" + source_type + '\'' +
                ", media=" + media +
                ", additional_field_key='" + additional_field_key + '\'' +
                ", additional_field_value='" + additional_field_value + '\'' +
                '}';
    }
}
