
import com.avrevic.babylon.health.challenge.BasicModule;
import com.avrevic.babylon.health.challenge.IConfig;
import com.avrevic.babylon.health.challenge.ICrawler;
import com.avrevic.babylon.health.challenge.ISiteMap;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import org.junit.Test;

public class HtmlSiteMapTest {

    @Inject
    private ICrawler crawler;

    @Inject
    private ISiteMap siteMap;

    @Test
    public void shouldGenerateCorrectHTMLSiteMap() throws Exception {
        Injector injector = Guice.createInjector(new BasicModule());
        this.crawler = injector.getInstance(ICrawler.class);
        this.siteMap = injector.getInstance(ISiteMap.class);
        this.crawler.initializeParams("http://localhost");
        siteMap.generateSitemap("http://localhost", this.crawler.crawl());
        Paths.get(FileSystems.getDefault().getPath(".").toString() + "/output/sitemap.html")
        , sb.toString().getBytes()
        
    }
}
