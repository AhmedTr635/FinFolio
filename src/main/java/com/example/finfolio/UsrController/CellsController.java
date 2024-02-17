package com.example.finfolio.UsrController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CellsController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label mail_lbl;

    @FXML
    private Label nbcredits_lbl;

    @FXML
    private Label nom_lbl;

    @FXML
    private Label note_lbl;

    @FXML
    private Label prenom_lbl;

    @FXML
    private Label role_lbl;

    @FXML
    private Button supprimer_btn;

    @FXML
    private Label tel_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
