package com.example.finfolio.UsrController;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;

public class EmailingApi {



    public void sendRecoveryCode(String userEmail, String verificationCode) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("finfoliofinfolio@gmail.com", "txzoffvmvmoiuyzw");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("finfoliofinfolio@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject("Verification Code for Your Account");
            message.setText("Votre code de vérification est : " + verificationCode);

            Transport.send(message);
            System.out.println("Email notification sent to " + userEmail);
        } catch (Exception e) {
            System.out.println("Error sending email notification: " + e.getMessage());
        }
    }
    public void sendEmailWithAttachment(String userEmail, String subject, String messageBody, String attachmentPath) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("finfoliofinfolio@gmail.com", "txzoffvmvmoiuyzw");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("finfoliofinfolio@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(messageBody);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Attach the file
            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                addAttachment(multipart, attachmentPath);
            }

            // Set the multipart message content
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println("Email with attachment sent to " + userEmail);
        } catch (Exception e) {
            System.out.println("Error sending email with attachment: " + e.getMessage());
        }
    }

    private void addAttachment(Multipart multipart, String filePath) throws MessagingException {
        DataSource source = new FileDataSource(filePath);
        BodyPart attachmentBodyPart = new MimeBodyPart();
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(new File(filePath).getName());
        multipart.addBodyPart(attachmentBodyPart);
    }
    public void sendProbleme(String userEmail, String verificationCode) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("finfoliofinfolio@gmail.com", "txzoffvmvmoiuyzw");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("finfoliofinfolio@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject("Réclammation ");
            message.setText(" A propos de votre reclamation: " + verificationCode);

            Transport.send(message);
            System.out.println("Email notification sent to " + userEmail);
        } catch (Exception e) {
            System.out.println("Error sending email notification: " + e.getMessage());
        }
    }
}

