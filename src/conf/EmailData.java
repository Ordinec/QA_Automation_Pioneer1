package conf;

import javax.mail.Address;
import java.util.Date;

/**
 * @author Sergii Moroz
 */
public class EmailData {
    private String messageBody;

    public EmailData(String subject, Address address, Address address1, Date receivedDate, Object content) {

    }

    public String getMessageBody() {
        return messageBody;
    }
}
