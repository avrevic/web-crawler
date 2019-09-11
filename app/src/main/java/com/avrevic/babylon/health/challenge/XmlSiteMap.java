package com.avrevic.babylon.health.challenge;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
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
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
        for (Integer key : urlList.keySet()) {
            for (Iterator<String> it = urlList.get(key).iterator(); it.hasNext();) {
                String innerUrl = it.next();
                sb.append("<url>\n");
                sb.append("<loc>" + innerUrl + "</loc>\n");
                sb.append("</url>\n");
            }
        }
        sb.append("</urlset");
        try {
            String path = FileSystems.getDefault().getPath(".").toString() + "/output/sitemap.xml";
            Files.write(Paths.get(path),
                    sb.toString().getBytes());
            System.out.println("Successfully generated html sitemap to " + path);
        } catch (IOException ex) {
            System.out.println("Cannot generate html sitemap: " + ex.getMessage());
        }
    }

}
