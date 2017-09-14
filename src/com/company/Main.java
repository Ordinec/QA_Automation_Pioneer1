package com.company;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "F:\\repos\\test\\src\\com\\company\\resources\\chromedriver_win32.exe");
        ChromeOptions options = new ChromeOptions();
        ChromeDriver chromeDriver = new ChromeDriver(options);
        driver = new EventFiringWebDriver(chromeDriver);
        baseUrl = "https://www.google.com.ua/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testUntitled() throws Exception {
        driver.get(baseUrl + "/?gfe_rd=cr&dcr=0&ei=6LC6Wf3LLMSv8weBl5qQBA&gws_rd=ssl");
        driver.findElement(By.id("gs_ok0")).click();
        driver.findElement(By.xpath("(//button[@id='K273'])[2]")).click();
        driver.findElement(By.xpath("(//button[@id='K273'])[2]")).click();
        driver.findElement(By.xpath("(//button[@id='K273'])[2]")).click();
        driver.findElement(By.id("K72")).click();
        driver.findElement(By.id("K273")).click();
        driver.findElement(By.id("K273")).click();
        driver.findElement(By.xpath("(//button[@id='K273'])[2]")).click();
        driver.findElement(By.xpath("(//button[@id='K273'])[2]")).click();
        driver.findElement(By.id("K67")).click();
        driver.findElement(By.id("K84")).click();
        driver.findElement(By.id("K75")).click();
        driver.findElement(By.id("K84")).click();
        driver.findElement(By.id("K89")).click();
        driver.findElement(By.id("K66")).click();
        driver.findElement(By.id("K69")).click();
        driver.findElement(By.id("K86")).click();
        driver.findElement(By.cssSelector("input.lsb")).click();
        driver.findElement(By.linkText("Что такое Selenium? / Хабрахабр")).click();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
//        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
