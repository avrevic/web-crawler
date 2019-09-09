/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avrevic.babylon.health.challenge;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

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

    @Override
    public void crawl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String fetchRobots() throws Exception {
        URI u = URI.create(this.url + "/robots.txt");
        try (InputStream in = u.toURL().openStream()) {
            File file = new File(System.getProperty("java.io.tmpdir") + "robots.txt");
            FileUtils.copyInputStreamToFile(in, file);
            byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            return new String(encoded, StandardCharsets.US_ASCII);
        }
    }

    private void populateDisabledSites() {
        try {
            String robotsFile = this.fetchRobots();
        } catch (Exception ex) {
            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
