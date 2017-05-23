package model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * this class contains the HTML of a page and the links to other pages.
 *
 * @author Yannic on 23-5-17.
 */
public class Page {
    private String url;
    private Document document;
    private List<String> subPages;
    private List<String> extPages;

    public Page(String url, Document document) {
        this.url = url;

        this.document = document;
        subPages = new ArrayList<>();
        extPages = new ArrayList<>();

        Elements links = document.select("a");
        for (Element e : links) {
            //todo distinguish between this and external domains.
            String link = e.attr("abs:href");
            System.out.println(link);
            subPages.add(link);
        }

    }

    public String getUrl() {
        return url;
    }

    public List<String> getSubPages() {
        return subPages;
    }

    public void setSubPages(List<String> subPages) {
        this.subPages = subPages;
    }

    public List<String> getExtPages() {
        return extPages;
    }

    public void setExtPages(List<String> extPages) {
        this.extPages = extPages;
    }
}
