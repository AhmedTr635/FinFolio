package com.example.finfolio.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {
    public TextField nom_fld;
    public TextField prenom_fld;
    public TextField mdp_fld;
    public TextField email_fld;
    public TextField tel_fld;
    public ChoiceBox role_box;
    public Button creer_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
