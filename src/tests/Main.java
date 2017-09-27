package tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;
import org.openqa.selenium.*;

public class Main {
    private static WebDriver driver;
    private String baseUrl = "https://www.google.com.ua/";

    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "F:\\repos\\test\\src\\drivers\\chromedriver_2_29.exe");
        ChromeOptions options = new ChromeOptions();
        ChromeDriver chromeDriver = new ChromeDriver(options);
        driver = new EventFiringWebDriver(chromeDriver);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        getDriver().get(baseUrl + "/?gfe_rd=cr&dcr=0&ei=6LC6Wf3LLMSv8weBl5qQBA&gws_rd=ssl");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }

    public static WebDriver getDriver(){
        return driver;
    }

}
