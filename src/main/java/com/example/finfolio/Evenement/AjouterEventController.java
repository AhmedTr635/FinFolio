package com.example.finfolio.Evenement;

import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;


import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Service.EvennementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AjouterEventController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_ajouter;

    @FXML
    private TextField event_address;

    @FXML
    private TextField event_date;

    @FXML
    private TextField event_montant;

    @FXML
    private TextField event_name;

    @FXML
    void ajouter_event(ActionEvent event) {
        String nom = event_name.getText();
        LocalDate date  = LocalDate.parse(event_date.getText());
        String adresse = event_address.getText();
        Float montant = Float.valueOf(event_montant.getText());

        Evennement e = new Evennement(nom,montant,date,adresse);
        EvennementService evs = new EvennementService();
        evs.add(e);

        Stage stage = (Stage) btn_ajouter.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {

    }

}

