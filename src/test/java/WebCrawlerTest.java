/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.avrevic.babylon.health.challenge.WebCrawler;
import org.junit.Test;

/**
 *
 * @author avrevic
 */
public class WebCrawlerTest {

    @Test
    public void robotsFileShouldBeLoadedSuccessfully() throws Exception {
        String path = ("file:/" + System.getProperty("user.dir") + "\\src\\test").replace("\\", "/");
        WebCrawler crawler = new WebCrawler();
        crawler.initializeParams(path);
        crawler.fetchRobots();

        path = "https://www.google.com";
        crawler.initializeParams(path);
        crawler.fetchRobots();
    }

    @Test(expected = IllegalArgumentException.class)
    public void robotsFileShouldThrowIllegalArgumentException() throws Exception {
        String path = ("not_valid_url");
        WebCrawler crawler = new WebCrawler();
        crawler.initializeParams(path);
        crawler.fetchRobots();
    }
    
}
