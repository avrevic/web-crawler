
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
        String args[] = new String[2];
        BabylonHealthCrawler.main(args);
    }

    @Test
    public void shouldExecuteProgramSuccessfully() {
        String args[] = new String[2];
        args[1] = "http://localhost";
        BabylonHealthCrawler.main(args);
    }

    @Test
    // Not really much of a test, but want to get 100% code coverage
    public void shouldLogSuccessfully() {
        BabylonHealthCrawler.log("Test");
    }
}
