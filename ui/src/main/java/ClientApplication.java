import crypto.AES;
import entities.Website;
import service.CoreService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

/**
 * Created by Marc on 3-5-2017.
 */
public class ClientApplication {
    public static void main(String[] args) {
        //System.out.println("No working UI yet");
        try {
            URL url = new URL("http://localhost:8080/service/main?wsdl");

            QName qname = new QName("http://service/", "CoreImplService");
            Service service = Service.create(url, qname);
            CoreService impl = service.getPort(CoreService.class);

            byte[] webBytes = impl.getWebsite("http://www.google.com");

            Website website = (Website) AES.decrypt(webBytes, AES.generateKey());

            System.out.println(website.getWebsiteId()+ " - " + new Timestamp(website.getDate()).toLocalDateTime().toString() + " - " + website.getUrl());

        } catch (NoSuchAlgorithmException | IOException | ClassNotFoundException | InvalidKeyException e) {
            e.printStackTrace();
        }

    }
}
