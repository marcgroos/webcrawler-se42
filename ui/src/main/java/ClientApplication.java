import model.Website;
import service.CoreService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
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

            Website website = impl.getWebsite("http://www.google.com");
            System.out.println(new Timestamp(website.getDate()).toLocalDateTime().toString() + " - " + website.getUrl());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
