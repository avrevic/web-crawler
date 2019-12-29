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
     * @param initializeSiteList Used in a multithreaded environment to prevent
     *         reinitialization of the site list when new threads are started
     */
    public void initializeParams(String url, Boolean initializeSiteList);

    /**
     * Main crawl method that should fetch all url's
     *
     * @return
     * @throws Exception
     */
    public HashMap<Integer, Set<String>> crawl() throws Exception;

}
