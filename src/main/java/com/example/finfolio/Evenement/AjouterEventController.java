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
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    void initialize() {
        inputcontrol();

    }

    @FXML
    void ajouter_event(ActionEvent event) {
        String nom = event_name.getText();
        LocalDate date = event_date.getValue();
        String adresse = event_address.getText();
        Float montant = Float.valueOf(event_montant.getText());

        Evennement e = new Evennement(nom, montant, date, adresse);
        EvennementService evs = new EvennementService();
        evs.add(e);

        Stage stage = (Stage) btn_ajouter.getScene().getWindow();
        stage.close();



        // Méthode utilitaire pour afficher une boîte de dialogue d'alerte
    }
    private boolean inputcontrol(){
        if(event_name.getText().isEmpty()){
            saisie_nom.setText("Veuillez entrer le nom de l'evennement");
            event_name.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            saisie_nom.setStyle("-fx-text-fill: red;");

            return false;
        }
        if(event_name.getText().length()<3 || event_name.getText().length()>32  ){
            saisie_nom.setText("Nom doit être entre 3 et 32 caractères");
            return false;
        }
        if(event_date.getValue()==null){
            saisie_date.setText("Veuillez entrer la date");
            event_date.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            saisie_date.setStyle("-fx-text-fill: red;");

            return false;
        }

        if(event_address.getText().isEmpty()){
            saisie_addresse.setText("Veuillez entrer l'adresse.");
            event_address.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            saisie_addresse.setStyle("-fx-text-fill: red;");

            return false;}

        if(!event_montant.getText().matches("^\\d+(\\.\\d{1,2})?$")){
            saisie_montant.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            saisie_montant.setText("Numéro de téléphone doit être exactement 8 chiffres");
            event_montant.setStyle("-fx-text-fill: red;");
            return false;}



        return true;
    }
}

