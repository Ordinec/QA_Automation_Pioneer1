package elements;

import conf.CustomLogger;
import org.openqa.selenium.By;

public class Button extends Element {

    public Button(By by) {
        super(by);
    }

    public void click(){
        composeWebElement().click();
    }
}
