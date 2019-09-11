package com.avrevic.babylon.health.challenge;

import java.util.HashMap;
import java.util.Set;

/**
 * Crawler interface
 */
public interface ICrawler {

    /**
     * Initialize class params
     *
     * @param url root url to crawl
     */
    public void initializeParams(String url);

    /**
     * Main crawl method that should fetch all url's
     *
     * @return
     * @throws Exception
     */
    public HashMap<Integer, Set<String>> crawl() throws Exception;

}
