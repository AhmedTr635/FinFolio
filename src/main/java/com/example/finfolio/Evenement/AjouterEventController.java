package com.example.finfolio.Evenement;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Service.EvennementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    void initialize() {


    }

    @FXML
    void ajouter_event(ActionEvent event) {
        // Récupérer les valeurs saisies dans les champs
        String nom = event_name.getText();
        LocalDate date = event_date.getValue();
        String adresse = event_address.getText();
        String montantText = event_montant.getText();

        // Vérifier si tous les champs sont remplis
        if (nom.isEmpty() || date == null || adresse.isEmpty() || montantText.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
            return;
        }

        // Vérifier si le montant est un nombre valide
        Float montant;
        try {
            montant = Float.valueOf(montantText);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le montant doit être un nombre valide.", Alert.AlertType.ERROR);
            return;
        }

        // Créer un nouvel événement
        Evennement e = new Evennement(nom, montant, date, adresse);
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
    }}




