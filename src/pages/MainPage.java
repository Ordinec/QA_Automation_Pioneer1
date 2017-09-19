package pages;

import elements.Button;
import org.openqa.selenium.By;

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
}
