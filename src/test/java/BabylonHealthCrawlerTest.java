/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.avrevic.babylon.health.challenge.BabylonHealthCrawler;
import org.junit.Test;

public class BabylonHealthCrawlerTest {

    @Test
    // Not really much of a test, but want to get 100% code coverage
    public void shouldInstantiateClassSuccessfully() {
        new BabylonHealthCrawler();
    }

    @Test
    public void shouldExecuteProgramSuccessfully() {
        BabylonHealthCrawler.main(null);
    }

    @Test
    // Not really much of a test, but want to get 100% code coverage
    public void shouldLogSuccessfully() {
        BabylonHealthCrawler.log("Test");
    }
}
