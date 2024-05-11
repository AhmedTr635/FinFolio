package com.example.finfolio.Evenement;


import com.example.finfolio.UsrController.QRCodeApi;
import com.google.zxing.WriterException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;


public class EmailController {

    public static void sendEmail(String to, String subject, String body, String eventInfo) {
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
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Generate QR code
            String qrCodePath = QRCodeApi.getPath();
            QRCodeApi qrCodeApi = new QRCodeApi();
            qrCodeApi.GenereQrCodeEvent(eventInfo);

            // Create email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);

            // Create email body with QR code image
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body + "<img src='cid:qr_code'/>", "text/html");
            multipart.addBodyPart(messageBodyPart);

            // Attach QR code image to email
            MimeBodyPart qrCodePart = new MimeBodyPart();
            qrCodePart.attachFile(qrCodePath);
            qrCodePart.setContentID("<qr_code>");
            qrCodePart.setDisposition(MimeBodyPart.INLINE);
            multipart.addBodyPart(qrCodePart);

            // Set the email content
            message.setContent(multipart);

            // Send email
            Transport.send(message);

            System.out.println("Email envoyé avec succès!");

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }


    public static void sendInvitationEmail(String to, String subject, String eventInfo) {
        try {
            sendEmail(to, subject, generateInvitationBody(eventInfo), eventInfo);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private static String generateInvitationBody(String eventInfo) {
        // Customize the email body as needed
        return "<html><head><style>body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; text-align: center; } .container { max-width: 600px; margin: 20px auto; background-color: #fff; border-radius: 10px; box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1); padding: 40px; } h1 { color: #333; margin-bottom: 20px; } p { color: #666; line-height: 1.6; margin-bottom: 20px; } .event-details { background-color: #f9f9f9; border-radius: 10px; padding: 20px; margin-bottom: 20px; } .cta-button { display: inline-block; background-color: #007bff; color: #fff; text-decoration: none; padding: 10px 20px; border-radius: 5px; transition: background-color 0.3s ease; } .cta-button:hover { background-color: #0056b3; } .footer { margin-top: 20px; color: #999; } .footer a { color: #007bff; text-decoration: none; }</style></head><body><div class=\"container\"><h1>You're Invited!</h1><p>You're invited to attend an exclusive event. Join us for an unforgettable experience!</p><div class=\"event-details\"><h2>Event Details:</h2>" + eventInfo + "</div><p>RSVP by clicking the button below:</p><a href=\"[RSVP Link]\" class=\"cta-button\">RSVP Now</a><p class=\"footer\">For inquiries, please contact Finfolio or visit our <a href=\"[Website URL]\">website</a>.</p></div></body></html>";
    }
}
