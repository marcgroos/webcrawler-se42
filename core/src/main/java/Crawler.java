import model.Page;
import model.ResourceEntity;
import model.WebsiteEntity;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
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

    private static final Logger LOGGER = Logger.getLogger(Crawler.class.getName());

    private WebsiteEntity startWebsite;
    private Map<String, WebsiteEntity> visitedWebsites;
    private Map<String, List<ResourceEntity>> resourceMap;
    private String startUrl;
    //the string to the root folder of app, used for saving temp files
    private String resourceDir;

    public Crawler(String startUrl) throws MalformedURLException, SocketTimeoutException {
        this.startUrl = startUrl;
        visitedWebsites = new HashMap<>();
        startWebsite = start();
        resourceDir = System.getProperty("user.dir") + "/core/src/main/resources/foundFiles";
    }

    /**
     * This method retrieves all the resources it can find on the given website.
     *
     * @param website
     * @return
     */
    public List<ResourceEntity> getResourcesForWebsite(WebsiteEntity website, Boolean saveToDisk) {
        //todo separate audio, video and photo types.
        Elements sourcesElement = website.getPage().getDocument().select("[src]");
        String filenamePrefix = website.getPage().getSimpleUrl() + "_";

        List<ResourceEntity> resources = new ArrayList<>();

        for (Element source : sourcesElement) {
            try {
                //prepare file and source
                URL url = new URL(source.attr("abs:src"));
                ResourceEntity resource = checkAndCreateResource(url);
                //if preferred, save file to disk
                if (saveToDisk) {
                    DLFileFromResource(resource, filenamePrefix);
                }
                resources.add(resource);

            } catch (MalformedURLException e1) {
                LOGGER.log(Level.WARNING, e1.getCause() + e1.getMessage());
            }
        }
        return resources;
    }

    private ResourceEntity checkAndCreateResource(URL url) {
        //todo
        String fileName = url.getFile();
        String extension = fileName.substring(fileName.lastIndexOf("."));

        switch (extension){
            case ".mp3":
                break;
            case ".png":
            case ".jpg":
                break;
            case ".mp4":
                break;
            default:
                break;
        }

    }

    private Boolean DLFileFromResource(ResourceEntity resource, String fileTemplate) {


        String filename = resource.getURL() + resource.getName();
        OutputStream fileOut = null;
        BufferedInputStream buffIn = null;
        try {
            //prepare download
            File file = new File(resourceDir + filename);
            file.mkdirs();
            fileOut = new FileOutputStream(file);
            buffIn = new BufferedInputStream(resource.getURL().openStream());
            //download the file.
            int i;
            while ((i = buffIn.read()) != -1) {
                fileOut.write(i);
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while downloading file: " + filename + "\n" + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeQuietly(fileOut);
            closeQuietly(buffIn);
        }
        LOGGER.log(Level.FINEST, "Saved file to: " + resourceDir);
        return true;
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
