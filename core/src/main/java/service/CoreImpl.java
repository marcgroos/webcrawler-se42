package service;

import crypto.AES;
import entities.Website;
import entities.WebsiteEntity;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.jws.WebService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Yannic on 13-6-17.
 */
@WebService(endpointInterface = "service.CoreService")
public class CoreImpl implements CoreService {

    private static final Logger LOGGER = Logger.getLogger(CoreImpl.class.getName());


    @Override
    public byte[] getWebsite(String url) {
        Crawler crawler;
        try {
            crawler = new Crawler(url);
            WebsiteEntity websiteEntity = crawler.getStartWebsite();
            Website modelSite = new Website();
            modelSite.setDate(websiteEntity.getDate().getTime());
            modelSite.setUrl(websiteEntity.getUrl());
            modelSite.setWebsiteId(websiteEntity.getWebsiteId());

            return AES.encrypt(modelSite, AES.generateKey());

        } catch (SocketTimeoutException e) {
            LOGGER.log(Level.WARNING, "Site took to long to respond. \n" + e.getMessage());
        } catch (MalformedURLException e) {
            LOGGER.log(Level.WARNING, "Malformed url. \n" + e.getMessage());
            e.printStackTrace();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

}
