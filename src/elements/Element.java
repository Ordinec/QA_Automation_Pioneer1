package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static tests.Main.getDriver;

public abstract class Element {
    protected By by;

    public Element(By by){
        this.by = by;
    }

    protected static WebElement composeWebElement(By by){
        return getDriver().findElement(by);
    }

}
