package elements;

import org.openqa.selenium.By;

public class TextInput extends Element {

    public TextInput(By by) {
        super(by);
    }

    public void fillIn(String text) {
        composeWebElement().sendKeys(text);
    }
}
