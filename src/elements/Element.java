package elements;

import com.google.common.base.Function;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static tests.Main.getDriver;

public abstract class Element {
    protected By by;

    public Element(By by){
        this.by = by;
    }

    protected WebElement composeWebElement(){
        return getDriver().findElement(by);
    }

    public void waitForElementToBeInvisible() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void waitForElementToBeVisible() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForElementToBeClickable() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void waitAndClick() {
//        TestCase.setImplicitlyWait(0);
//        log.info("Attempt to wait until element found " + by + " will be appeared");
        new FluentWait(getDriver())
                .withTimeout(30000, TimeUnit.MILLISECONDS)
                .pollingEvery(200, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .withMessage("Element found by " + by + " is still invisible, but should not be")
                .until(new Function<WebDriver, Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        try{
                            getDriver().findElement(by).click();
                            return true;
                        }catch(WebDriverException ignored){
                        }
                        return false;
                    }
                });
//        TestCase.setImplicitlyWait(TestCase.DEFAULT_WAIT);
    }

    public boolean isPresent() {
        try {
            composeWebElement().isDisplayed();
        }catch (StaleElementReferenceException e){
            composeWebElement().isDisplayed();
        }catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public void scrollToElement() {
        try {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();", composeWebElement());
        } catch (StaleElementReferenceException ignore) {
            //ignore this exception
        }
    }
}
