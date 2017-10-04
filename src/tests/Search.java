package tests;

import org.testng.annotations.Test;
import pages.MainPage;

public class Search extends Main{

    @Test
    public void searchWithKeyboard() {
        String publication = "Что такое Selenium? / Хабрахабр";
        MainPage mainPage = new MainPage();
        mainPage.activateKeyboard()
                .enterSearchCriteria()
                .performSearch()
                .clickOnSearchResultLink(publication)
                .openUserProfile()
                .openPublications()
                .verifyPublicationPresence(publication);
    }
}
