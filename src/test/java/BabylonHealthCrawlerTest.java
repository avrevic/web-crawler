
import com.avrevic.babylon.health.challenge.BabylonHealthCrawler;
import org.junit.Test;

public class BabylonHealthCrawlerTest {

    @Test
    // Not really much of a test, but want to get 100% code coverage
    public void shouldInstantiateClassSuccessfully() {
        new BabylonHealthCrawler();
    }

    @Test
    public void shouldExecuteProgramSuccessfully() {
        BabylonHealthCrawler.main(null);
    }

    @Test
    // Not really much of a test, but want to get 100% code coverage
    public void shouldLogSuccessfully() {
        BabylonHealthCrawler.log("Test");
    }
}
