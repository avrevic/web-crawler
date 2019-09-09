/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avrevic.babylon.health.challenge;

import com.google.common.base.CharMatcher;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author avrevic
 */
public class WebCrawler implements ICrawler {

    private String url;
    private List<String> disabledUrls;

    public String getUrl() {
        return this.url;
    }

    public List<String> getDisabledUrls() {
        return this.disabledUrls;
    }

    @Override
    public void initializeParams(String url) {
        this.url = url;
        this.disabledUrls = new ArrayList<>();
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

    public void populateDisabledSites() throws Exception {
        String robotsFile = this.fetchRobots();
        new BufferedReader(new StringReader(robotsFile)).lines().forEach((String line) -> {
            if (line.startsWith("Disallow: ")) {
                Integer urlPathBranch = CharMatcher.is('/').countIn(line.substring(line.indexOf("/")));
                if (!this.disabledUrls.contains(line.substring(line.indexOf("/")))) {
                    this.disabledUrls.add(line.substring(line.indexOf("/")));
                }
            }
        });
    }

}
