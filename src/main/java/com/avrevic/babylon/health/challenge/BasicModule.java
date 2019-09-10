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
