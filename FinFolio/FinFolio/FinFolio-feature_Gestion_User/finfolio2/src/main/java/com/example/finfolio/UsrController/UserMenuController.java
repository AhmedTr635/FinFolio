package com.example.finfolio.UsrController;

import Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    public Button dashboard_btn;
    public Button credits_btn;
    public Button portfolio_btn;
    public Button investissements_btn;
    public Button evenements_btn;
    public Button depenses_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button signaler_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListeners();
    }
    private void addListeners(){
        dashboard_btn.setOnAction(e->onDashboard());
        credits_btn.setOnAction(e->onCredits());
        portfolio_btn.setOnAction(e->onPortfolio());
        investissements_btn.setOnAction(e->onInvestissements());
        evenements_btn.setOnAction(e->onEvenements());
        depenses_btn.setOnAction(e->onDepenses());
        profile_btn.setOnAction(e->onProfil());
        logout_btn.setOnAction(e-> {
            try {
                onLogout();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
    private void onDashboard(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Dashboard");
        DashboardController dash=new DashboardController();
    }
    private void onCredits(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Credits");
    }
    private void onPortfolio(){
    Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Portfolio");
}
    private void onInvestissements(){Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Investissements");}
    private void onEvenements(){Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Evenements");}
    private void onDepenses(){Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Depenses");}
    private void onProfil(){Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Profil");}

    private void onLogout() throws IOException {
        Stage st = (Stage) logout_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(st);
        Model.getInstance().getViewFactory().showLoginWindow();

    }


}