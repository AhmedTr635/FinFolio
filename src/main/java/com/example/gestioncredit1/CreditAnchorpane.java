package com.example.gestioncredit1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.example.gestioncredit1.PDFGenerator.generatePDF;

//import static jdk.jpackage.internal.WixAppImageFragmentBuilder.ShortcutsFolder.Desktop;

//import static jdk.jpackage.internal.WixAppImageFragmentBuilder.ShortcutsFolder.Desktop;

//import static jdk.jpackage.internal.WixAppImageFragmentBuilder.ShortcutsFolder.Desktop;

public class CreditAnchorpane {

    @FXML
    private Label nameLabel;

    @FXML
    private Button contactButton;

    @FXML
    private Label labedpdf;
    @FXML
    private Label idLabeluser;

    @FXML
    private Label idLabel;

    @FXML
    private Label montantLabel;

    @FXML
    private Label interetMaxLabel;

    @FXML
    private Label interetMinLabel;

    @FXML
    private Label dateDebutLabel;

    @FXML
    private Label dateFinLabel;



    @FXML
    private Button sendButton;
    private String creditID;
    private int userId;


    public void initialize(String id, String montant, String interetMax, String interetMin, String dateDebut, String dateFin, String userId, String userName, String creditID, String name) {
        this.creditID = creditID; // Set credit ID
        this.userId = Integer.parseInt(userId);

        idLabel.setText("ID: " + id); // Set the credit ID label
        montantLabel.setText("Montant: " + montant);
        interetMaxLabel.setText("Intérêt Max: " + interetMax);
        interetMinLabel.setText("Intérêt Min: " + interetMin);
        dateDebutLabel.setText("Date Début: " + dateDebut);
        dateFinLabel.setText("Date Fin: " + dateFin);
        nameLabel.setText("Nom: " + userName); // Set the user name label
        idLabeluser.setText("User ID: " + userId); // Set the user ID label


    }





    @FXML
    private void handleContactButton() {
        // Handle contact button action
    }

    @FXML
    private void handleSendButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sendOffre.fxml"));
            Parent root = loader.load();

            // Access the controller of the loaded FXML file
            SendOffre sendOffreController = loader.getController();

            // Set the creditID in the SendOffreController
            sendOffreController.setCreditID(creditID);
            ;
            sendOffreController.setUserID(userId);

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Send Offer"); // Set the title of the stage

            // Show the stage
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }





    public void generatepdfclick(ActionEvent actionEvent) {

        try {
            List<Label> creditDetails = Arrays.asList(montantLabel, interetMaxLabel, interetMinLabel, dateDebutLabel, dateFinLabel,nameLabel);

            // Prompt user to choose a download location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.setInitialFileName("credit_details.pdf");
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                String filePath = file.getAbsolutePath();
                generatePDF(filePath, getLabelTexts(creditDetails),nameLabel.getText());

                // Handle opening file using platform-specific method
                openFile(file);
            }
        } catch (IOException ex) {
            System.err.println("Error generating or opening PDF: " + ex.getMessage());
        }
    }

    // Method to extract text from JavaFX Labels
    private List<String> getLabelTexts(List<Label> labels) {
        return labels.stream()
                .map(Label::getText)
                .toList();
    }

    // Platform-specific file opening method
    private void openFile(File file) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // For Windows
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
            } else if (os.contains("mac")) {
                // For macOS
                Runtime.getRuntime().exec("open " + file.getAbsolutePath());
            } else {
                // For Linux/Unix
                Runtime.getRuntime().exec("xdg-open " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void contacterbutton(ActionEvent event) {
        try {
            // Load the FXML file of the chat room UI
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chatroom.fxml"));
            Parent root = loader.load();

            Chatroom chatroomController = loader.getController();

            // Pass the creditID and userId to the ChatroomController
            chatroomController.setCreditID(creditID);
           // chatroomController.setUserID(userId);

            // Create a new stage for the chat room
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Chat Room"); // Set the title of the stage

            // Show the chat room stage
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }



    }



    }


