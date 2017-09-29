package pages;

import elements.Button;
import elements.Label;
import org.openqa.selenium.By;

import static org.testng.AssertJUnit.assertTrue;
import static tests.Main.waitInSeconds;

public class DocumentsPage {

    private Button createNewFileButton = new Button(By.xpath("//img[@src='https://ssl.gstatic.com/docs/templates/thumbnails/docs-blank_1.png']/.."));
    private Label documentLabel = new Label(By.xpath("//body[@itemtype='http://schema.org/CreativeWork/DocumentObject']"));

    public DocumentsPage createNewFile() {
        createNewFileButton.waitAndClick();
        return this;
    }

    public void verifyFile(){
        waitInSeconds(2);
        assertTrue(documentLabel.isPresent());
    }
}
