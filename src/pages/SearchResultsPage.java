package pages;

import elements.Button;
import org.openqa.selenium.By;

public class SearchResultsPage {

    private Button searchResultLink(String linkText){
        return new Button(By.linkText(linkText));
    }

    public HabrPublicationPage clickOnSearchResultLink(String linkText) {
        searchResultLink(linkText).click();
        return new HabrPublicationPage();
    }

}
