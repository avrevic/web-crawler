
import com.avrevic.babylon.health.challenge.IConfig;
import com.avrevic.babylon.health.challenge.PropertyFileConfig;
import java.util.Properties;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author avrevic
 */
public class PropertyFileConfigTest {

    @Test
    public void propertyFileShouldBeLoadedSuccessfully() throws Exception {
        IConfig config = new PropertyFileConfig();
        Properties properties = config.getConfig(System.getProperty("user.dir") + "\\src\\test\\" + PropertyFileConfig.DEFAULT_CONFIG);
        Assert.assertEquals("https://www.babylonhealth.com", properties.get("website"));
    }

}
