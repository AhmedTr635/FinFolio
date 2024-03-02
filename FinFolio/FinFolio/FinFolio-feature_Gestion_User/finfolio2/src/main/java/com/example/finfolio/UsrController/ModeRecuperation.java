package com.example.finfolio.UsrController;

import Models.Model;
import com.example.finfolio.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModeRecuperation implements Initializable {

    public ChoiceBox<String> modechx;
    public Button continuerBtn;
    public Button retournerBtn;
    private String[] modes = {"SMS", "Email"};

    public void getMode(ActionEvent event) {

        String modechxValue = modechx.getValue();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modechx.getItems().addAll(modes);
        modechx.setOnAction(this::getMode);
        continuerBtn.setOnAction(e->onConfirmer());
        retournerBtn.setOnAction(e-> {
            try {
                onRetourner();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
    public void onConfirmer()  {
        if ("SMS".equals(modechx.getValue())) {
            Stage st = (Stage) modechx.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(st);
            Model.getInstance().getViewFactory().showSMSWindow();
        }
            else
        {
            Stage st = (Stage) modechx.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(st);
        Model.getInstance().getViewFactory().showMotDepasseOublieWindow();}
    }
    public void onRetourner() throws IOException {
        Stage st = (Stage) modechx.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(st);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
