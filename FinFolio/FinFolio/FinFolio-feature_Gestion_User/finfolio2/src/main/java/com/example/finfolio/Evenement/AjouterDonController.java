package com.example.finfolio.Evenement;

import Models.Model;
import com.example.finfolio.Entite.Don;
import com.example.finfolio.Service.DonService;
import com.example.finfolio.Service.EvennementService;
import com.example.finfolio.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;


public class AjouterDonController {

    @FXML
    private Button btnAdd;


    @FXML
    public TextField montant_field;

    @FXML
    private Label event_id;

    @FXML
    private Label user_id;
    @FXML
    void annuler(ActionEvent event) {

    }
    public void setEventId(String id) {
        event_id.setText(id);
    }


    @FXML
    void faireundon(ActionEvent event) throws SQLException {

        // Get the donation amount from the TextField
        String montantText = montant_field.getText();
        String eventIdText = event_id.getText();
        String userIdText = user_id.getText();



        // Check if the montantText is not empty
        if (!montantText.isEmpty()) {
            // Parse the montantText to a double (assuming montant is a decimal value)
            float montant = Float.parseFloat(montantText);
            int eventId = Integer.parseInt(String.valueOf(eventIdText));
            int userId = Integer.parseInt(String.valueOf(1));
            EvennementService es =new EvennementService();
            UserService us = new UserService();

            // Create a new Don object

            Don donation = new Don(montant, Model.getInstance().getUser(), es.readById(eventId));

            // Save the donation using the DonService
            DonService.getInstance().add(donation);

            Stage currentStage = (Stage) btnAdd.getScene().getWindow();
            currentStage.close();

            // Alert for don
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Don fait avec succès");
            alert.showAndWait();

EmailController ec = new EmailController();
            ec.sendEmail("siwarbouali27@gmail.com", "Invitation à l'événement", "Bonjour, vous êtes invité à participer à notre événement. Cordialement, Finfolio");


            // Optionally, display a success message or close the window

        }
    }





}
