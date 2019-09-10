/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.avrevic.babylon.health.challenge.BasicModule;
import com.avrevic.babylon.health.challenge.IConfig;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author avrevic
 */
public class BasicModuleTest {

    @Inject
    private IConfig config;

    @Test
    public void shouldInjectCorrectClassInstanceSuccessfully() {
        Injector injector = Guice.createInjector(new BasicModule());
        this.config = injector.getInstance(IConfig.class);
        Assert.assertEquals("com.avrevic.babylon.health.challenge.PropertyFileConfig", this.config.getClass().getName());
    }
}
