package com.example.finfolio.UsrController;

import Models.Model;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
    public Button offreBtn;
    public Button immobilier;
    public Button trading;


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
        offreBtn.setOnAction(e->onOffre());
        signaler_btn.setOnAction(e->openError());
        trading.setOnAction(e->onTrading());
        immobilier.setOnAction(e->onImmobilier());

    }
    private void onDashboard(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Dashboard");
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
    private void onOffre(){Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Offre");}
    private void onTrading(){Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Trading");}
    private void onImmobilier(){Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Immobilier");}


    private void onLogout() throws IOException {
        Stage st = (Stage) logout_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(st);
        Model.getInstance().getViewFactory().showLoginWindow();

    }
    void openError() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/addError.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Votre réclamation");
            stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/finfolio/Pics/icon.png"))));
            stage.setScene(new Scene(root));
            stage.setTitle("Probleme");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

}}