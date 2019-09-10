package com.avrevic.babylon.health.challenge;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Implementation of config file manipulation
 * 
 * @author avrevic
 */
public class PropertyFileConfig implements IConfig {

    public static final String DEFAULT_CONFIG = "config.properties";

    /**
     * Get config from file and store it in key => value form
     *
     * @param resourceName Name of the property resource
     * @return key => value config property set
     * @throws IOException
     */
    @Override
    public Properties getConfig(String resourceName) throws IOException {
        try (InputStream input = new FileInputStream(resourceName)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException ex) {
            throw ex;
        }
    }

}
