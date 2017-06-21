package entities;

import constants.DBConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Marc on 17-5-2017.
 */
@Entity
@DiscriminatorValue("audio")
@Table(name = "audio", schema = DBConstants.DB_NAME)
public class AudioEntity extends ResourceEntity {

    private int duration;

    public AudioEntity(String name, String url, int size, int duration) {
        super(name, url, size);
        this.duration = duration;
    }

    public AudioEntity(String name, String url, int size) {
        super(name, url, size);
    }

    public AudioEntity() {
    }
}
