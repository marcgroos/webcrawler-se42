package model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * this class contains the HTML of a page and the links to other pages.
 * @author Yannic on 23-5-17.
 */
public class Page {
    private Document document;
    private List<String> subPages;
    private List<String> extPages;

    public Page(Document document) {
        this.document = document;
        subPages = new ArrayList<>();
        extPages = new ArrayList<>();

        Elements links = document.select("a");
        for(Element e:links){
            subPages.add(e.attr("href"));
        }

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
