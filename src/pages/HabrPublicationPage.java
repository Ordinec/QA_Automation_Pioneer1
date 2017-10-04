package pages;

import elements.Button;
import org.openqa.selenium.By;

public class HabrPublicationPage {
    private Button userProfileButton = new Button(By.cssSelector(".user-info__nickname.user-info__nickname_small"));

    public HabrUserProfilePage openUserProfile() {
        userProfileButton.click();
        return new HabrUserProfilePage();
    }
}
