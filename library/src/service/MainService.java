package service;

import model.WebsiteEntity;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @author Yannic on 13-6-17.
 */
@WebService
public interface MainService {
    @WebMethod WebsiteEntity getWebsite(String url);
}
