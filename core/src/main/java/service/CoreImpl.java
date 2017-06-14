package service;

import model.Website;
import model.WebsiteEntity;

import javax.jws.WebService;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Yannic on 13-6-17.
 */
@WebService(endpointInterface = "service.CoreService")
public class CoreImpl implements CoreService {

    private static final Logger LOGGER = Logger.getLogger(CoreImpl.class.getName());


    @Override
    public Website getWebsite(String url) {
        Crawler crawler;
        try {
            crawler = new Crawler(url);
            WebsiteEntity websiteEntity = crawler.getStartWebsite();
            Website modelSite = new Website();
            modelSite.setDate(websiteEntity.getDate().getTime());
            modelSite.setUrl(websiteEntity.getUrl());
            modelSite.setWebsiteId(websiteEntity.getWebsiteId());
            return modelSite;

        } catch (SocketTimeoutException e) {
            LOGGER.log(Level.WARNING, "Site took to long to respond. \n" + e.getMessage());
        } catch (MalformedURLException e) {
            LOGGER.log(Level.WARNING, "Malformed url. \n" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
