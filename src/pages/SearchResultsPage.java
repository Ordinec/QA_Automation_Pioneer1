package pages;

import elements.Button;
import org.openqa.selenium.By;

public class SearchResultsPage {

    private Button searchResultLink(String linkText){
        return new Button(By.linkText(linkText));
    }

    public SearchResultsPage clickOnSearchResultLink(String linkText) {
        searchResultLink(linkText).click();
        return this;
    }
}
