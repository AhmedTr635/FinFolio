package com.example.finfolio.Credits;

import Models.Model;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.OffreService;
import com.example.finfolio.Service.UserService;
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
    private int userId;



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


            int user_Id=1;
            UserService userService = new UserService();
            // Retrieve the User object corresponding to the provided ID
            User user = Model.getInstance().getUser();
           // int userIdValue = Integer.parseInt(userId);




            // Add the offre to the database
            OffreService offreService = new OffreService();
            if (offreService.addOffreToCredit(creditid, montantValue, intretValue, user)) {
                // Afficher une alerte en cas de soumission réussie de l'offre
                showAlert(Alert.AlertType.INFORMATION, "Success", "Offer Submitted",
                        "Your offer has been submitted successfully.");
            } else {
                // Afficher une alerte en cas d'échec de la soumission de l'offre
                showAlert(Alert.AlertType.ERROR, "Error", "Submission Failed",
                        "An error occurred while submitting your offer. Please try again later.");
            }

            // Optionnellement, vous pouvez fermer la fenêtre actuelle après avoir ajouté l'offre
            userIdTextField.getScene().getWindow().hide();
        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion (par exemple, format d'entrée invalide)
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
    }

    public void setUserID(int userId) {

           this.userId=userId;
         userIdTextField.setText(String.valueOf(userId));

    }
}

