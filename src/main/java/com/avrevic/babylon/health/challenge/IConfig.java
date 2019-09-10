package com.avrevic.babylon.health.challenge;

import java.util.Properties;

/**
 *
 * @author avrevic
 */
public interface IConfig {

    public Properties getConfig(String fileName) throws Exception;

}
