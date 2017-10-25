package conf;

import com.sun.mail.imap.IMAPFolder;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.fail;
import static tests.Main.waitInSeconds;

public class EmailService {

    private static final String MAIL_STORE_PROTOCOL_PROPERTY = "mail.store.protocol";
    private static final String IMAPS = "imaps";
    private static final String HOST = "imap.googlemail.com";
    private static final String GMAIL_TRASH_FOLDER = "[Gmail]/Trash";
    private static final String PARENT_FOLDER = "Chorus/";

    private static Store connectToEmailBox() {
        Properties properties = System.getProperties();
        properties.setProperty(MAIL_STORE_PROTOCOL_PROPERTY, IMAPS);
        Session session = Session.getDefaultInstance(properties, null);
        Store store = null;
        try {
            store = session.getStore(IMAPS);
            store.connect(HOST, "autopioneer1@gmail.com", "123123A");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        return store;
    }

    private static IMAPFolder waitForEmailInFolder(Store store, String folderName){
        IMAPFolder workFolder = getFolder(store, folderName);
        int seconds = 0;
        try {
            while (workFolder.getMessageCount() == 0) {
                workFolder = getFolder(store, folderName);
                waitInSeconds(1);
                seconds++;
                if (seconds == 3000) {
                    fail("Email has not been delivered after 5 min waiting");
                    break;
                }
            }
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        return workFolder;
    }

    private static IMAPFolder getFolder(Store store, String folderName){
        IMAPFolder result = null;
        try {
            result = (IMAPFolder) store.getFolder(PARENT_FOLDER + folderName);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Message[] getMessages(IMAPFolder folder) throws javax.mail.MessagingException {
        if (!folder.isOpen()) {
            folder.open(Folder.READ_WRITE);
        }
        Message[] messages = new Message[0];
        messages = folder.getMessages();
        CustomLogger.log("No of Messages : " + folder.getMessageCount());
        return messages;
    }

    private static List<EmailData> getEmailDataFromMessages(Message[] messages) {
        List<EmailData> emailsList;
        emailsList = new ArrayList<EmailData>();
        for (int i = 0; i < messages.length; i++) {
            CustomLogger.log("MESSAGE " + (i + 1) + ":");

            Message msg = messages[i];

            try {
            CustomLogger.log("Subject: " + msg.getSubject());
            CustomLogger.log("From: " + msg.getFrom()[0]);
            CustomLogger.log("To: " + msg.getAllRecipients()[0]);
            CustomLogger.log("Date: " + msg.getReceivedDate());
            CustomLogger.log("Body: \n" + msg.getContent());
            emailsList.add(new EmailData(msg.getSubject(), msg.getFrom()[0], msg.getAllRecipients()[0], msg.getReceivedDate(), msg.getContent()));
            } catch (javax.mail.MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return emailsList;
    }

    private static void removeAllMessages(Store store, Message[] messages, IMAPFolder folder){
        //remove all messages in the folder after reading them
        Folder trash;
        try {
            trash = store.getFolder(GMAIL_TRASH_FOLDER);
        for (Message m : messages) {
            CustomLogger.log("Removing message with subject " + m.getSubject() + "...");
            folder.copyMessages(new Message[]{m}, trash);
        }
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }

    }

    private static void disconnectFromEmailBox(Store store, IMAPFolder folder) {
        if (folder != null && folder.isOpen()) {
            try {
                folder.close(true);
                if (store != null) {
                    store.close();
                }
            } catch (javax.mail.MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    //Complex public methods
    public static List<EmailData> connectAndGetAllEmailsFromFolder(String folderName) {
        Store store = connectToEmailBox();
        IMAPFolder folder = null;
        List<EmailData> emailsList = null;
        try {
            folder = waitForEmailInFolder(store, folderName);
            Message[] messages = getMessages(folder);
            emailsList = getEmailDataFromMessages(messages);
            removeAllMessages(store, messages, folder);
        } catch (Exception e){
            fail("Error working with remote email service");
        } finally {
            disconnectFromEmailBox(store, folder);
        }
        return emailsList;
    }

    public static void waitForEmailAndRemoveAllMessagesFromFolder(String folderName) {
        Store store = connectToEmailBox();
        IMAPFolder folder = null;
        try {
            folder = waitForEmailInFolder(store, folderName);
            Message[] messages = getMessages(folder);
            removeAllMessages(store, messages, folder);
        } catch (Exception e) {
            fail("Error working with remote email service");
        } finally {
            disconnectFromEmailBox(store, folder);
        }
    }

    public static void connectAndRemoveAllMessagesFromFolder(String folderName) {
        Store store = connectToEmailBox();
        IMAPFolder folder = null;
        try {
            folder = (IMAPFolder) store.getFolder(PARENT_FOLDER + folderName);
            Message[] messages = getMessages(folder);
            removeAllMessages(store, messages, folder);
        } catch (Exception e){
            fail("Error working with remote email service");
        } finally {
            disconnectFromEmailBox(store, folder);
        }
    }

    public static String getLinkFromEmail(EmailData emailData) {
        String fullMessage = emailData.getMessageBody();
        String url = null;
        String regex = "((https://dev.chorusproject.org/)|(https://chorusproject.org/)|(localhost:8080))(.+)(\")";       //finds link, which starts with https://dev.chorusproject.org/ and ends with double quites (including them)
        Matcher m = Pattern.compile(regex).matcher(fullMessage);
        if (m.find()) {
            url = m.group().replaceFirst("\"", ""); //removes double quotes from the link
        }
//        if (url != null && getChorusUrl().equals(localUrl)) {
//            int getIndexOfFirstSlash = url.indexOf("/", 7);
//            url = localUrl.substring(0, 28) + url.substring(getIndexOfFirstSlash);
//        }
        return url;
    }
}