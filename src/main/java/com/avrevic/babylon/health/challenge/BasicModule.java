/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avrevic.babylon.health.challenge;

import com.google.inject.AbstractModule;

/**
 *
 * @author avrevic
 */
public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IConfig.class).to(PropertyFileConfig.class);
    }

}
