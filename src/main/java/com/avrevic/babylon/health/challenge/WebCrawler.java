/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avrevic.babylon.health.challenge;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public String getUrl() {
        return this.url;
    }

    public List<String> getDisabledUrls() {
        return this.disabledUrls;
    }

    public HashMap<Integer, HashMap<String, Boolean>> getSiteUrls() {
        return this.siteUrls;
    }

    @Override
    public void initializeParams(String url) {
        this.url = url;
        this.disabledUrls = new ArrayList<>();
        this.siteUrls = new HashMap<>();
        Injector injector = Guice.createInjector(new BasicModule());
        this.urlUtil = injector.getInstance(UrlUtil.class);
    }

    @Override
    public void crawl() {
        try {
            this.populateDisabledSites();
            fetchAllLinks(this.url, 0);
        } catch (Exception ex) {
            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fetchAllLinks(String url, Integer level) {
        Document doc;
        try {
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

        } catch (Exception ex) {
            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String fetchRobots() throws Exception {
        Document doc = Jsoup.parse(Jsoup.connect(this.url + "/robots.txt").get().toString());
        return doc.select("body").html();
    }

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
