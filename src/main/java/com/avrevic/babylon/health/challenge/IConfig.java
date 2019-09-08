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
public interface IConfig {

    public Properties getConfig(String fileName) throws Exception;

}
