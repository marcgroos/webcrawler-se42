import model.WebsiteEntity;
import util.HTMLParser;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Marc on 10-5-2017.
 */
public class Crawler {

    private WebsiteEntity website;
    private String startUrl;
    private int maxDepth;

    private HTMLParser parser;

    public Crawler(String startUrl, int maxDepth) {
        this.startUrl = startUrl;
        this.maxDepth = maxDepth;
        parser = new HTMLParser();
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

    private WebsiteEntity parseUrl(String siteUrl) throws Exception{
        WebsiteEntity w = new WebsiteEntity();
        w.setUrl(siteUrl);
        parser.setUrl(siteUrl);

        //todo bedenk hoe pages worden aangemaakt, en hoe ze worden gebruikt.
        //setPageLinks kan de pages zelf aanmaken, is er dan ook een homepage page in website?
        //hoe worden pages in pages aan de website toegevoegd?
        //
        w.setPageLinks(parser.getPageLinks());
        w.setExtLinks(parser.getExtLinks());

        return null;
    }

    /**
     * Creates the first website entity with the entered parameters
     * @return a WebsiteEntity or null if errors occur
     */
    public WebsiteEntity start() {
        try {
            return parseUrl(startUrl);

        } catch (Exception e){
            Logger.getLogger(this.getClass().toString()).log(Level.WARNING, "Error while parsing URL");
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
