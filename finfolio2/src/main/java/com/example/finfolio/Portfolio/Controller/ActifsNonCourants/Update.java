package com.example.finfolio.Portfolio.Controller.ActifsNonCourants;

import com.example.finfolio.Portfolio.Entite.ActifsNonCourants;
import com.example.finfolio.Service.ActifsNCservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Update {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField achat_id;

    @FXML
    private Button confirmid;

    @FXML
    private Label id_achat;

    @FXML
    private Label id_name;

    @FXML
    private Label id_type;

    @FXML
    private Label id_valeur;

    @FXML
    private TextField name_id;

    @FXML
    private TextField type_id;

    @FXML
    private TextField valeur_id;


    private ActifsNonCourants a;


    public void setA(ActifsNonCourants a) {
        this.a = a;
    }

    @FXML
    void updateActif(ActionEvent event) {


        if (name_id.getText().isEmpty() || achat_id.getText().isEmpty() || type_id.getText().isEmpty()
                || valeur_id.getText().isEmpty()) {

            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez remplir tous les champs.");
            return;}
        if (!name_id.getText().matches("[a-zA-Z]+") || !type_id.getText().matches("[a-zA-Z]+")) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Les champs 'nom' et 'type' doivent contenir uniquement des lettres.");
            return;
        }


        float valeur = Float.parseFloat(valeur_id.getText());
        float achat = Float.parseFloat(achat_id.getText());
        String name=name_id.getText();
        String type=type_id.getText();


        ActifsNCservice ans=new ActifsNCservice();
        ans.update(this.a,name,type,valeur,achat);
        Stage stage = (Stage) confirmid.getScene().getWindow();
        stage.close();





    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    public void initData(ActifsNonCourants selectedItem) {
        id_name.setText(selectedItem.getName());
        id_type.setText(selectedItem.getType());
        id_valeur.setText(String.valueOf(selectedItem.getValeur()));
        id_achat.setText(String.valueOf(selectedItem.getPrix_achat()));
        name_id.setText(selectedItem.getName());
        type_id.setText(selectedItem.getType());
        valeur_id.setText(String.valueOf(selectedItem.getValeur()));
        achat_id.setText(String.valueOf(selectedItem.getPrix_achat()));


    }




}



