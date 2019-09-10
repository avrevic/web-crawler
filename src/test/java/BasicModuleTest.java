
import com.avrevic.babylon.health.challenge.BasicModule;
import com.avrevic.babylon.health.challenge.IConfig;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;

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
