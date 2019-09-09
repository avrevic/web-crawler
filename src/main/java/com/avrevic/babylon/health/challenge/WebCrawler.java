/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avrevic.babylon.health.challenge;

/**
 *
 * @author avrevic
 */
public class WebCrawler implements ICrawler {

    private String url;
    
    public String getUrl() {
        return this.url;
    }

    @Override
    public void initializeParams(String url) {
        this.url = url;
    }
    
    private String fetchRobots() {

    @Override
    public void crawl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void populateDisabledSites(String robotsFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
