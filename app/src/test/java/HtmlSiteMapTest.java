
import com.avrevic.babylon.health.challenge.BasicModule;
import com.avrevic.babylon.health.challenge.HtmlSiteMap;
import com.avrevic.babylon.health.challenge.ICrawler;
import com.avrevic.babylon.health.challenge.ISiteMap;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import junit.framework.Assert;
import org.junit.Test;

public class HtmlSiteMapTest {

    @Inject
    private ICrawler crawler;

    @Test
    public void shouldGenerateCorrectHTMLSiteMap() throws Exception {
        Injector injector = Guice.createInjector(new BasicModule());
        this.crawler = injector.getInstance(ICrawler.class);
        HtmlSiteMap siteMap = new HtmlSiteMap();
        this.crawler.initializeParams("http://localhost");
        siteMap.generateSitemap("http://localhost", this.crawler.crawl());
        String path = FileSystems.getDefault().getPath(".").toString();
        String newFile = new String(Files.readAllBytes(Paths.get(path + "/output/sitemap.html")));
        String testFile = new String(Files.readAllBytes(Paths.get(path + "/src/test/sitemap-test.html")));
        Assert.assertEquals(testFile, newFile);
    }
}
