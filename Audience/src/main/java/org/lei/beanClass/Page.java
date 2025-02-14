package org.lei.beanClass;

/**
 * ClassName: Page
 * Package: org.lei.beanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 12:26 pm
 * @Version 1.0
 */
public class Page {
    private String page_name;
    private String page_type;
    private String site;
    private String site_section;
    private String site_sub_section;
    private String site_sub_sub_section;
    private String language;
    private String platform;
    private String responsive_layout;
    private String rendered_on;
    private String click_through_source;
    private String article_name;
    private String variant_name;
    private String site_sub_sub_sub_section;
    private Click_through_source_2 click_through_source_2;
    private String breadcrumbs;

    public Page() {
    }

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public String getPage_type() {
        return page_type;
    }

    public void setPage_type(String page_type) {
        this.page_type = page_type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSite_section() {
        return site_section;
    }

    public void setSite_section(String site_section) {
        this.site_section = site_section;
    }

    public String getSite_sub_section() {
        return site_sub_section;
    }

    public void setSite_sub_section(String site_sub_section) {
        this.site_sub_section = site_sub_section;
    }

    public String getSite_sub_sub_section() {
        return site_sub_sub_section;
    }

    public void setSite_sub_sub_section(String site_sub_sub_section) {
        this.site_sub_sub_section = site_sub_sub_section;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getResponsive_layout() {
        return responsive_layout;
    }

    public void setResponsive_layout(String responsive_layout) {
        this.responsive_layout = responsive_layout;
    }

    public String getRendered_on() {
        return rendered_on;
    }

    public void setRendered_on(String rendered_on) {
        this.rendered_on = rendered_on;
    }

    public String getClick_through_source() {
        return click_through_source;
    }

    public void setClick_through_source(String click_through_source) {
        this.click_through_source = click_through_source;
    }

    public String getArticle_name() {
        return article_name;
    }

    public void setArticle_name(String article_name) {
        this.article_name = article_name;
    }

    public String getVariant_name() {
        return variant_name;
    }

    public void setVariant_name(String variant_name) {
        this.variant_name = variant_name;
    }

    public String getSite_sub_sub_sub_section() {
        return site_sub_sub_sub_section;
    }

    public void setSite_sub_sub_sub_section(String site_sub_sub_sub_section) {
        this.site_sub_sub_sub_section = site_sub_sub_sub_section;
    }

    public Click_through_source_2 getClick_through_source_2() {
        return click_through_source_2;
    }

    public void setClick_through_source_2(Click_through_source_2 click_through_source_2) {
        this.click_through_source_2 = click_through_source_2;
    }

    public String getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(String breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    @Override
    public String toString() {
        return "Page{" +
                "page_name='" + page_name + '\'' +
                ", page_type='" + page_type + '\'' +
                ", site='" + site + '\'' +
                ", site_section='" + site_section + '\'' +
                ", site_sub_section='" + site_sub_section + '\'' +
                ", site_sub_sub_section='" + site_sub_sub_section + '\'' +
                ", language='" + language + '\'' +
                ", platform='" + platform + '\'' +
                ", responsive_layout='" + responsive_layout + '\'' +
                ", rendered_on='" + rendered_on + '\'' +
                ", click_through_source='" + click_through_source + '\'' +
                ", article_name='" + article_name + '\'' +
                ", variant_name='" + variant_name + '\'' +
                ", site_sub_sub_sub_section='" + site_sub_sub_sub_section + '\'' +
                ", click_through_source_2=" + click_through_source_2 +
                ", breadcrumbs='" + breadcrumbs + '\'' +
                '}';
    }
}
