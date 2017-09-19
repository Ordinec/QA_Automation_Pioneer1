package tests;

import org.testng.annotations.Test;
import pages.MainPage;

public class Search extends Main{

    @Test
    public void searchWithKeyboard() {
        MainPage mainPage = new MainPage();
        mainPage.activateKeyboard()
                .enterSearchCriteria()
                .performSearch()
                .clickOnSearchResultLink("Что такое Selenium? / Хабрахабр");
    }
}
