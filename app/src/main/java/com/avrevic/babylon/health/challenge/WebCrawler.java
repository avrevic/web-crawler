package com.avrevic.babylon.health.challenge;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Crawler implementation that gets the sitemap in a hierarchical structure
 */
public class WebCrawler implements ICrawler, Runnable {

    private String url;
    private static volatile List<String> disabledUrls;
    private static volatile HashMap<Integer, Set<String>> siteUrls;
    private static volatile Integer threadCount = 0;
    private String threadHref = "";

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
        return WebCrawler.disabledUrls;
    }

    /**
     * Fetch the sitemap
     *
     * @return
     */
    public HashMap<Integer, Set<String>> getSiteUrls() {
        return WebCrawler.siteUrls;
    }

    /**
     * Initialize class params
     *
     * @param url url which we want to crawl
     */
    @Override
    public void initializeParams(String url, Boolean initializeSiteList) {
        this.url = url;
        if (initializeSiteList) {
            WebCrawler.siteUrls = new HashMap<>();
            WebCrawler.disabledUrls = new ArrayList<>();
        }
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
    public HashMap<Integer, Set<String>> crawl() throws Exception {
        try {
            this.populateDisabledSites();
        } catch (Exception ex) {
        }
        fetchAllLinks(this.url);
        return this.getSiteUrls();
    }

    public Boolean urlNeedsFetching(String path, Integer hierachyLevel, String url) throws MalformedURLException {
        if (WebCrawler.siteUrls.get(hierachyLevel) != null) {
            for (String storedUrl : WebCrawler.siteUrls.get(hierachyLevel)) {
                String pathStored = urlUtil.getUrlPath(storedUrl);
                //Link already in the hierarchy table
                if (pathStored.contains(path)) {
                    return false;
                }
            }
        }

        if (!urlUtil.isSourceHostURLEqualTarget(this.url, url)) {
            //External link
            return false;
        }
        if (WebCrawler.disabledUrls.contains(StringUtils.stripEnd(StringUtils.stripStart(path, "/"), "/"))) {
            //Link crawling is disabled by robots
            return false;
        }

        return true;
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

        url = StringUtils.stripEnd(url, "/");
        String path = urlUtil.getUrlPath(url);
        Integer hierachyLevel = StringUtils.countMatches(path, "/");

        if (!urlNeedsFetching(path, hierachyLevel, url)) {
            return;
        }
        
        // Initialize tha hashmap
        if (WebCrawler.siteUrls.get(hierachyLevel) == null) {
            Set<String> newUrlBranch = new HashSet<>();
            newUrlBranch.add(url);
            WebCrawler.siteUrls.put(hierachyLevel, newUrlBranch);
        } else {
            WebCrawler.siteUrls.get(hierachyLevel).add(url);
        }
        try {
            doc = Jsoup.parse(Jsoup.connect(url).get().toString());
        } catch (Exception ex) {
            return;
        }
        Elements links = doc.select("a[href]");
        System.out.println("href fetch complete for url: " + url);
        List<Thread> threadList = new ArrayList<>();
        for (Element link : links) {
            String href = link.attr("href");
            try {
                // See if it is a valid URL - if yes, then it is a fully
                // qualified URL and not only a path
                new URL(href);
            } catch (MalformedURLException ex) {
                // Not a fully qualified url, only a path
                // TODO refactor this, looks shitty
                href = StringUtils.stripEnd(StringUtils.stripEnd(this.url, "/") + "/" + StringUtils.stripStart(href, "/"), "/");
            }

            path = urlUtil.getUrlPath(href);
            hierachyLevel = StringUtils.countMatches(path, "/");
            
            if (!urlNeedsFetching(path, hierachyLevel, href)) {
                continue;
            }
            
            if (WebCrawler.threadCount > 150) {
                fetchAllLinks(href);
            } else {
                WebCrawler.threadCount++;
                WebCrawler newThread = new WebCrawler();
                newThread.initializeParams(this.url, false);
                newThread.threadHref = href;
                Thread thread = new Thread(newThread, "New thread");
                threadList.add(thread);
                newThread.run();
            }
        }
        for (Thread crawler : threadList) {
            try {
                crawler.join();
                WebCrawler.threadCount--;
            } catch (InterruptedException ex) {
                Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
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
            if (slashIndex != -1 && !WebCrawler.disabledUrls.contains(line.substring(slashIndex))) {
                WebCrawler.disabledUrls.add(StringUtils.stripEnd(StringUtils.stripStart(line.substring(slashIndex), "/"), "/"));
            }
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Running thread for: " + this.threadHref);
            fetchAllLinks(this.threadHref);
        } catch (IOException ex) {
            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
