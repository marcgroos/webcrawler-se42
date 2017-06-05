import model.WebsiteEntity;
import util.DBUtil;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;

/**
 * Created by Marc on 3-5-2017.
 */
public class Application {

    public static void main(String[] args) {
        String startUrl = "http://google.com/";
        Crawler mainCrawler = null;
        try {
            mainCrawler = new Crawler(startUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
        WebsiteEntity rootSite = mainCrawler.getStartWebsite();
        List<WebsiteEntity> siteList = mainCrawler.getExternalSitesFromSite(rootSite);

    }

    private static void testPersistance(){
//        EntityManager entityManager = DBUtil.getEntityManager();
//
//        WebsiteEntity w = new WebsiteEntity();
//        w.setUrl("www.kaas.nl");
//
//
//        entityManager.getTransaction().begin();
//        entityManager.persist(w);
//        entityManager.getTransaction().commit();
    }

}
