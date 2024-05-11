package com.example.finfolio.Portfolio.Controller.ActifsNonCourants;

import Models.Model;
import com.example.finfolio.Portfolio.Entite.ActifsNonCourants;
import com.example.finfolio.Service.ActifsNCservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Ajouter {

    @FXML
    private Label errorachat;

    @FXML
    private Label errorname;

    @FXML
    private Label errortype;

    @FXML
    private Label errorvaleur;

    @FXML
    private Button idbtn;

    @FXML
    private TextField idname;

    @FXML
    private TextField idprixachat;

    @FXML
    private TextField idtype;

    @FXML
    private TextField iduser;

    @FXML
    private TextField idvaleur;

    @FXML
    void addactif(ActionEvent event) {

        if (idname.getText().isEmpty() || idprixachat.getText().isEmpty() || idtype.getText().isEmpty()
                || idvaleur.getText().isEmpty()) {

            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez remplir tous les champs.");
            return;}
            if (!idname.getText().matches("[a-zA-Z]+") || !idtype.getText().matches("[a-zA-Z]+")) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Les champs 'nom' et 'type' doivent contenir uniquement des lettres.");
                return;
            }
        int valeur = Integer.parseInt(idvaleur.getText());
        int achat = Integer.parseInt(idprixachat.getText());
        String name=idname.getText();
        String type=idtype.getText();

        ActifsNonCourants a=new ActifsNonCourants(name,type,valeur,achat, Model.getInstance().getUser());
        ActifsNCservice anc=new ActifsNCservice();
        anc.add(a);




//            //FXMLLoader loader=new FXMLLoader(getClass().getResource("/afficher.fxml"));
//           // Parent root=loader.load();
            Stage stage = (Stage) idbtn.getScene().getWindow();
            stage.close();




    }
        private void showAlert(Alert.AlertType alertType, String title, String content) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }

    private boolean inputcontrol(){
        if(idname.getText().isEmpty()){
            errorname.setText("Veuillez entrez le nom");
            idname.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            errorname.setStyle("-fx-text-fill: red;");

            return false;
        }
        if(idname.getText().length()<3 || idname.getText().length()>32  ){
            errorname.setText("Nom doit être entre 3 et 32 caractères");
            return false;
        }
        if(idtype.getText().isEmpty()){
            errortype.setText("Veuillez entrez le type");
            idtype.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            errortype.setStyle("-fx-text-fill: red;");

            return false;
        }
        if(idtype.getText().length()<3 || idtype.getText().length()>32  ){
            errortype.setText("Type doit être entre 3 et 32 caractères");
            return false;
        }
        if(idvaleur.getText().isEmpty()){
            errorvaleur.setText("Veuillez la valeur.");
            idvaleur.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            errorvaleur.setStyle("-fx-text-fill: red;");

            return false;}
        if(!idvaleur.getText().matches("\\d{8}")){
            idvaleur.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            errorvaleur.setStyle("-fx-text-inner-color: red;");
            errorvaleur.setText("Valeur non valide");
            return false;}
        if(idprixachat.getText().isEmpty()){
            errorachat.setText("Veuillez le prix");
            idprixachat.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            errorachat.setStyle("-fx-text-fill: red;");

            return false;}
        if(!idprixachat.getText().matches("\\d{8}")){
            idprixachat.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            errorachat.setStyle("-fx-text-inner-color: red;");
            errorachat.setText("Prix non valide");
            return false;}
        return true;
    }
}




