/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avrevic.babylon.health.challenge;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author avrevic
 */
public class WebCrawler implements ICrawler {

    private String url;
    private List<String> disabledUrls;

    public String getUrl() {
        return this.url;
    }

    public List<String> getDisabledUrls() {
        return this.disabledUrls;
    }

    @Override
    public void initializeParams(String url) {
        this.url = url;
        this.disabledUrls = new ArrayList<>();
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

    public boolean checkHostUrlEquality(String source, String target) throws MalformedURLException {
        URL sourceUrl = new URL(source);
        URL targetUrl = new URL(target);
        String sourceUrlToCompare = sourceUrl.getHost();
        String targetUrlToCompare = targetUrl.getHost();
        if (sourceUrlToCompare != null && sourceUrlToCompare.startsWith("www.")) {
            sourceUrlToCompare = sourceUrlToCompare.substring(4);
        }
        if (targetUrlToCompare != null && targetUrlToCompare.startsWith("www.")) {
            targetUrlToCompare = targetUrlToCompare.substring(4);
        }
        if (sourceUrlToCompare.equals(targetUrlToCompare)) {
            return true;
        } else {
            targetUrlToCompare = targetUrlToCompare.substring(targetUrlToCompare.indexOf(".") + 1);
            if (sourceUrlToCompare.equals(targetUrlToCompare)) {
                return true;
            }
            return false;
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
                this.disabledUrls.add(line.substring(slashIndex));
            }
        }
    }

}
