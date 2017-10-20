package pages;

import conf.EmailData;
import conf.EmailService;
import elements.Button;
import elements.TextInput;
import org.openqa.selenium.By;

import java.util.List;

public class EmailPage {

    private Button composeButton = new Button(By.xpath("//div[text()='COMPOSE']"));

    private TextInput toInputBox = new TextInput(By.cssSelector("[aria-label='To']"));
    private TextInput subjectInputBox = new TextInput(By.cssSelector("[name='subjectbox']"));
    private Button sendButton = new Button(By.cssSelector("[aria-label='Send \u202A(Ctrl-Enter)\u202C']"));

    public EmailPage sendEmail() {
        composeButton.click();
        toInputBox.fillIn("autoPioneer1@gmail.com");
        subjectInputBox.fillIn("Subject");
        sendButton.click();
        return this;
    }

    public EmailPage verifyEmailReceived() {
        List<EmailData> emails = EmailService.connectAndGetAllEmailsFromFolder("Inbox");
        EmailService.waitForEmailAndRemoveAllMessagesFromFolder("Inbox");
        return this;
    }
}
