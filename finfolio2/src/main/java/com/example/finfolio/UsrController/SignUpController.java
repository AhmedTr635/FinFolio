package com.example.finfolio.UsrController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Models.Model;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController  implements Initializable {



    @FXML
    private Button engistrez_fld;

    @FXML
    private Label error_mail;

    @FXML
    private Label error_mdp;

    @FXML
    private Label error_nom;

    @FXML
    private Label error_ntel;

    @FXML
    private Label error_prenom;

    @FXML
    private TextField mail_fld;

    @FXML
    private TextField modp_field;

    @FXML
    private TextField nom_fld;

    @FXML
    private TextField numTelfld;

    @FXML
    private TextField prenom_fld;



    @FXML
    private ChoiceBox<String> role_choicebox;
    private String[] roles = {"Role", "user", "admin"};

    public void getRoles(ActionEvent event) {

        String myRoles = role_choicebox.getValue();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        role_choicebox.getItems().addAll(roles);
        role_choicebox.setOnAction(this::getRoles);
        role_choicebox.setValue("Role");
        engistrez_fld.setOnAction(e-> {
            try {
                onEngistrez();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    void onEngistrez() throws SQLException, IOException {
        String role = "";
        if (role_choicebox.getValue() == "User") {
            role = "user";
        } else if (role_choicebox.getValue() == "Admin") {
            role = "admin";
        } else {
            role = "user";
        }

        if (inputcontrol()) {
            User user = new User(nom_fld.getText(), prenom_fld.getText(), mail_fld.getText(), "+216"+numTelfld.getText(), modp_field.getText(), "Mourouj", 0, 2, role,"20000");
            UserService userS = new UserService();
            userS.add(user);
            Stage st = (Stage) error_mdp.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(st);
            Model.getInstance().getViewFactory().showLoginWindow();
        }

    }
    private boolean inputcontrol(){
        if(nom_fld.getText().isEmpty()){
            error_nom.setText("Veuillez entrez votre nom");
            nom_fld.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            error_nom.setStyle("-fx-text-fill: red;");

            return false;
        }
        if(nom_fld.getText().length()<3 || nom_fld.getText().length()>32  ){
            error_nom.setText("Nom doit être entre 3 et 32 caractères");
            return false;
        }
        if(prenom_fld.getText().isEmpty()){
            error_prenom.setText("Veuillez entrez votre prénom");
            prenom_fld.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            error_prenom.setStyle("-fx-text-fill: red;");

            return false;
        }
        if(prenom_fld.getText().length()<3 || prenom_fld.getText().length()>32  ){
            error_prenom.setText("Prénom doit être entre 3 et 32 caractères");
            return false;
        }
        if(mail_fld.getText().isEmpty()){
            error_mail.setText("Veuillez entre votre email.");
            mail_fld.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            error_mail.setStyle("-fx-text-fill: red;");

            return false;}
        if(!mail_fld.getText().matches(("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"))){
            mail_fld.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            error_mail.setStyle("-fx-text-inner-color: red;");
            error_mail.setText("Email non valide");
            return false;}
        if(!numTelfld.getText().matches("\\d{8}")){
            error_ntel.setStyle("-fx-text-inner-color: red;-fx-border-color: red");
            error_ntel.setText("Numéro de téléphone doit être exactement 8 chiffres");
            numTelfld.setStyle("-fx-text-fill: red;");
            return false;}
        if(!modp_field.getText().matches("^(?=.*[A-Z])(?=.*[a-z]).{8,}$")){
            error_mdp.setText("Mot de Passe doit être composé au moins de 8 caractères au min l'un est majuscule ");
            modp_field.setStyle("-fx-text-fill: red;");
            error_mdp.setStyle("-fx-text-inner-color: red;-fx-border-color: red");

            return false;}
        return true;
    }
}