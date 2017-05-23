package model;

import constants.DBConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Marc on 17-5-2017.
 */
@Entity
@DiscriminatorValue("video")
@Table(name = "video", schema = DBConstants.DB_NAME)
public class VideoEntity extends ResourceEntity {

    private int duration;
    private int width;
    private int height;

    public VideoEntity(String name, String url, int size, int duration, int width, int height) {
        super(name, url, size);

        this.duration = duration;
        this.width = width;
        this.height = height;
    }

    public VideoEntity(String name, String url, int size) {
        super(name, url, size);
    }

    public VideoEntity() {
    }
}