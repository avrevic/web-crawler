package com.avrevic.babylon.health.challenge;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Crawler implementation that gets the sitemap in a hierarchical structure
 */
public class WebCrawler implements ICrawler {

    private String url;
    private List<String> disabledUrls;
    private HashMap<Integer, HashMap<String, Boolean>> siteUrls;
    @Inject
    private UrlUtil urlUtil;

    /**
     * Get the root url which we want to crawl
     *
     * @return
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Fetch the list of disabled urls
     *
     * @return
     */
    public List<String> getDisabledUrls() {
        return this.disabledUrls;
    }

    /**
     * Fetch the sitemap
     *
     * @return
     */
    public HashMap<Integer, HashMap<String, Boolean>> getSiteUrls() {
        return this.siteUrls;
    }

    /**
     * Initialize class params
     *
     * @param url url which we want to crawl
     */
    public void initializeParams(String url) {
        this.url = url;
        this.disabledUrls = new ArrayList<>();
        this.siteUrls = new HashMap<>();
        Injector injector = Guice.createInjector(new BasicModule());
        this.urlUtil = injector.getInstance(UrlUtil.class);
    }

    /**
     * Crawl!
     *
     * @return Hierarchical list of URLs
     * @throws Exception
     */
    @Override
    public HashMap<Integer, HashMap<String, Boolean>> crawl() throws Exception {
        this.populateDisabledSites();
        fetchAllLinks(this.url);
        return this.getSiteUrls();
    }

    /**
     * Get all links from the website, but first add the source url to the list
     * if it is not in the list. Recursive function
     *
     * @param url root url from which we want to get the sitemap
     * @throws MalformedURLException
     * @throws IOException
     */
    public void fetchAllLinks(String url) throws MalformedURLException, IOException {
        Document doc;
        if (!urlUtil.isSourceHostURLEqualTarget(this.url, url)) {
            System.out.println("External link");
            return;
        }
        String path = urlUtil.getUrlPath(url);
        Integer hierachyLevel = StringUtils.countMatches(path, "/");
        if (this.siteUrls.get(hierachyLevel) != null && this.siteUrls.get(hierachyLevel).get(url) != null) {
            System.out.println("Link already in the hierarchy table");
            return;
        }
        if (disabledUrls.contains(StringUtils.stripEnd(StringUtils.stripStart(path, "/"), "/"))) {
            System.out.println("Link crawling is disabled by robots");
            return;
        }
        // Initialize tha hashmap
        if (this.siteUrls.get(hierachyLevel) == null) {
            HashMap<String, Boolean> newUrlBranch = new HashMap<>();
            newUrlBranch.put(url, Boolean.TRUE);
            this.siteUrls.put(hierachyLevel, newUrlBranch);
        } else {
            this.siteUrls.get(hierachyLevel).put(url, Boolean.TRUE);
        }
        doc = Jsoup.parse(Jsoup.connect(url).get().toString());
        Elements links = doc.select("a[href]");

        for (Element link : links) {
            String href = link.attr("href");
            try {
                // See if it is a valid URL - if yes, then it is a fully
                // qualified URL and not only a path
                new URL(href);
                fetchAllLinks(href);
            } catch (MalformedURLException ex) {
                // Not a fully qualified url, only a path
                fetchAllLinks(this.url + "/" + href);
            }
        }
    }

    /**
     * Fetch the robots file so that we can add the disabled urls to the list we
     * do not want to crawl
     *
     * @return HTML document
     * @throws Exception
     */
    public String fetchRobots() throws Exception {
        Document doc = Jsoup.parse(Jsoup.connect(this.url + "/robots.txt").get().toString());
        return doc.select("body").html();
    }

    /**
     * Parse the robots file and add the disabled urls list - we do not want to
     * crawl them or we might get banned
     *
     * @throws Exception
     */
    public void populateDisabledSites() throws Exception {
        String robotsFile[] = this.fetchRobots().split("\\r?\\n");
        for (String line : robotsFile) {
            // Find where the slash ('/') starts - from that point we want to get the path of URL
            Integer slashIndex = line.indexOf("/");
            if (slashIndex != -1 && !this.disabledUrls.contains(line.substring(slashIndex))) {
                this.disabledUrls.add(StringUtils.stripEnd(StringUtils.stripStart(line.substring(slashIndex), "/"), "/"));
            }
        }
    }

}
