package conf;

import tests.Main;

import java.io.IOException;
import java.util.Properties;

public class PropertyLoader2 {

    private static String apiToken = selectPropertiesFile(Main.env).getProperty("mailtrap.token");

    public static Properties selectPropertiesFile(String env) {
        final Properties properties = new Properties();
        switch (env) {
            case "stage":
                try {
                    properties.load(Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream("default.properties"));
                } catch (IOException e) {
                    CustomLogger.log("problem loading properties file");
                }
                break;
            case "release":
                try {
                    properties.load(Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream("release.properties"));
                } catch (IOException e) {
                    CustomLogger.log("problem loading properties file");
                }
                break;
            default:
                CustomLogger.log("Incorrect env variable");
                break;
        }
        return properties;
    }
}
