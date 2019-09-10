/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avrevic.babylon.health.challenge;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 *
 * @author avrevic
 */
public class BabylonHealthCrawler {

    public static void log(String message) {
        System.out.println(">>> " + message);
    }

    @Inject
    private ICrawler crawler;

    @Inject
    private ISiteMap siteMap;

    @Inject
    private IConfig config;

    public void run() throws Exception {
        Injector injector = Guice.createInjector(new BasicModule());
        this.crawler = injector.getInstance(ICrawler.class);
        this.config = injector.getInstance(IConfig.class);
        this.siteMap = injector.getInstance(ISiteMap.class);
        String url = this.config.getConfig(null).getProperty("website");
        this.crawler.initializeParams(url);
        siteMap.generateSitemap(url, this.crawler.crawl());
    }

    public static void main(String args[]) {
        try {
            new BabylonHealthCrawler().run();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}
