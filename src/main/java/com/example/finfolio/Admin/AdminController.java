package com.example.finfolio.Admin;

import Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model.getInstance().getViewFactory().getAdminSelectMenuItem().addListener((observableValue,oldVal,newVal)->
        {
            switch (newVal){
                case "CreateUser"->admin_parent.setCenter(Model.getInstance().getViewFactory().getCreditsAdminView());
                case "Users"->admin_parent.setCenter(Model.getInstance().getViewFactory().getUsersAdminView());
                case "Investissements"->admin_parent.setCenter(Model.getInstance().getViewFactory().getInvestissementsAdminView());
                case "Evenements"->admin_parent.setCenter(Model.getInstance().getViewFactory().getEvenementsAdminView());
                case "Taxes"->admin_parent.setCenter(Model.getInstance().getViewFactory().getTaxesAdminView());
                case "Credits"->admin_parent.setCenter(Model.getInstance().getViewFactory().getCreditsAdminView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateUserView());
            }

        });
    }
}
