package util;

import model.WebsiteEntity;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * this class is responsible for parsing html.
 * it does things like:
 * find list of links, list of resources;
 *
 * Created by Yannic on 18-5-17.
 */
public class HTMLParser {
    private Document htmlDoc;

    public HTMLParser(){

    }

    public Document getWebsiteHTML(String url) throws MalformedURLException, HttpStatusException{
        try {
            htmlDoc = Jsoup.connect(url).get();
        } catch (IOException e){
            e.printStackTrace();
        }


        //todo bedenk hoe pages worden aangemaakt, en hoe ze worden gebruikt. = Pages are created by the Crawler;
        //setPageLinks kan de pages zelf aanmaken, is er dan ook een homepage page in website?
        //hoe worden pages in pages aan de website toegevoegd?

        //todo create website Entity;
        return htmlDoc;
    }

}
