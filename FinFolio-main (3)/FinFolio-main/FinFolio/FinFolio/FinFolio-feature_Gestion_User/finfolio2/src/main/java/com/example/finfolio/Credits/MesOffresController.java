package com.example.finfolio.Credits;

import Models.Model;
import com.example.finfolio.Entite.Credit;
import com.example.finfolio.Entite.Offre;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.CreditService;
import com.example.finfolio.Service.OffreService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MesOffresController {
    public Label montantErrorMessageLabel;
    public Label intretErrorMessageLabel;
    public Label interetErrorMessageLabel;
    private Map<Integer, String> userIdToNameMap;
    @FXML
    public TableColumn user_name_offre;
    @FXML
    private Button Update_offre;

    @FXML
    private TextField my_id_offre;

    @FXML
    private TextField myuser_id;

    @FXML
    private TextField my_icreditid;
////////////////////////////////////////

    @FXML
    private TableView<Offre> tableau_Offre;

    @FXML
    private TableColumn<Offre, Double> montant_offre;

    @FXML
    private TableColumn<Offre, Integer> user_id_offre;

    @FXML
    private TableColumn<Offre, Integer> credit_id_offre1;

    @FXML
    private TableColumn<Offre, Double> intret_offre;

    @FXML
    private TableColumn<Offre, Integer> id_offre;

    @FXML
    private Button delete_offre;
    @FXML
    private TextField interetInput;
    @FXML
    private TextField montantInput;

    private ObservableList<Offre> observableList2;

    public void initialize() {
        OffreService offreService = new OffreService();
        CreditService creditService = new CreditService();

        // Fetch all credits and build a map of user IDs to user names
        List<Credit> allCredits = creditService.readAllCredits();
        Map<Integer, String> userIdToNameMap = new HashMap<>();
        for (Credit credit : allCredits) {
            userIdToNameMap.put(credit.getUser().getId(), credit.getUserName());
        }
        User user = Model.getInstance().getUser();

        observableList2 = FXCollections.observableList(offreService.getOffersByUserId(user.getId()));

        tableau_Offre.setItems(observableList2);

        id_offre.setCellValueFactory(new PropertyValueFactory<>("id"));
        montant_offre.setCellValueFactory(new PropertyValueFactory<>("montant"));
        intret_offre.setCellValueFactory(new PropertyValueFactory<>("interet"));
        user_id_offre.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        credit_id_offre1.setCellValueFactory(new PropertyValueFactory<>("credit_id"));

        // Add a new TableColumn for user name
        TableColumn<Offre, String> user_name_offre = new TableColumn<>("User Name");
        user_name_offre.setCellValueFactory(cellData -> {
            int userId = cellData.getValue().getUser_id();
            String userName = userIdToNameMap.getOrDefault(userId, "Unknown"); // Get user name from the map
            return new SimpleStringProperty(userName);
        });
        tableau_Offre.getColumns().add(user_name_offre);

        tableau_Offre.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Populate input fields with selected offre's details
                Offre selectedOffre = tableau_Offre.getSelectionModel().getSelectedItem();
                montantInput.setText(String.valueOf(selectedOffre.getMontant()));
                interetInput.setText(String.valueOf(selectedOffre.getInteret()));

                // Hide other fields
                my_id_offre.setVisible(false);
                myuser_id.setVisible(false);
                my_icreditid.setVisible(false);
            }
        });
    }


    @FXML
    void Update_offre(ActionEvent event) {
        // Get the selected offer
        Offre selectedOffre = tableau_Offre.getSelectionModel().getSelectedItem();

        if (selectedOffre == null) {
            showAlert("Veuillez sélectionner une offre à mettre à jour.", Alert.AlertType.ERROR);
            return;
        }

        // Prompt the user for confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Voulez-vous vraiment mettre à jour cette offre ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed, proceed with the update
            try {
                // Get updated montant and intret values from the input fields
                double updatedMontant = Double.parseDouble(montantInput.getText());
                double updatedIntret = Double.parseDouble(interetInput.getText());

                // Update the offer object with the new values
                selectedOffre.setMontant(updatedMontant);
                selectedOffre.setInteret(updatedIntret);

                // Call the service to update the offer in the database
                OffreService offreService = new OffreService();
                boolean isUpdated = offreService.updateOffre(selectedOffre);

                // Show appropriate message based on update success or failure
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                if (isUpdated) {
                    alert.setContentText("Offre mise à jour avec succès.");
                } else {
                    alert.setContentText("Échec de la mise à jour de l'offre.");
                }
                alert.showAndWait();

                // Refresh the table view to reflect the changes
                refreshTable();
            } catch (NumberFormatException e) {
                showAlert("Veuillez entrer des valeurs valides pour le montant et l'intérêt.", Alert.AlertType.ERROR);
            }
        }
    }


    @FXML
    void delete_offre(ActionEvent event) {
        Offre selectedOffre = tableau_Offre.getSelectionModel().getSelectedItem();

        if (selectedOffre == null) {
            showAlert("Veuillez sélectionner une offre à supprimer.", Alert.AlertType.ERROR);
            return;
        }

        int offreId = selectedOffre.getId();
        OffreService offreService = new OffreService();
        boolean isDeleted = offreService.deleteOffre(offreId);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        if (isDeleted) {
            alert.setContentText("Offre supprimée avec succès.");
        } else {
            alert.setContentText("Échec de la suppression de l'offre. Aucune offre trouvée avec l'ID spécifié.");
        }
        alert.showAndWait();

        refreshTable();
    }

    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void refreshTable() {
        OffreService of = new OffreService();
        observableList2.clear();
        observableList2.addAll(of.getAllOffres());
    }


    @FXML
    void montantinput(KeyEvent event) {
        handleNumericInput(montantInput, montantErrorMessageLabel, event);
    }

    @FXML
    void intretinput(KeyEvent event) {
        handleNumericInput(interetInput, intretErrorMessageLabel, event);
    }

    private void handleNumericInput(TextField textField, Label montantErrorMessageLabel, KeyEvent event) {
        String character = event.getCharacter();
        if (!character.matches("[0-9]")) {
            event.consume(); // Consume the event to prevent the character from being entered
            textField.setStyle("-fx-border-color: red;");
            textField.setStyle("-fx-text-fill: red;");
            switch (textField.getId()) {
                case "montantInput":
                    this.montantErrorMessageLabel.setTextFill(Color.RED);
                    this.montantErrorMessageLabel.setText("Veuillez entrer un numéro valide.");
                    break;
                case "interetInput":
                    interetErrorMessageLabel.setTextFill(Color.RED);
                    interetErrorMessageLabel.setText("Veuillez entrer un numéro valide.");
                    break;
            }
        } else {
            textField.setStyle("-fx-border-color: blue;");
            textField.setStyle("-fx-text-fill: black;");
            switch (textField.getId()) {
                case "montantInput":
                    this.montantErrorMessageLabel.setText("");
                    break;
                case "interetInput":
                    interetErrorMessageLabel.setText("");
                    break;
            }
        }
    }
}


