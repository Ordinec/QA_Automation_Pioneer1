package tests;

import org.testng.annotations.Test;
import pages.MainPage;

/**
 * @author Sergii Moroz
 */
public class EmailTest extends Main {

    @Test
    public void emailTest(){
        MainPage mainPage = new MainPage();
        mainPage.goToEmail()
                .sendEmail()
                .verifyEmailReceived();
    }
}
