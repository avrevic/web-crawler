/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.avrevic.babylon.health.challenge.WebCrawler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    }

    private void urlEqualityCombinationsAssert(WebCrawler crawler, String source, String targetUrlString, boolean assertCheck, boolean targetProtocolSpecified) throws MalformedURLException {
        if (targetProtocolSpecified) {
            Assert.assertEquals(crawler.checkHostUrlEquality(source, targetUrlString), assertCheck);
            Assert.assertEquals(crawler.checkHostUrlEquality(source, targetUrlString + "/"), assertCheck);
            Assert.assertEquals(crawler.checkHostUrlEquality(source, targetUrlString + ":/"), assertCheck);
            Assert.assertEquals(crawler.checkHostUrlEquality(source, targetUrlString + ":80/"), assertCheck);
        } else {
            Assert.assertEquals(crawler.checkHostUrlEquality(source, "http://" + targetUrlString + ""), assertCheck);
            Assert.assertEquals(crawler.checkHostUrlEquality(source, "http://" + targetUrlString + "/"), assertCheck);
            Assert.assertEquals(crawler.checkHostUrlEquality(source, "http://" + targetUrlString + ":/"), assertCheck);
            Assert.assertEquals(crawler.checkHostUrlEquality(source, "http://" + targetUrlString + ":80/"), assertCheck);
            Assert.assertEquals(crawler.checkHostUrlEquality(source, "http://com." + targetUrlString + "/"), assertCheck);
            Assert.assertEquals(crawler.checkHostUrlEquality(source, "https://" + targetUrlString + ""), assertCheck);
            Assert.assertEquals(crawler.checkHostUrlEquality(source, "https://" + targetUrlString + "/"), assertCheck);
            Assert.assertEquals(crawler.checkHostUrlEquality(source, "https://" + targetUrlString + ":/"), assertCheck);
            Assert.assertEquals(crawler.checkHostUrlEquality(source, "https://" + targetUrlString + ":80/"), assertCheck);
            Assert.assertEquals(crawler.checkHostUrlEquality(source, "https://com." + targetUrlString + "/"), assertCheck);
        }
    }

    @Test
    public void urlsShouldBeEqual() throws MalformedURLException {
        String sourceUrlString = "http://example.com";
        String targetUrlString = "example.com";
        WebCrawler crawler = initializeCrawler(sourceUrlString);
        urlEqualityCombinationsAssert(crawler, sourceUrlString, targetUrlString, true, false);
        targetUrlString = "https://example.com";
        urlEqualityCombinationsAssert(crawler, sourceUrlString, targetUrlString, true, true);
        targetUrlString = "example.com/test";
        urlEqualityCombinationsAssert(crawler, sourceUrlString, targetUrlString, true, false);
        targetUrlString = "https://example.com/test";
        urlEqualityCombinationsAssert(crawler, sourceUrlString, targetUrlString, true, true);
    }

    @Test
    public void urlsShouldNotBeEqual() throws MalformedURLException {
        String sourceUrlString = "http://example.com";
        String targetUrlString = "http://google.com";
        WebCrawler crawler = initializeCrawler(sourceUrlString);
        urlEqualityCombinationsAssert(crawler, sourceUrlString, targetUrlString, false, true);
        targetUrlString = "google.com";
        urlEqualityCombinationsAssert(crawler, sourceUrlString, targetUrlString, false, false);
        targetUrlString = "google.com/test";
        urlEqualityCombinationsAssert(crawler, sourceUrlString, targetUrlString, false, false);
        targetUrlString = "http://google.com/test";
        urlEqualityCombinationsAssert(crawler, sourceUrlString, targetUrlString, false, true);
    }

}
