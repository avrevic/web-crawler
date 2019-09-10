
import com.avrevic.babylon.health.challenge.BasicModule;
import com.avrevic.babylon.health.challenge.UrlUtil;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.net.MalformedURLException;
import junit.framework.Assert;
import org.junit.Test;

public class UrlUtilTest {

    @Inject
    private final UrlUtil urlUtil;

    public UrlUtilTest() {
        Injector injector = Guice.createInjector(new BasicModule());
        this.urlUtil = injector.getInstance(UrlUtil.class);
    }

    /**
     * 
     * @param source
     * @param targetUrlString
     * @param assertCheck
     * @param targetProtocolSpecified
     * @throws MalformedURLException 
     */
    private void urlEqualityCombinationsAssert(String source, String targetUrlString, boolean assertCheck, boolean targetProtocolSpecified) throws MalformedURLException {
        if (targetProtocolSpecified) {
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, targetUrlString), assertCheck);
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, targetUrlString + "/"), assertCheck);
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, targetUrlString + ":/"), assertCheck);
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, targetUrlString + ":80/"), assertCheck);
        } else {
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, "http://" + targetUrlString + ""), assertCheck);
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, "http://" + targetUrlString + "/"), assertCheck);
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, "http://" + targetUrlString + ":/"), assertCheck);
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, "http://" + targetUrlString + ":80/"), assertCheck);
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, "http://com." + targetUrlString + "/"), assertCheck);
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, "https://" + targetUrlString + ""), assertCheck);
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, "https://" + targetUrlString + "/"), assertCheck);
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, "https://" + targetUrlString + ":/"), assertCheck);
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, "https://" + targetUrlString + ":80/"), assertCheck);
            Assert.assertEquals(urlUtil.isSourceHostURLEqualTarget(source, "https://com." + targetUrlString + "/"), assertCheck);
        }
    }

    @Test
    public void urlsShouldBeEqual() throws MalformedURLException {
        String sourceUrlString = "http://example.com";
        String targetUrlString = "example.com";
        urlEqualityCombinationsAssert(sourceUrlString, targetUrlString, true, false);
        targetUrlString = "https://example.com";
        urlEqualityCombinationsAssert(sourceUrlString, targetUrlString, true, true);
        targetUrlString = "example.com/test";
        urlEqualityCombinationsAssert(sourceUrlString, targetUrlString, true, false);
        targetUrlString = "https://example.com/test";
        urlEqualityCombinationsAssert(sourceUrlString, targetUrlString, true, true);
    }

    @Test
    public void urlsShouldNotBeEqual() throws MalformedURLException {
        String sourceUrlString = "http://example.com";
        String targetUrlString = "http://google.com";
        urlEqualityCombinationsAssert(sourceUrlString, targetUrlString, false, true);
        targetUrlString = "google.com";
        urlEqualityCombinationsAssert(sourceUrlString, targetUrlString, false, false);
        targetUrlString = "google.com/test";
        urlEqualityCombinationsAssert(sourceUrlString, targetUrlString, false, false);
        targetUrlString = "http://google.com/test";
        urlEqualityCombinationsAssert(sourceUrlString, targetUrlString, false, true);
    }

    @Test
    public void shouldReturnCorrectHostUrl() throws MalformedURLException {
        String sourceUrlString = "http://example.com/test";
        Assert.assertEquals("example.com", urlUtil.getHostUrl(sourceUrlString));
        sourceUrlString = "https://www.example.com/test";
        Assert.assertEquals("example.com", urlUtil.getHostUrl(sourceUrlString));
    }

    @Test
    public void pathsShouldBeEqual() throws MalformedURLException {
        String sourceUrlString = "http://example.com/test";
        String targetUrlString = "https://example.com/test";
        Assert.assertEquals(urlUtil.getUrlPath(sourceUrlString), urlUtil.getUrlPath(targetUrlString));
    }

    @Test
    public void isSubdomainShouldReturnTrue() throws MalformedURLException {
        String sourceUrlString = "http://example.com/test";
        String targetUrlString = "https://com.example.com/test";
        Assert.assertEquals(urlUtil.isSubdomain(urlUtil.getHostUrl(sourceUrlString), urlUtil.getHostUrl(targetUrlString)), true);
    }

}
