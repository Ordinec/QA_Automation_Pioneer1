package tests;

import java.awt.*;
import java.beans.EventHandler;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import conf.CaptureScreenShotOnFailureListener;
import conf.LoggingEventListener;
import conf.PropertyLoader;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;
import org.openqa.selenium.*;

@Listeners(CaptureScreenShotOnFailureListener.class)
public class Main {
    private static EventFiringWebDriver driver;
    protected static final Logger LOG = Logger.getLogger(Main.class.getName());
    public static String env = "dev";
    private String baseUrl = PropertyLoader.loadProperty("app.url");
    private static final String OS = System.getProperty("os.name").toLowerCase();

    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", Main.class.getResource("/drivers/chromedriver_2_33.exe").getFile());
        ChromeOptions options = new ChromeOptions();
        ChromeDriver chromeDriver = new ChromeDriver(options);
        driver = new EventFiringWebDriver(chromeDriver);
        LoggingEventListener handler = new LoggingEventListener();
        driver.register(handler);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        getDriver().get(baseUrl);
        maximizeScreen(driver);
        LOG.info("Running user agent: " + resolveUserAgent(chromeDriver));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        stopDriver();
    }

    public static WebDriver getDriver(){
        return driver;
    }

    public static void stopDriver(){
        driver.quit();
    }

    public static void waitInSeconds(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void maximizeScreen(WebDriver driver) {
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point position = new Point(0, 0);
        driver.manage().window().setPosition(position);
        Dimension maximizedScreenSize =
                new Dimension(screenSize.width, screenSize.height);
        driver.manage().window().setSize(maximizedScreenSize);
    }

    public static String getUrlStatusCode(String url){
        String result = null;
        try{
            URL address = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)address.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            result = ""+code;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private static String resolveUserAgent(ChromeDriver chromeDriver) {
        return (String) chromeDriver.executeScript("return navigator.userAgent;");
    }

}
