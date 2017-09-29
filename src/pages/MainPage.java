package pages;

import elements.Button;
import elements.TextInput;
import org.openqa.selenium.By;

import javax.swing.filechooser.FileSystemView;

import static pages.LoginPage.loginPageisShown;

public class MainPage {

    private Button activateKeyboardButton = new Button(By.id("gs_ok0"));
    private Button cButton = new Button(By.id("K67"));
    private Button eButton = new Button(By.id("K84"));
    private Button lButton = new Button(By.id("K75"));
    private Button nButton = new Button(By.id("K89"));
    private Button iButton = new Button(By.id("K66"));
    private Button uButton = new Button(By.id("K69"));
    private Button mButton = new Button(By.id("K86"));
    private Button searchButton = new Button(By.cssSelector("input.lsb"));
    private Button navigationButton = new Button(By.cssSelector("[title='Додатки Google']"));
    private Button moreButton = new Button(By.linkText("Більше"));
    private Button doccumentsButton = new Button(By.xpath("//span[text()='Документи']"));
    private TextInput recoveryEmailInput = new TextInput(By.cssSelector("placeholder='you@example.com'"));

    public MainPage activateKeyboard(){
        activateKeyboardButton.click();
        return this;
    }

    public MainPage enterSearchCriteria() {
        cButton.click();
        eButton.click();
        lButton.click();
        eButton.click();
        nButton.click();
        iButton.click();
        uButton.click();
        mButton.click();
        return this;
    }

    public SearchResultsPage performSearch() {
        searchButton.click();
        return new SearchResultsPage();
    }

    public DocumentsPage goToDocs() {
        navigationButton.click();
        moreButton.click();
        doccumentsButton.waitAndClick();
        if(loginPageisShown()){
            new LoginPage().login();
        }
        if(recoveryEmailInput.isPresent()){
            recoveryEmailInput.fillIn("sergiitst2@gmail.com");
        }
        return new DocumentsPage();
    }


}
