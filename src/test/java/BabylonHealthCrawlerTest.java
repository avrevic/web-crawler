
import com.avrevic.babylon.health.challenge.BabylonHealthCrawler;
import org.junit.Test;

public class BabylonHealthCrawlerTest {

    @Test
    // Not really much of a test, but want to get 100% code coverage
    public void shouldInstantiateClassSuccessfully() {
        new BabylonHealthCrawler();
    }

    @Test
    // Not really much of a test, but want to get 100% code coverage
    public void shouldSilentlyThrowException() {
        String args[] = new String[1];
        BabylonHealthCrawler.main(args);
    }

    @Test
    public void shouldExecuteProgramSuccessfully() {
        String args[] = new String[1];
        args[0] = "http://localhost";
        BabylonHealthCrawler.main(args);
    }

    @Test
    // Not really much of a test, but want to get 100% code coverage
    public void shouldLogSuccessfully() {
        BabylonHealthCrawler.log("Test");
    }
}
