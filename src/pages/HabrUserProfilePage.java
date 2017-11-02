package pages;

import elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;

import static com.sun.javaws.JnlpxArgs.verify;
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
        List<WebElement> paginator = getDriver().findElements(By.cssSelector(".toggle-menu__item-link.toggle-menu__item-link_pagination"));
        for(int i=0; i<numberOfPages; i++){
            waitInSeconds(3);
            LinkedList<WebElement> publications = new LinkedList<>();
            publications.addAll(getDriver().findElements(By.cssSelector(".post__title_link")));
            WebElement publication;
            for(int y=0; y<publications.size(); y++){
                try {
                    publication = publications.get(y);
                }catch (StaleElementReferenceException e){
                    publication = publications.get(y);
                }
                if(publication.getText().trim().equals(publicationName)){
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
