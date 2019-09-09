/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avrevic.babylon.health.challenge;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String fetchRobots() throws Exception {
        URI u = URI.create(this.url + "/robots.txt");
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
