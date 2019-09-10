
import com.avrevic.babylon.health.challenge.WebCrawler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
        expectedValues.add("vendor");
        Assert.assertEquals(expectedValues, crawler.getDisabledUrls());
    }

    @Test
    public void propertiesShouldBeInitializedSuccessfully() {
        WebCrawler crawler = initializeCrawler("http://localhost");
        Assert.assertNotNull(crawler.getUrl());
    }

    @Test
    public void shouldReturnCorrectLinkHierarchy() throws Exception {
        HashMap<Integer, HashMap<String, Boolean>> testUrls = new HashMap<>();
        HashMap<String, Boolean> urlLinks = new HashMap<>();
        urlLinks.put("http://localhost", true);
        testUrls.put(0, urlLinks);
        urlLinks = new HashMap<>();
        urlLinks.put("http://localhost/page2.html", true);
        urlLinks.put("http://localhost/index.html", true);
        testUrls.put(1, urlLinks);
        WebCrawler crawler = initializeCrawler("http://localhost");
        crawler.crawl();
        Assert.assertEquals(testUrls, crawler.getSiteUrls());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldReturnInvalidArgumentException() throws Exception {
        WebCrawler crawler = initializeCrawler("invalid_url");
        crawler.crawl();
    }

}
