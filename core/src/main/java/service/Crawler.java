package service;

import model.Page;
import model.ResourceEntity;
import model.WebsiteEntity;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.ResourceManager;

import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.io.IOUtils.closeQuietly;

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
    //todo make new extension of this class with search capabilities.
    private static final Logger LOGGER = Logger.getLogger(Crawler.class.getName());

    private WebsiteEntity startWebsite;
    private Map<String, WebsiteEntity> visitedWebsites;
    private Map<String, List<ResourceEntity>> resourceMap;
    private String startUrl;


    private ResourceManager downloader;


    /**
     * The constructor for this class, it creates the first Website automatically, if you need more websites you can use the methods to retrieve more.
     * @param startUrl the url to create the first Website out of, use http:// before url!
     * @throws MalformedURLException is thrown when the input cannot be interpreted as url.
     * @throws SocketTimeoutException is thrown when the response takes too long.
     */
    public Crawler(String startUrl) throws MalformedURLException, SocketTimeoutException {
        this.startUrl = startUrl;
        visitedWebsites = new HashMap<>();
        downloader = new ResourceManager();
        startWebsite = getWebsite(startUrl);

    }
    public Crawler(){
        startUrl = "";
        visitedWebsites = new HashMap<>();
        downloader = new ResourceManager();
        startWebsite = null;
    }


    /**
     * This method retrieves all the resources it can find on the given Website.
     *
     * @param website
     * @return
     */
    public List<ResourceEntity> getResourcesForWebsite(WebsiteEntity website, Boolean saveToDisk) {

        Elements sourcesElement = website.getPage().getDocument().select("[src]");

        List<ResourceEntity> resources = new ArrayList<>();

        for (Element source : sourcesElement) {
            try {
                //prepare file and source
                URL url = new URL(source.attr("abs:src"));
                ResourceEntity resource = checkAndMakeResource(url);
                //if preferred, save file to disk
                if (saveToDisk) {
                    downloader.saveResourceFile(resource);
                }

                if(resource != null){
                    resources.add(resource);
                }
            } catch (MalformedURLException e1) {
                LOGGER.log(Level.WARNING, e1.getCause() + e1.getMessage());
            }
        }
        return resources;
    }

    private ResourceEntity checkAndMakeResource(URL url) {
        //todo create method, fix something for getting properties for different filetypes.
        // todo test audio video and photo types.
        String fileName = url.getFile();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        ResourceEntity madeSource;
        switch (extension){
            case ".mp3":
            case ".wav":
                madeSource = downloader.getAudioResource(url);
                break;
            case ".png":
            case ".jpg":
                madeSource = downloader.getImageResource(url);
                break;
            case ".mp4":
                madeSource = downloader.getVideoResource(url);
                break;
            default:
                madeSource = downloader.getUnknownResource(url);
                break;
        }
        return madeSource;
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
     * Creates the a Website with the entered parameters
     * This method expects the homepage of a Website (e.g: http://www.stackoverflow.com).
     * @return a WebsiteEntity or null if errors occur  
     */
    ;

    public WebsiteEntity getWebsite(String url) throws MalformedURLException, SocketTimeoutException {
        try {
            Page page = new Page(url);
            //add Website to visited sites.
            WebsiteEntity site = new WebsiteEntity(page);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            site.setDate(timestamp);
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
     * loops through the list of external links in the given Website and parses their HTML;
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
            //check if Website is visited, if so, return that one.
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
                //add the the url to the already existing Website's Set of urls
                visitedWebsites.get(tempPage.getTrimUrl(true)).getPage().addSubPage(url);
            } else {
                //create a new Website with the trimmed url, and new HomePage.
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
