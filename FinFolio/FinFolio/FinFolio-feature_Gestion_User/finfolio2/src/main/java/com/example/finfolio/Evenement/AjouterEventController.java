package com.example.finfolio.Evenement;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Service.EvennementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

import javafx.scene.control.*;
import javafx.stage.Stage;


public class AjouterEventController {


    @FXML
    private Button btn_ajouter;

    @FXML
    private TextField event_address;

    @FXML
    private DatePicker event_date;


    @FXML
    private TextField event_montant;

    @FXML
    private TextField event_name;

    @FXML
    private Label saisie_addresse;

    @FXML
    private Label saisie_date;

    @FXML
    private Label saisie_montant;

    @FXML
    private Label saisie_nom;

    @FXML
    private TextArea description_event;


    @FXML
    void initialize() {
        // Add a listener to the valueProperty of the DatePicker
        event_date.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the selected date is not null and is in the future
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                // Show an error message and reset the value to null
                showAlert("Erreur", "Veuillez sélectionner une date future.", Alert.AlertType.ERROR);
                event_date.setValue(null);
            }
        });

        // Clear validation labels when the user starts typing in the text fields
        event_name.setOnKeyTyped(event -> saisie_nom.setText(""));
        event_address.setOnKeyTyped(event -> saisie_addresse.setText(""));
        event_date.setOnMouseClicked(event -> saisie_date.setText(""));
        event_montant.setOnKeyTyped(event -> saisie_montant.setText(""));
    }

    @FXML
    void ajouter_event(ActionEvent event) {
        // Récupérer les valeurs saisies dans les champs
        String nom = event_name.getText().trim();
        LocalDate date = event_date.getValue();
        String adresse = event_address.getText().trim();
        String montantText = event_montant.getText().trim();
        String description = description_event.getText();

        // Vérifier si tous les champs sont remplis
        if (nom.isEmpty() || date == null || adresse.isEmpty() || montantText.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
            if (nom.isEmpty()) saisie_nom.setText("Ce champ est obligatoire");
            if (date == null) saisie_date.setText("Ce champ est obligatoire");
            if (adresse.isEmpty()) saisie_addresse.setText("Ce champ est obligatoire");
            if (montantText.isEmpty()) saisie_montant.setText("Ce champ est obligatoire");
            return;
        }

        // Vérifier si le montant est un nombre valide
        Float montant;
        try {
            montant = Float.valueOf(montantText);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le montant doit être un nombre valide.", Alert.AlertType.ERROR);
            saisie_montant.setText("Le montant doit être un nombre valide");
            return;
        }

        // Créer un nouvel événement
        Evennement e = new Evennement(nom, montant, date, adresse, description);
        EvennementService evs = new EvennementService();
        evs.add(e);

        // Fermer la fenêtre actuelle
        Stage stage = (Stage) btn_ajouter.getScene().getWindow();
        stage.close();

        // Afficher un message de succès
        showAlert("Succès", "Événement ajouté avec succès.", Alert.AlertType.INFORMATION);
    }

    // Méthode utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}




