
import com.avrevic.babylon.health.challenge.BasicModule;
import com.avrevic.babylon.health.challenge.HtmlSiteMap;
import com.avrevic.babylon.health.challenge.ICrawler;
import com.avrevic.babylon.health.challenge.ISiteMap;
import com.avrevic.babylon.health.challenge.XmlSiteMap;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import junit.framework.Assert;
import org.junit.Test;

public class XmlSiteMapTest {

    @Inject
    private ICrawler crawler;

    @Test
    public void shouldGenerateCorrectXMLSiteMap() throws Exception {
        Injector injector = Guice.createInjector(new BasicModule());
        this.crawler = injector.getInstance(ICrawler.class);
        XmlSiteMap siteMap = new XmlSiteMap();
        this.crawler.initializeParams("http://localhost", true);
        siteMap.generateSitemap("http://localhost", this.crawler.crawl());
        String path = FileSystems.getDefault().getPath(".").toString();
        String newFile = new String(Files.readAllBytes(Paths.get(path + "/output/sitemap.xml")));
        String testFile = new String(Files.readAllBytes(Paths.get(path + "/src/test/sitemap-test.xml")));
        Assert.assertEquals(testFile, newFile);
    }
}
