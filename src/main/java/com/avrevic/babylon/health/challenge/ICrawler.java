package com.avrevic.babylon.health.challenge;

import java.util.HashMap;

/**
 * Crawler interface
 */
public interface ICrawler {

    /**
     * Main crawl method that should fetch all url's
     *
     * @return
     * @throws Exception
     */
    public HashMap<Integer, HashMap<String, Boolean>> crawl() throws Exception;

}
