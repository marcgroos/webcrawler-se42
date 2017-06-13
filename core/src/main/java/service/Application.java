package service;

import model.WebsiteEntity;

import javax.xml.ws.Endpoint;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

/**
 * Created by Marc on 3-5-2017.
 */
public class Application {

    public static void main(String[] args) {

        Endpoint.publish("http://localhost:8080/service/main", new MainImpl());



        /*        String startUrl = "http://google.com/";
        Crawler mainCrawler = null;
        try {
            mainCrawler = new Crawler(startUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
        WebsiteEntity rootSite = mainCrawler.getStartWebsite();
        mainCrawler.getResourcesForWebsite(rootSite, false);*/
        //List<WebsiteEntity> siteList = mainCrawler.getExternalSitesFromSite(rootSite);

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
