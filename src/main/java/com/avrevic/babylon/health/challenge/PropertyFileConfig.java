package com.avrevic.babylon.health.challenge;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author avrevic
 */
public class PropertyFileConfig implements IConfig {

    public static final String DEFAULT_CONFIG = "config.properties";

    @Override
    public Properties getConfig(String fileName) throws IOException {
        try (InputStream input = new FileInputStream(fileName)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException ex) {
            throw ex;
        }
    }

}
