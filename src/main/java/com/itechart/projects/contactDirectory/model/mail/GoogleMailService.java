package com.itechart.projects.contactDirectory.model.mail;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GoogleMailService implements MailService, Runnable {

    private static final String GMAIL_PROPERTY = "GMail";
    private String username;
    private String password;
    private Properties properties;
    private Session session;
    private Mail mail;

    public GoogleMailService() {
        ResourceBundle bundle = ResourceBundle.getBundle(GMAIL_PROPERTY);
        username = bundle.getString("email");
        password = bundle.getString("password");
        properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        session = Session.getInstance(properties,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    @Override
    public void sendMail(Mail mail) {
        this.mail = mail;
 
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));
            //2
            for (Contact c : mail.getContacts()) {
                //3
                if (c.getEmail() != null && c.getEmail().trim().length() > 0) {
                    //4
                    message.addRecipient(Message.RecipientType.TO,
                            new InternetAddress(c.getEmail()));
                }
            }
            //5
            message.setSubject(mail.getTitle());
            message.setText(mail.getMessage());

            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

}
