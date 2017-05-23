import model.Page;
import model.WebsiteEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.HTMLParser;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * the class responsible for crawling through the web, it uses HTMLparser to look at websites.
 * this class saves crawled websites to not create double entries and circular references.
 * Created by Marc on 10-5-2017.
 * @author Marc
 * @author Yannic
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

    private void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     * this method parses a URL and returns a page
     * @return a Page
     */
    private Page parseUrl(String siteUrl) throws Exception{

        Document doc = Jsoup.connect(siteUrl).get();
        Elements links = doc.select("a");
        Page page = new Page(siteUrl, doc);
        return page;
    }

    /**
     * Creates the first website entity with the entered parameters
     * @return a WebsiteEntity or null if errors occur
     */
    public WebsiteEntity start() {
        try {
            Page page = parseUrl(startUrl);
            return new WebsiteEntity(page);

        } catch (Exception e){
            Logger.getLogger(this.getClass().toString()).log(Level.WARNING, "Unknown error while parsing URL");
        }
        return null;
    }

    /**
     * loops through the list of external links in the given website and parses their HTML;
     * @param website
     * @return A list of Websites.
     */
    public List<WebsiteEntity> getExternalSitesFromSite(WebsiteEntity website){
        return null;
    }
}
