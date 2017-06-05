import model.Page;
import model.WebsiteEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * the class responsible for crawling through the web, it uses Jsoup (library) to look at websites.
 * this class saves crawled websites to not create double entries and circular references.
 * Created by Marc on 10-5-2017.
 *
 * @author Marc
 * @author Yannic
 */
public class Crawler {

    private static final Logger LOGGER = Logger.getLogger(Crawler.class.getName());

    private WebsiteEntity startWebsite;
    private Map<String, WebsiteEntity> visitedWebsites;
    private String startUrl;

    public Crawler(String startUrl) throws MalformedURLException, SocketTimeoutException{
        this.startUrl = startUrl;
        visitedWebsites = new HashMap<>();
        startWebsite = start();
    }

    public Map<String, WebsiteEntity> getVisitedWebsites() {
        return visitedWebsites;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public WebsiteEntity getStartWebsite() {
        return startWebsite;
    }

    /**
     * Creates the first website entity with the entered parameters
     * This method expects the homepage of a website (e.g: http://www.stackoverflow.com).
     * @return a WebsiteEntity or null if errors occur  
     */
    ;

    private WebsiteEntity start() throws MalformedURLException, SocketTimeoutException{
        try {
            Page page = new Page(startUrl);
            //add website to visited sites.
            WebsiteEntity site = new WebsiteEntity(page);
            visitedWebsites.put(page.getTrimUrl(true), site);
            return site;

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unknown error while parsing url");

        } catch (IllegalArgumentException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new MalformedURLException();
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error occured while creating site");
        }
        return null;
    }

    /**
     * loops through the list of external links in the given website and parses their HTML;
     *
     * @param website
     * @return A list of Websites.
     */
    public List<WebsiteEntity> getExternalSitesFromSite(WebsiteEntity website) {

        List<WebsiteEntity> extSites = new ArrayList<>();

        Page homePage = website.getPage();
        homePage.initPageSites();
        Set<String> pages = homePage.getExtPages();
        for (String url : pages) {
            //check if website is visited, if so, return that one.
            Page tempPage = null;
            try {
                tempPage = new Page(url);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Error parsing url: " + url + "\n" + e.getMessage(), e);
                continue;
            } catch (IllegalArgumentException e) {
                LOGGER.log(Level.WARNING, "Invalid URL: " + url + "\n" + e.getMessage());
                continue;
            }
            if (isKnownDomain(tempPage)) {
                //add the the url to the already existing website's Set of urls
                visitedWebsites.get(tempPage.getTrimUrl(true)).getPage().addSubPage(url);
            } else {
                //create a new website with the trimmed url, and new HomePage.
                String trimUrl = tempPage.getTrimUrl(true);
                try {
                    visitedWebsites.put(trimUrl, new WebsiteEntity(new Page(trimUrl)));
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Error getting homepage: " + trimUrl);
                    visitedWebsites.remove(trimUrl);
                }
            }
        }
        return extSites;
    }

    private boolean isKnownDomain(Page page) {
        if (visitedWebsites.containsKey(page.getTrimUrl(true))) {
            return true;
        } else {
            return false;
        }
    }
}
