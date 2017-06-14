package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.Crawler;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

/**
 * @author Yannic on 13-6-17.
 */
public class ResourceManagerTest {
    //todo make tests for this class
    private Crawler testCrawler;
    @Before
    public void Setup(){
        testCrawler = new Crawler();
    }

    @After
    public void Teardown(){
        testCrawler = null;
    }

    @Test
    public void getImageResourceTest() throws MalformedURLException, SocketTimeoutException {
        WebsiteEntity website = testCrawler.getWebsite("http://www.banaanmetoor.nl");
        ResourceEntity resource = testCrawler.getResourcesForWebsite(website, false).get(0);
        resource.getSize();
    }
}
