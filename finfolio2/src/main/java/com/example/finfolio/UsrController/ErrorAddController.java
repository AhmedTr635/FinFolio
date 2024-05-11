package com.example.finfolio.UsrController;

import Models.Model;
import com.example.finfolio.Entite.Error;
import com.example.finfolio.Service.ErrorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorAddController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add_error;

    @FXML
    private TextArea error_text;
    @FXML
    private Button closeErr;


    @FXML
    void addError(ActionEvent event) {



        String err=error_text.getText();

        Error er=new Error(Model.getInstance().getUser(), err);

        ErrorService as=new ErrorService();
        as.add(er);
        System.out.println(err);

        }



    @FXML
    void shutOff(ActionEvent event) {

        Stage stage = (Stage) closeErr.getScene().getWindow();
        stage.close();

    }

}
