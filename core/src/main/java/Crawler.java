import model.WebsiteEntity;

/**
 * Created by Marc on 10-5-2017.
 */
public class Crawler {

    private WebsiteEntity website;
    private String startUrl;
    private int maxDepth;

    public Crawler(String startUrl, int maxDepth) {
        this.startUrl = startUrl;
        this.maxDepth = maxDepth;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public WebsiteEntity start() {
        return null;
    }
}
