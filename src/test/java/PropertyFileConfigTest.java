
import com.avrevic.babylon.health.challenge.IConfig;
import com.avrevic.babylon.health.challenge.PropertyFileConfig;
import java.util.Properties;
import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;	

/**
 * 
 * @author avrevic
 */
public class PropertyFileConfigTest {
    
    
    @Test
    public void propertyFileShouldBeLoadedSuccessfully() {
        IConfig config = new PropertyFileConfig();
        Properties properties = config.getConfig(PropertyFileConfig.defaultConfig);
        Assert.assertEquals("https://www.babylonhealth.com", properties.get("website"));
    }
    
}
