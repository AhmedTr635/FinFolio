package com.example.finfolio.Portfolio.Controller.ActifsCourants;

import com.example.finfolio.Portfolio.Entite.ActifsCourants;
import com.example.finfolio.Service.ActifsCservices;
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
    private Button confirmid;

    @FXML
    private Label id_montant;

    @FXML
    private Label id_name;

    @FXML
    private Label id_type;





    @FXML
    private TextField montant_id;

    @FXML
    private TextField name_id;

    @FXML
    private TextField type_id;

    private ActifsCourants a;


    public void setA(ActifsCourants a) {
        this.a = a;
    }

    @FXML
    void updateActif(ActionEvent event) {
        if (montant_id.getText().isEmpty() || name_id.getText().isEmpty() || type_id.getText().isEmpty()  ) {

            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez remplir tous les champs.");
            return;}

        if (!montant_id.getText().matches("\\d+(\\.\\d+)?")  ) {

            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez entrer des valeurs numériques valides pour le montant et l'utilisateur.");
            return;
        }

        if (!name_id.getText().matches("[a-zA-Z]+") || !type_id.getText().matches("[a-zA-Z]+")) {

            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Les champs 'nom' et 'type' doivent contenir uniquement des lettres.");
            return;
        }

        float montant = Float.parseFloat(montant_id.getText());
        String name=name_id.getText();
        String type=type_id.getText();


        ActifsCservices as=new ActifsCservices();
        as.update(this.a,name,montant,type);
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







    public void initData(ActifsCourants selectedItem) {
        id_name.setText(selectedItem.getName());
        id_type.setText(selectedItem.getType());
        id_montant.setText(String.valueOf(selectedItem.getMontant()));
        name_id.setText(selectedItem.getName());
        type_id.setText(selectedItem.getType());
        montant_id.setText(String.valueOf(selectedItem.getMontant()));


    }




}


