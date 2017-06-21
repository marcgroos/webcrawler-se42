package entities;

import constants.DBConstants;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Marc on 17-5-2017.
 */
@Entity
@DiscriminatorValue("image")
@Table(name = "image", schema = DBConstants.DB_NAME)
public class ImageEntity extends ResourceEntity {

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    public ImageEntity(String name, String url, int size, int width, int height) {
        super(name, url, size);

        this.width = width;
        this.height = height;
    }

    public ImageEntity(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public ImageEntity() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
