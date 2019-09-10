package com.avrevic.babylon.health.challenge;

import com.google.inject.AbstractModule;

/**
 * Support class for DI configuration
 */
public class BasicModule extends AbstractModule {

    /**
     * Configure DI Interface -> Class mapping
     */
    @Override
    protected void configure() {
        bind(IConfig.class).to(PropertyFileConfig.class);
        bind(ISiteMap.class).to(HtmlSiteMap.class);
        bind(ICrawler.class).to(WebCrawler.class);
    }

}
