package model;

import constants.DBConstants;

import javax.persistence.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Marc on 23-5-2017.
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="resource_type")
@Table(name = "resource", schema = DBConstants.DB_NAME)
public class ResourceEntity {

    @Id
    @GeneratedValue
    @Column(name = "resource_id")
    private int resourceId;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "size")
    private int size;

    private static final Logger LOGGER = Logger.getLogger(ResourceEntity.class.getName());

    public ResourceEntity(String name, String url, int size) {
        this.name = name;
        this.url = url;
        this.size = size;
    }

    public ResourceEntity() {
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public URL getURL(){
        try {
            return new URL(getUrl());
        } catch (MalformedURLException e) {
            LOGGER.log(Level.SEVERE, "Unexpected error in URL: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceEntity that = (ResourceEntity) o;

        if (resourceId != that.resourceId) return false;
        if (size != that.size) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = resourceId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + size;
        return result;
    }
}
