package Service;

import exception.DAOException;
import exception.OperationException;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ServizioEmail {
    
    private static ServizioEmail sE = null;
    
    public static ServizioEmail getInstance() { 
        
        if (sE == null) 
                sE = new ServizioEmail(); 

        return sE; 
    }

    
    public static void inviaEmail(String toEmail, String subject, String body, String filePath, String fileName, String fromEmail, String fromPassword) throws DAOException, OperationException {
        // Configurazione delle proprietà SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Creazione della sessione con autenticazione
        Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, fromPassword);
                }
        });

        try {
            // Composizione del messaggio
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            if (filePath != null && !filePath.isBlank() && new File(filePath).exists()) {
            // Testo + allegato
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePath));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);
            } else {
            // Solo testo
                message.setText(body);
            }

            // Invio del messaggio
            Transport.send(message);
            // System.out.println("Email inviata con successo!");

        } catch (IOException e) {
            throw new DAOException("Errore nell'accesso al file di allegato.");
        } catch (Exception e) {
            throw new OperationException("Errore generico durante l'invio dell'email.");
        }
    }
}

