/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avrevic.babylon.health.challenge;

import java.util.HashMap;

/**
 * Interface for various sitemap implementations
 *
 * @author avrevic
 */
public interface ISiteMap {

    /**
     * Build a sitemap file
     *
     * @param urlList List or urls in the final sitemap
     */
    public void generateSitemap(HashMap<Integer, HashMap<String, Boolean>> urlList);
}
