package com.avrevic.babylon.health.challenge;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author avrevic
 */
public class UrlUtil {

    public String fetchUrlPath(String source) throws MalformedURLException {
        URL sourceUrl = new URL(source);
        return sourceUrl.getPath();
    }

    public boolean isSubdomain(String source, String target) throws MalformedURLException {
        target = target.substring(target.indexOf(".") + 1);
        if (source.equals(target)) {
            return true;
        } else {
            return false;
        }
    }

    public String getHostUrl(String source) throws MalformedURLException {
        URL sourceUrl = new URL(source);
        String sourceUrlToCompare = sourceUrl.getHost();
        if (sourceUrlToCompare != null && sourceUrlToCompare.startsWith("www.")) {
            sourceUrlToCompare = sourceUrlToCompare.substring(4);
        }
        return sourceUrlToCompare;
    }

    public boolean checkHostUrlEquality(String source, String target) throws MalformedURLException {
        String sourceUrlToCompare = getHostUrl(source);
        String targetUrlToCompare = getHostUrl(target);
        if (sourceUrlToCompare.equals(targetUrlToCompare)) {
            return true;
        } else {
            if (isSubdomain(sourceUrlToCompare, targetUrlToCompare)) {
                return true;
            }
            return false;
        }
    }
}
