package com.avrevic.babylon.health.challenge;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates human readable sitemap
 */
public class HtmlSiteMap implements ISiteMap {

    public static final String DEFAULT_SITEMAP = "sitemap.html";

    StringBuilder sb;

    public void appendUrlHierarchy(HashMap<Integer, Set<String>> urlList, Integer level, String url, Integer maxPath) {
        if (level > maxPath) {
            return;
        }

        for (Iterator<String> it = urlList.get(level).iterator(); it.hasNext();) {
            String f = it.next();
            if (f.startsWith(url)) {
                sb.append("<li>\n");
                sb.append(f + "\n");
                sb.append("<ul>\n");
                appendUrlHierarchy(urlList, level + 1, f, maxPath);
                sb.append("</ul>\n");
                sb.append("</li>\n");
            }
        }
    }

    /**
     * Parse list of urls and generate sitemap
     *
     * @param urlList list of urls in a final sitemap
     */
    @Override
    public void generateSitemap(String baseUrl, HashMap<Integer, Set<String>> urlList) {
        sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("<body>\n");
        int maxPath = 0;
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
        appendUrlHierarchy(urlList, 1, baseUrl, maxPath);
        sb.append("</ul>\n");
        sb.append("</li>\n");
        sb.append("</ul>\n");
        sb.append("</body>\n");
        sb.append("</html>");
        try {
            Files.write(Paths.get(FileSystems.getDefault().getPath(".").toString() + "/output/sitemap.html"), sb.toString().getBytes());
            System.out.println("Success");
        } catch (IOException ex) {
            Logger.getLogger(HtmlSiteMap.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fail");
        }
    }

}
