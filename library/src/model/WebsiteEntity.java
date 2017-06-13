package model;

import constants.DBConstants;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Marc on 10-5-2017.
 */
@Entity
@Table(name = "website", schema = DBConstants.DB_NAME)
public class WebsiteEntity {

    private int websiteId;

    private Timestamp date;
    private String url;
    @XmlElement(name = "page")
    private Page page;

    public Page getPage() {
        return page;
    }

    public WebsiteEntity(Page homepage) {
        this.page = homepage;
        url = page.getUrl();
    }

    public WebsiteEntity() {
    }

    @Id
    @GeneratedValue
    @Column(name = "website_id", nullable = false)
    public int getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(int websiteId) {
        this.websiteId = websiteId;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "url")
    @XmlElement(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebsiteEntity that = (WebsiteEntity) o;

        if (websiteId != that.websiteId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = websiteId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

}
