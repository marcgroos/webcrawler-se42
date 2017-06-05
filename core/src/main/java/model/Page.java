package model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a class uses to represent a page of a website.
 * Usage:
 * Initialize with constructor.
 * If you need the HTML of the page, call initPageDoc()
 * If you need all the separate links to other sites, call initPageSites()
 *
 * @author Yannic on 23-5-17.
 */
public class Page {

    private static final Logger LOGGER = Logger.getLogger(Page.class.getName());

    private String url;
    private Document document;
    private Set<String> subPages;
    private Set<String> extPages;

    /**
     * initializes a page with only a URL
     *
     * @param url
     */
    public Page(String url) throws IOException {
        this.url = url;
        subPages = new HashSet<>();
        extPages = new HashSet<>();
        initPageDoc();
    }

    private void initPageDoc() throws IOException {
        LOGGER.log(Level.INFO, "loading page: " + url);
        document = Jsoup.connect(url).get();
        //LOGGER.log(Level.INFO, "done loading");
    }

    public void initPageSites() {
        LOGGER.log(Level.INFO, "loading links for: " + url);
        Elements links = document.select("a");
        for (Element e : links) {

            String link = e.attr("abs:href");
            if (link.contains(getTrimUrl(false))) {
                subPages.add(link);
                //LOGGER.log(Level.INFO, "found page: sub " + link);
            } else {
                extPages.add(link);
                //LOGGER.log(Level.INFO, "found page: ext " + link);
            }
        }
        LOGGER.log(Level.INFO, "Done loading links, found " + extPages.size() + " external pages, and " + subPages.size() + " sub pages.");
    }

    public String getTrimUrl(boolean withPreFix) {
        int startIndex = url.indexOf("://");
        int endIndex = url.length();
        int domainEndIndex = url.indexOf("/", startIndex + 3);
        if (domainEndIndex > 0) {
            endIndex = domainEndIndex;
        }
        String out = url.substring(startIndex, endIndex);

        if (withPreFix) {
            return "http" + out;
        } else {
            return out;
        }

    }


    public String getUrl() {
        return url;
    }

    public Set<String> getSubPages() {
        return new HashSet<>(subPages);
    }

    public void addSubPage(String url) {
        //add url checking.
        subPages.add(url);
    }

    public Set<String> getExtPages() {
        return extPages;
    }

    public void setExtPages(Set<String> extPages) {
        this.extPages = extPages;
    }
}
