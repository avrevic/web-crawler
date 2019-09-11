
import com.avrevic.babylon.health.challenge.BasicModule;
import com.avrevic.babylon.health.challenge.ICrawler;
import com.avrevic.babylon.health.challenge.ISiteMap;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;

public class BasicModuleTest {

    @Inject
    private ICrawler crawler;

    @Inject
    private ISiteMap siteMap;

    @Test
    public void shouldInjectCorrectClassInstanceSuccessfully() {
        Injector injector = Guice.createInjector(new BasicModule());
        this.crawler = injector.getInstance(ICrawler.class);
        this.siteMap = injector.getInstance(ISiteMap.class);
        Assert.assertEquals("com.avrevic.babylon.health.challenge.WebCrawler", this.crawler.getClass().getName());
        Assert.assertEquals("com.avrevic.babylon.health.challenge.HtmlSiteMap", this.siteMap.getClass().getName());
    }
}
