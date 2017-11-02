package conf;

import org.junit.Assert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
    private static final String PROPERTIES_DEFAULT = "/conf/default.properties";
    private static final String PROPERTIES_ENV_VAR = "/conf/config.path";
    private static PropertyLoader instance;
    private Properties propertyDefault;
    private Properties property;

        public static Capabilities loadCapabilities() {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(loadProperty("browser.name"));
            return capabilities;
        }

        public static String loadProperty(final String name) {
            String res = get().load(name);
            if (res == null) {
                Assert.fail("Can't get property, name: "+name);
            }
            return res;
        }

        private String load(final String name) {
            String res = null;
            if (property != null) {
                res = property.getProperty(name);
            }
            if (res == null) {
                res = propertyDefault.getProperty(name);
            }
            return res;
        }

        synchronized private static PropertyLoader get() {
            if (instance == null) {
                try {
                    instance = new PropertyLoader();
                } catch (Exception e) {
                    e.printStackTrace();
                    Assert.fail("Can't initialize property loader");
                }
            }
            return instance;
        }

        private PropertyLoader() throws IOException {
            {
                Properties tmp = new Properties();
                tmp.load(PropertyLoader.class.getResourceAsStream(PROPERTIES_DEFAULT));
                propertyDefault = tmp;
            }
            String propertyPath = System.getProperty(PROPERTIES_ENV_VAR);
            if (propertyPath != null) {
                Properties tmp = new Properties();
                tmp.load(PropertyLoader.class.getResourceAsStream(propertyPath));
                property = tmp;
            }
        }
}
