/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avrevic.babylon.health.challenge;

import java.util.Properties;

/**
 *
 * @author avrevic
 */
public class PropertyFileConfig implements IConfig{
    
    public static final String DEFAULT_CONFIG = "config.properties";

    @Override
    public Properties getConfig(String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
