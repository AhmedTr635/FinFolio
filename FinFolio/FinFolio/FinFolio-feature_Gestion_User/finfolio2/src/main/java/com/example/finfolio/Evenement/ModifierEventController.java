package com.example.finfolio.Evenement;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Service.EvennementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class ModifierEventController {



    @FXML
    private TextField event_address_modif;

    @FXML
    private TextField event_date_modif;

    @FXML
    private TextField event_montant_modif;

    @FXML
    private TextField event_name_modif;

    private Evennement eventToModify;


    @FXML
    private Button btn_modifier;

    @FXML
    private Text erreur_adresse_modif;

    @FXML
    private Text erreur_date_modif;

    @FXML
    private Text erreur_montant_modif;

    @FXML
    private Text erreur_nom_modif;


    @FXML
    private Text erreur_desc_modif;

    @FXML
    private TextArea event_desc;




    public void initData(Evennement e) {
        eventToModify = e;
        // Set the text fields with the data of the event being modified
        event_name_modif.setText(e.getNom());
        event_date_modif.setText(e.getDate().toString());
        event_address_modif.setText(e.getAdresse());
        event_montant_modif.setText(Float.toString(e.getMontant()));
        event_desc.setText(e.getDescription());
    }
    @FXML
    void modifier_event(ActionEvent event) {
        // Reset error labels
        erreur_nom_modif.setText("");
        erreur_date_modif.setText("");
        erreur_adresse_modif.setText("");
        erreur_montant_modif.setText("");
        erreur_desc_modif.setText("");

        // Get user input
        String nom = event_name_modif.getText();
        String dateText = event_date_modif.getText(); // You need to parse this string to LocalDate
        String adresse = event_address_modif.getText();
        String montantText = event_montant_modif.getText();
        String desc = event_desc.getText();

        // Validate input
        boolean isValid = true;

        if (nom.isEmpty()) {
            erreur_nom_modif.setText("Veuillez saisir un nom.");
            isValid = false;
        }

        LocalDate date = null;
        if (dateText.isEmpty()) {
            erreur_date_modif.setText("Veuillez saisir une date.");
            isValid = false;
        } else {
            try {
                date = LocalDate.parse(dateText);
            } catch (DateTimeParseException e) {
                erreur_date_modif.setText("Format de date incorrect.");
                isValid = false;
            }
        }

        if (adresse.isEmpty()) {
            erreur_adresse_modif.setText("Veuillez saisir une adresse.");
            isValid = false;
        }

        Float montant = null;
        if (montantText.isEmpty()) {
            erreur_montant_modif.setText("Veuillez saisir un montant.");
            isValid = false;
        } else {
            try {
                montant = Float.parseFloat(montantText);
            } catch (NumberFormatException e) {
                erreur_montant_modif.setText("Format de montant incorrect.");
                isValid = false;
            }
        }

        if (desc.isEmpty()) {
            erreur_desc_modif.setText("Veuillez saisir une description.");
            isValid = false;
        }

        if (!isValid) {
            // If input is not valid, don't proceed with updating the event
            return;
        }

        // Update the event information
        eventToModify.setNom(nom);
        eventToModify.setDate(date);
        eventToModify.setAdresse(adresse);
        eventToModify.setMontant(montant);
        eventToModify.setDescription(desc);

        // Call the update method in your service to update the event in the database
        EvennementService.getInstance().update(eventToModify, eventToModify.getId());

        // Close the window after updating the event
        btn_modifier.getScene().getWindow().hide();
    }


    @FXML
    void initialize() {


    }

}

