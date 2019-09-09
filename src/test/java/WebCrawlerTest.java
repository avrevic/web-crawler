/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.avrevic.babylon.health.challenge.WebCrawler;
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
        String path = ("file:/" + System.getProperty("user.dir") + "\\src\\test").replace("\\", "/");
        WebCrawler crawler = initializeCrawler(path);
        String robotsFile = crawler.fetchRobots();
        Assert.assertEquals(
                "User-agent: *\n"
                + "Disallow: /vendor/", robotsFile);

        path = "https://www.google.com";
        crawler.initializeParams(path);
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
        String path = ("file:/" + System.getProperty("user.dir") + "\\src\\test").replace("\\", "/");
        WebCrawler crawler = initializeCrawler(path);
        crawler.populateDisabledSites();
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("/vendor/");
        Assert.assertEquals(expectedValues, crawler.getDisabledUrls());
    }

}
