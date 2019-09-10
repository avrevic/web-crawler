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
 *
 * @author avrevic
 */
public class WebCrawler implements ICrawler {

    private String url;
    private List<String> disabledUrls;
    private HashMap<Integer, HashMap<String, Boolean>> siteUrls;
    @Inject
    private UrlUtil urlUtil;

    /**
     *
     * @return
     */
    public String getUrl() {
        return this.url;
    }

    /**
     *
     * @return
     */
    public List<String> getDisabledUrls() {
        return this.disabledUrls;
    }

    /**
     *
     * @return
     */
    public HashMap<Integer, HashMap<String, Boolean>> getSiteUrls() {
        return this.siteUrls;
    }

    /**
     *
     * @param url
     */
    public void initializeParams(String url) {
        this.url = url;
        this.disabledUrls = new ArrayList<>();
        this.siteUrls = new HashMap<>();
        Injector injector = Guice.createInjector(new BasicModule());
        this.urlUtil = injector.getInstance(UrlUtil.class);
    }

    /**
     *
     * @return @throws Exception
     */
    @Override
    public HashMap<Integer, HashMap<String, Boolean>> crawl() throws Exception {
        this.populateDisabledSites();
        fetchAllLinks(this.url, 0);
        return this.getSiteUrls();
    }

    /**
     *
     * @param url
     * @param level
     * @throws MalformedURLException
     * @throws IOException
     */
    public void fetchAllLinks(String url, Integer level) throws MalformedURLException, IOException {
        Document doc;
        if (!urlUtil.checkHostUrlEquality(this.url, url)) {
            System.out.println("External link");
            return;
        }
        String path = urlUtil.fetchUrlPath(url);
        Integer hierachyLevel = StringUtils.countMatches(path, "/");
        if (this.siteUrls.get(hierachyLevel) != null && this.siteUrls.get(hierachyLevel).get(url) != null) {
            System.out.println("Link already in the hierarchy table");
            return;
        }
        if (disabledUrls.contains(StringUtils.stripEnd(StringUtils.stripStart(path, "/"), "/"))) {
            System.out.println("Link crawling is disabled by robots");
            return;
        }
        if (this.siteUrls.get(hierachyLevel) == null) {
            HashMap<String, Boolean> newUrlBranch = new HashMap<>();
            newUrlBranch.put(url, Boolean.TRUE);
            this.siteUrls.put(level, newUrlBranch);
        } else {
            this.siteUrls.get(hierachyLevel).put(url, Boolean.TRUE);
        }
        doc = Jsoup.parse(Jsoup.connect(url).get().toString());
        Elements links = doc.select("a[href]");

        for (Element link : links) {
            String href = link.attr("href");
            try {
                new URL(href);
                fetchAllLinks(href, level + 1);
            } catch (MalformedURLException ex) {
                fetchAllLinks(this.url + "/" + href, level + 1);
            }
        }
    }

    /**
     *
     * @return @throws Exception
     */
    public String fetchRobots() throws Exception {
        Document doc = Jsoup.parse(Jsoup.connect(this.url + "/robots.txt").get().toString());
        return doc.select("body").html();
    }

    /**
     *
     * @throws Exception
     */
    public void populateDisabledSites() throws Exception {
        String robotsFile[] = this.fetchRobots().split("\\r?\\n");
        for (String line : robotsFile) {
            Integer slashIndex = line.indexOf("/");
            if (slashIndex != -1 && !this.disabledUrls.contains(line.substring(slashIndex))) {
                this.disabledUrls.add(StringUtils.stripEnd(StringUtils.stripStart(line.substring(slashIndex), "/"), "/"));
            }
        }
    }

}
