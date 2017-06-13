package service;

import model.WebsiteEntity;

import javax.jws.WebService;
import javax.xml.soap.*;
import javax.xml.ws.Provider;
import javax.xml.ws.WebServiceProvider;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Yannic on 13-6-17.
 */
@WebService(endpointInterface = "service.MainService")
public class MainImpl implements MainService{

    private static final Logger LOGGER = Logger.getLogger(MainImpl.class.getName());


    @Override
    public WebsiteEntity getWebsite(String url) {
        Crawler crawler;
        try {
            crawler = new Crawler(url);
            return crawler.getStartWebsite();
        } catch (SocketTimeoutException e) {
            LOGGER.log(Level.WARNING, "Site took to long to respond. \n" + e.getMessage());
        } catch (MalformedURLException e) {
            LOGGER.log(Level.WARNING, "Malformed url. \n" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
