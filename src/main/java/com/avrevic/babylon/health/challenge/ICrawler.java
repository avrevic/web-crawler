package com.avrevic.babylon.health.challenge;

/**
 *
 * @author avrevic
 */
public interface ICrawler {

    public void initializeParams(String url);

    public void crawl() throws Exception;

}
