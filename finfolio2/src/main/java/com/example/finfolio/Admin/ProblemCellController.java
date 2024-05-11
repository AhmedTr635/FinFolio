package com.example.finfolio.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import com.example.finfolio.Entite.Error;

public class ProblemCellController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private AnchorPane pane_id;

    @FXML
    private Label problem_id;
    @FXML
    private Button confirm_id;


    @FXML
    void initialize() {



    }
    @FXML
    void sendEmail(ActionEvent event) {

    }
    void setData(Error err){
        problem_id.setText(err.getError());
    }

}

