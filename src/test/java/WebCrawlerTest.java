/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.avrevic.babylon.health.challenge.WebCrawler;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author avrevic
 */
public class WebCrawlerTest {

    private WebCrawler initializeCrawler(String path) {
        WebCrawler crawler = new WebCrawler();
        crawler.initializeParams(path);
        return crawler;
    }

    @Test
    public void robotsFileShouldBeLoadedSuccessfully() throws Exception {
        WebCrawler crawler = initializeCrawler("http://localhost");
        String robotsFile = crawler.fetchRobots();
        Assert.assertEquals("User-agent: * Disallow: /vendor/", robotsFile);

        crawler.initializeParams("https://www.google.com");
        robotsFile = crawler.fetchRobots();
        Assert.assertNotNull(robotsFile);
    }

    @Test(expected = Exception.class)
    public void invalidRobotsFileShouldThrowException() throws Exception {
        String path = ("not_valid_url");
        WebCrawler crawler = initializeCrawler(path);
        crawler.fetchRobots();
    }

    @Test
    public void shouldParseDissalowedSitesCorrectly() throws Exception {
        WebCrawler crawler = initializeCrawler("http://localhost");
        crawler.populateDisabledSites();
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("/vendor/");
        Assert.assertEquals(expectedValues, crawler.getDisabledUrls());
    }

    @Test
    public void propertiesShouldBeInitializedSuccessfully() {
        WebCrawler crawler = initializeCrawler("http://localhost");
        Assert.assertNotNull(crawler.getUrl());
    }

    /*
    @Test
    public void shouldReturnCorrectLinkHierarchy() {
        HashMap<Integer, Set<String>> testUrls = new HashMap<>();
        Set<String> urlLinks = new HashSet<>();
        urlLinks.add("http://localhost");
        testUrls.put(0, urlLinks);
        urlLinks = new HashSet<>();
        urlLinks.add("page2.html");
        testUrls.put(1, urlLinks);
        WebCrawler crawler = initializeCrawler("http://localhost");
        crawler.crawl();
        Assert.assertEquals(testUrls, crawler.getSiteUrls());
    }*/
}
