package com.example.gestioncredit1;

import Entity.Offre;
import Service.OffreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class SendOffre {

    @FXML
    private TextField creditidTextField;

    @FXML
    private TextField montantTextField;

    @FXML
    private TextField intretTextField;

    @FXML
    private TextField userIdTextField;

    private String creditID;

    public void initialize() {
        // Initialize the creditID text field with the passed creditID
        creditidTextField.setText(creditID);
    }

    public void setCreditID(String creditID) {
        this.creditID = creditID;
        creditidTextField.setText(creditID);
    }

    @FXML
    public void handleSendButton(ActionEvent actionEvent) {
        // Get the values from the text fields
        String creditID = creditidTextField.getText();
        String montant = montantTextField.getText();
        String intret = intretTextField.getText();
        String userId = userIdTextField.getText();

        // Check if any of the fields are empty
        if (creditID.isEmpty() || montant.isEmpty() || intret.isEmpty() || userId.isEmpty()) {
            // Show an alert for incomplete information
            showAlert(Alert.AlertType.ERROR, "Error", "Incomplete Information", "Please fill in all fields.");
            return;
        }

        try {
            // Convert String inputs to appropriate data types
            int creditid= Integer.parseInt(creditID);
            double montantValue = Double.parseDouble(montant);
            double intretValue = Double.parseDouble(intret);
            int userIdValue = Integer.parseInt(userId);




            // Add the offre to the database
            OffreService offreService = new OffreService();
            if (offreService.addOffreToCredit(creditid, montantValue, intretValue, userIdValue)) {
                // Show an alert for successful offer submission
                showAlert(Alert.AlertType.ERROR, "Success", "Submission Failed",
                        "An error occurred while submitting your offer. Please try again later.");
            } else {
                // Show an alert for offer submission failure
                showAlert(Alert.AlertType.INFORMATION, "Error", "Offer Submitted",
                        "Your offer has been submitted successfully.");
            }

            // Optionally, you can close the current window after adding the offre
            userIdTextField.getScene().getWindow().hide();
        } catch (NumberFormatException e) {
            // Handle parsing errors (e.g., invalid input format)
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Input",
                    "Please enter numeric values for Montant and Intret, and an integer for User ID.");
        }
    }

    // Method to show an alert with the given parameters
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }}

