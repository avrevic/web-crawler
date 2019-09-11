package com.avrevic.babylon.health.challenge;

import java.util.HashMap;
import java.util.Set;

/**
 * Generates machine readable xml sitemap
 */
public class XmlSiteMap implements ISiteMap {

    public static final String DEFAULT_SITEMAP = "sitemap.xml";

    /**
     * Parse list of urls and generate sitemap
     *
     * @param baseUrl
     * @param urlList list of urls in a final sitemap
     */
    @Override
    public void generateSitemap(String baseUrl, HashMap<Integer, Set<String>> urlList) {
        
    }

}
