package ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class EmailBean {

    @Resource(name = "java:/jboss/mail/fakeSMTP")
    private Session mailSession;

    private static final Logger LOGGER = Logger.getLogger("EmailBean.LOGGER");

    public void send(String to, String subject, String text) throws MessagingException {

        Thread emailJob = new Thread(() -> {
            Message message = new MimeMessage(mailSession);
            try{
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

                message.setSubject(subject);

                message.setText(text);

                Date timeStamp = new Date();
                message.setSentDate(timeStamp);

                Transport.send(message);
            } catch (MessagingException messagingException) {
                //throw messagingException;
                LOGGER.log(Level.SEVERE, messagingException.getMessage());
            }
        });

        emailJob.start();
    }
}
