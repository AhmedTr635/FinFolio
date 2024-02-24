package com.example.finfolio.UsrController;

import Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    public BorderPane user_parent;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Model.getInstance().getViewFactory().getUserSelectedMenuItem().addListener((observableValue,oldVal,newVal)->
        {
            switch (newVal){
                case "Credits"->user_parent.setCenter(Model.getInstance().getViewFactory().getCreditsView());
                case "Portfolio"->user_parent.setCenter(Model.getInstance().getViewFactory().getPortfolioView());
                case "Investissements"->user_parent.setCenter(Model.getInstance().getViewFactory().getInvestissementsView());
                case "Evenements"->user_parent.setCenter(Model.getInstance().getViewFactory().getEvenementsView());
                case "Depenses"->user_parent.setCenter(Model.getInstance().getViewFactory().getDepensesView());
                case "Profil"->user_parent.setCenter(Model.getInstance().getViewFactory().getProfilView());
                default -> user_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }

        });

    }
}
