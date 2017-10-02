package tests;

import org.testng.annotations.Test;
import pages.MainPage;

public class Docs extends Main{

    @Test(priority = 1)
    public void docsTest(){
        String folderName = "Test Folder";
        MainPage mainPage = new MainPage();
        mainPage.goToDocs()
                .createNewFile()
                .verifyFile();
    }
}
