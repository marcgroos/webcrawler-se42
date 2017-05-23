import model.ImageEntity;
import model.WebsiteEntity;
import util.DBUtil;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Marc on 3-5-2017.
 */
public class Application {

    public static void main(String[] args) {
//        String startUrl = "http://stackoverflow.com/";
//        Crawler mainCrawler = new Crawler(startUrl, 2);
//        mainCrawler.start();

        EntityManager entityManager = DBUtil.getEntityManager();

        ImageEntity i = new ImageEntity("plaatje", "http://unium.nl/plaatje.jpg", 5983754, 640, 480);

        entityManager.getTransaction().begin();
        entityManager.persist(i);
        entityManager.getTransaction().commit();

    }

    private static void testPersistance() {
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
