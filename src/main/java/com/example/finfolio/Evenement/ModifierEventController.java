package com.example.finfolio.Evenement;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Service.EvennementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


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



    public void initData(Evennement e) {
        eventToModify = e;
        // Set the text fields with the data of the event being modified
        event_name_modif.setText(e.getNom());
        event_date_modif.setText(e.getDate().toString());
        event_address_modif.setText(e.getAdresse());
        event_montant_modif.setText(Float.toString(e.getMontant()));
    }
    @FXML
    void modifier_event(ActionEvent event) {
        String nom = event_name_modif.getText();
        String date = event_date_modif.getText(); // You need to parse this string to LocalDate
        String adresse = event_address_modif.getText();
        Float montant = Float.parseFloat(event_montant_modif.getText());

        // Update the event information
        eventToModify.setNom(nom);
        eventToModify.setDate(LocalDate.parse(date));
        eventToModify.setAdresse(adresse);
        eventToModify.setMontant(montant);

        // Call the update method in your service to update the event in the database
        EvennementService.getInstance().update(eventToModify, eventToModify.getId());

        // Close the window after updating the event
        btn_modifier.getScene().getWindow().hide();
    }


    @FXML
    void initialize() {


    }

}

