package org.lei.beanClass;

/**
 * ClassName: Media
 * Package: org.lei.beanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 12:31 pm
 * @Version 1.0
 */
public class Media {
    private String url;
    private int index;
    private String type;

    public Media() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Media{" +
                "url='" + url + '\'' +
                ", index=" + index +
                ", type='" + type + '\'' +
                '}';
    }
}
