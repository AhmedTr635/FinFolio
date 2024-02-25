package com.example.gestioncredit1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CreditAnchorpane {

    @FXML
    private Label nameLabel;

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
    private Button contactButton;

    @FXML
    private Button sendButton;
    private String creditID;

    public void initialize(String id, String montant, String interetMax, String interetMin, String dateDebut, String dateFin, String creditID, String userName) {
        this.creditID = creditID; // Set credit ID
        idLabel.setText("ID: " + id);
        montantLabel.setText("Montant: " + montant);
        interetMaxLabel.setText("Intérêt Max: " + interetMax);
        interetMinLabel.setText("Intérêt Min: " + interetMin);
        dateDebutLabel.setText("Date Début: " + dateDebut);
        dateFinLabel.setText("Date Fin: " + dateFin);
        nameLabel.setText("Nom: " + userName); // Set the user name label
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

}
