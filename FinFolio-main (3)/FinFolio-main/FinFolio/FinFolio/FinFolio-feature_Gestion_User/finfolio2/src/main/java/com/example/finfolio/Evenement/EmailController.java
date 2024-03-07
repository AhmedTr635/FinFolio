package com.example.finfolio.Evenement;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class EmailController {

    public static void sendEmail(String to, String subject, String body) {
        // Informations de connexion au serveur SMTP
        final String username = "finfoliofinfolio@gmail.com";
        final String password = "bsmkekkpxfjfkzie";

        // Paramètres pour le serveur SMTP de Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Création d'une session pour l'envoi d'email
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Création du message email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            // Envoi du message
            Transport.send(message);

            System.out.println("Email envoyé avec succès!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
