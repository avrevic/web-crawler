package com.avrevic.babylon.health.challenge;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Helper utility for URL validation, parsing ...
 *
 * @author avrevic
 */
public class UrlUtil {

    /**
     * Fetch URL path after hostname
     *
     * @param source source URL
     * @return String path
     * @throws MalformedURLException
     */
    public String getUrlPath(String source) throws MalformedURLException {
        URL sourceUrl = new URL(source);
        return sourceUrl.getPath();
    }

    /**
     * Validate if the target url is the subdomain of the source URL
     *
     * @param source source url
     * @param target subdomain?
     * @return true or false - depending if it is or isn't subdomain
     * @throws MalformedURLException
     */
    public boolean isSubdomain(String source, String target) throws MalformedURLException {
        target = target.substring(target.indexOf(".") + 1);
        if (source.equals(target)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get only the host url from the full url
     *
     * @param source source host url
     * @return only the host url
     * @throws MalformedURLException
     */
    public String getHostUrl(String source) throws MalformedURLException {
        URL sourceUrl = new URL(source);
        String sourceUrlToCompare = sourceUrl.getHost();
        /*
         * We want the barebone source host url in case the target host url
         * (that we are comparing later) might not contain www. We would get
         * the issue that the URLs are not the same
         */
        if (sourceUrlToCompare != null && sourceUrlToCompare.startsWith("www.")) {
            sourceUrlToCompare = sourceUrlToCompare.substring(4);
        }
        return sourceUrlToCompare;
    }

    /**
     * Validate if two urls are eqal
     *
     * @param source Source url
     * @param target Target URL
     * @return true or false - equal or not
     * @throws MalformedURLException
     */
    public boolean isSourceHostURLEqualTarget(String source, String target) throws MalformedURLException {
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
