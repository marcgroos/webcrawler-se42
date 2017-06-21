import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.Crawler;


import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yannic on 5-6-17.
 */
public class CrawlerTest {

    private static final Logger LOGGER = Logger.getLogger(CrawlerTest.class.getName());

    private Crawler testCrawler;

    @Before
    public void Setup() {

    }

    @After
    public void Setdown() {

    }

    @Test
    public void startTest() {
        try {
            testCrawler = new Crawler("http://google.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
        assertNotNull(testCrawler.getStartWebsite().getPage());

    }

    @Test
            (expected = MalformedURLException.class)
    public void startTest2() throws MalformedURLException, SocketTimeoutException{
            testCrawler = new Crawler(".google.com");
    }

}
