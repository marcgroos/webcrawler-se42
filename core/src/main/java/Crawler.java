import model.Page;
import model.ResourceEntity;
import model.WebsiteEntity;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * the class responsible for crawling through the web, it uses Jsoup (library) to look at websites.
 * this class saves crawled websites to not create double entries and circular references.
 * it also saves any audio or video resources it can find.
 * Created by Marc on 10-5-2017.
 *
 * @author Marc
 * @author Yannic
 */
public class Crawler {

    private static final Logger LOGGER = Logger.getLogger(Crawler.class.getName());

    private WebsiteEntity startWebsite;
    private Map<String, WebsiteEntity> visitedWebsites;
    private Map<String, List<ResourceEntity>> resourceMap;
    private String startUrl;

    public Crawler(String startUrl) throws MalformedURLException, SocketTimeoutException {
        this.startUrl = startUrl;
        visitedWebsites = new HashMap<>();
        startWebsite = start();
    }

    /**
     * This method retrieves all the resources it can find on the given website.
     *
     * @param website
     * @return
     */
    public List<ResourceEntity> getRecourcesForWebsite(WebsiteEntity website) {
        //todo separate audio, video and photo types. (maybe make new class with generics to solve this?)
        Elements images = website.getPage().getDocument().select("img");
        String filenamePrefix = website.getPage().getTrimUrl(false) + System.currentTimeMillis();
        List<ResourceEntity> resources = new ArrayList<>();

        for (Element img : images) {
            File file = null;
            try {

                URL url = new URL(img.attr("abs:src"));
                String filename = filenamePrefix + url.getFile();
                file = new File("/home/zeb/IdeaProjects/S44/webcrawler-se42/core/src/main/resources/foundFiles/" + filename);
                FileUtils.copyURLToFile(url, file);
                String test = file.getCanonicalPath();

                ResourceEntity resource = new ResourceEntity(file.getName(), url.toString(), (int) file.getTotalSpace());
                resources.add(resource);

            } catch (MalformedURLException e1) {
                LOGGER.log(Level.WARNING, e1.getCause() + e1.getMessage());
                continue;
            } catch (IOException e2) {
                LOGGER.log(Level.FINE, "Error downloading file " + e2.getMessage());
                e2.printStackTrace();
                continue;
            }
        }
        return resources;
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

    private WebsiteEntity start() throws MalformedURLException, SocketTimeoutException {
        try {
            Page page = new Page(startUrl);
            //add website to visited sites.
            WebsiteEntity site = new WebsiteEntity(page);
            visitedWebsites.put(page.getTrimUrl(true), site);
            return site;

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unknown error while parsing url");

        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new MalformedURLException();
        } catch (Exception e) {
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
