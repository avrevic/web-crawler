package com.avrevic.babylon.health.challenge;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Generates human readable sitemap
 */
public class HtmlSiteMap implements ISiteMap {

    public static final String DEFAULT_SITEMAP = "sitemap.html";

    StringBuilder sb;

    /**
     * Recursively outputs url hierarchy
     *
     * @param urlList list of urls to scan
     * @param level depth of hierachy
     * @param url base url
     * @param maxPath max depth to scan
     */
    public void appendUrlHierarchy(HashMap<Integer, Set<String>> urlList, Integer level, String url, Integer maxPath) {
        if (level > maxPath) {
            return;
        }

        for (Iterator<String> it = urlList.get(level).iterator(); it.hasNext();) {
            String innerUrl = it.next();
            if (innerUrl.startsWith(url)) {
                sb.append("<li>\n");
                sb.append(innerUrl + "\n");
                sb.append("<ul>\n");
                appendUrlHierarchy(urlList, level + 1, innerUrl, maxPath);
                sb.append("</ul>\n");
                sb.append("</li>\n");
            }
        }
    }

    /**
     * Parse list of urls and generate sitemap
     *
     * @param baseUrl
     * @param urlList list of urls in a final sitemap
     */
    @Override
    public void generateSitemap(String baseUrl, HashMap<Integer, Set<String>> urlList) {
        sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("<body>\n");
        int maxPath = 0;
        // Get the max key num so that we know how deep to scan in recursion
        Set<Integer> keys = urlList.keySet();
        for (Integer key : keys) {
            if (key >= maxPath) {
                maxPath = key;
            }
        }
        // Outer loop iterates through all hierarchy levels
        sb.append("<ul>\n");
        sb.append("<li>\n");
        sb.append(baseUrl + "\n");
        sb.append("<ul>\n");
        // Call a recursive function that hierarchically outputs the URLs to file
        appendUrlHierarchy(urlList, 1, baseUrl, maxPath);
        sb.append("</ul>\n");
        sb.append("</li>\n");
        sb.append("</ul>\n");
        sb.append("</body>\n");
        sb.append("</html>");
        try {
            String path = FileSystems.getDefault().getPath(".").toString() + "/output/sitemap.html";
            Files.write(Paths.get(path),
                    sb.toString().getBytes());
            System.out.println("Successfully generated html sitemap to " + path);
        } catch (IOException ex) {
            System.out.println("Cannot generate html sitemap: " + ex.getMessage());
        }
    }

}
