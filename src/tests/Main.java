package tests;

import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import conf.CaptureScreenShotOnFailureListener;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;
import org.openqa.selenium.*;

@Listeners(CaptureScreenShotOnFailureListener.class)
public class Main {
    private static WebDriver driver;
    private String baseUrl = "https://www.google.com.ua/";

    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", Main.class.getResource("/drivers/chromedriver_2_29.exe").getFile());
        ChromeOptions options = new ChromeOptions();
        ChromeDriver chromeDriver = new ChromeDriver(options);
        driver = new EventFiringWebDriver(chromeDriver);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        getDriver().get(baseUrl);
        maximizeScreen(driver);
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


}
