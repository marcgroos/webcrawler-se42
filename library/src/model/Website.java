package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * This is the model class for usage with SOAP.
 * @author Yannic on 14-6-17.
 */
public class Website implements Serializable{
    private int websiteId;
    private Long dateInLong;
    private String url;

    public int getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(int websiteId) {
        this.websiteId = websiteId;
    }

    public Timestamp getDate() {
        return new Timestamp(dateInLong);
    }

    public void setDate(Timestamp date) {

        this.dateInLong = date.getTime();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString(){
        return websiteId + "_url";
    }
}
