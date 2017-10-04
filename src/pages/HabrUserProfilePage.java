package pages;

import elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.fail;
import static tests.Main.getDriver;
import static tests.Main.waitInSeconds;

public class HabrUserProfilePage {
    private Button publicationsTabButton = new Button(By.xpath("//span[contains(@title,'Публикации:')]"));
    private Button nextButton = new Button(By.cssSelector(".arrows-pagination__item-link.arrows-pagination__item-link_next > span"));

    public HabrUserProfilePage openPublications() {
        publicationsTabButton.click();
        return this;
    }

    private int numberOfPages(){
        int number = getDriver().findElements(By.cssSelector(".toggle-menu__item-link.toggle-menu__item-link_pagination")).size();
        return Integer.parseInt(getDriver().findElements(By.cssSelector(".toggle-menu__item-link.toggle-menu__item-link_pagination")).get(number-1).getText());
    }

    public HabrUserProfilePage verifyPublicationPresence(String publicationName) {
        int numberOfPages = numberOfPages();
        boolean resultFounded = false;
        LinkedList<WebElement> publications = new LinkedList<WebElement>();
        List<WebElement> paginator = getDriver().findElements(By.cssSelector(".toggle-menu__item-link.toggle-menu__item-link_pagination"));
        for(int i=0; i<numberOfPages; i++){
            publications.addAll(getDriver().findElements(By.cssSelector(".post__title_link")));
            for(int y=0; y<publications.size(); y++){
                if(publications.get(y).getText().trim().equals(publicationName)){
                    resultFounded = true;
                    break;
                }
            }
            if(resultFounded){
                break;
            }else if(i==numberOfPages-1&&!resultFounded){
                fail("Results not found");
            }else{
                paginator.get(i).click();
            }
        }
        return this;
    }
}
