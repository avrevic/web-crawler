package com.avrevic.babylon.health.challenge;

import java.util.Properties;

/**
 * Program configuration interface
 */
public interface IConfig {

    /**
     * Get config in form of key => value properties
     *
     * @param resourceName name of the property config file
     * @return key => value property mapping
     * @throws Exception
     */
    public Properties getConfig(String resourceName) throws Exception;

}
