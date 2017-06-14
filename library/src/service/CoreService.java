package service;

import model.Website;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author Yannic on 13-6-17.
 */
@WebService
public interface CoreService {
    @WebMethod
    public Website getWebsite(String url);
}
