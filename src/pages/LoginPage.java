package pages;

import elements.Button;
import elements.TextInput;
import org.openqa.selenium.By;

import static tests.Main.waitInSeconds;

public class LoginPage {

    private static TextInput emailInput = new TextInput(By.cssSelector("input[type='email']"));
    private TextInput passwordInput = new TextInput(By.cssSelector("input[type='password']"));
    private Button nextButton = new Button(By.xpath("//span[text()='Next']"));

    public void login(){
        emailInput.fillIn("autopioneer1@gmail.com");
        nextButton.click();
        waitInSeconds(3);
        passwordInput.fillIn("123123123A");
        nextButton.click();
    }


    public static boolean loginPageisShown() {
        return emailInput.isPresent();
    }
}
