package com.example.finfolio.UsrController;

import Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserBanController implements Initializable {
    public BorderPane userBan_parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Model.getInstance().getViewFactory().getUserSelectedMenuItem().addListener((observableValue, oldVal, newVal)->
        {
            switch (newVal){
                case "Credits"->userBan_parent.setCenter(Model.getInstance().getViewFactory().getCreditsView());
                case "Portfolio"->userBan_parent.setCenter(Model.getInstance().getViewFactory().getPortfolioView());
                case "Dashboard"->userBan_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                case "Depenses"->userBan_parent.setCenter(Model.getInstance().getViewFactory().getDepensesView());
            }

        });

    }
}
