import model.WebsiteEntity;
import service.MainImpl;
import service.MainService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Marc on 3-5-2017.
 */
public class ClientApplication {
    public static void main(String[] args) {
        //System.out.println("No working UI yet");
        try {
            URL url = new URL("http://localhost:8080/service/main?wsdl");

            QName qname = new QName("http://service/", "MainImplService");
            Service service = Service.create(url, qname);
            MainService impl = service.getPort(MainService.class);

            WebsiteEntity website = impl.getWebsite("http://www.google.com");
            for(String site:website.getPage().getSubPages()){
                System.out.println(site + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
