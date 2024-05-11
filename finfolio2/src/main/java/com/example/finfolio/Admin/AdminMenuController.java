package com.example.finfolio.Admin;

import Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController  implements Initializable   {

    public Button creer_user_btn;
    public Button users_btn;
    public Button investissements_btn;
    public Button evenements_btn;
    public Button taxes_btn;
    public Button credits_btn;
    public Button logout_btn;
    public Button data_btn;



    @Override
    public void initialize(URL location, ResourceBundle resources) {addListeners();}
    private void addListeners(){
        creer_user_btn.setOnAction(e->onCreateUser());
        users_btn.setOnAction(e->onUsers());
        investissements_btn.setOnAction(e->onInvestissements());
        evenements_btn.setOnAction(e->onEvenements());
        taxes_btn.setOnAction(e->onTaxes());
        credits_btn.setOnAction(e->onCredits());
        logout_btn.setOnAction(e-> {
            try {
                onLogout();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        data_btn.setOnAction(e->onData());


    }
    private void onCreateUser(){
        Model.getInstance().getViewFactory().getAdminSelectMenuItem().set("CreateUser");
    }
    private void onCredits(){
        Model.getInstance().getViewFactory().getAdminSelectMenuItem().set("Credits");
    }
    private void onTaxes(){
        Model.getInstance().getViewFactory().getAdminSelectMenuItem().set("Taxes");
    }
    private void onInvestissements(){Model.getInstance().getViewFactory().getAdminSelectMenuItem().set("Investissements");}
    private void onEvenements(){Model.getInstance().getViewFactory().getAdminSelectMenuItem().set("Evenements");}
    private void onUsers(){Model.getInstance().getViewFactory().getAdminSelectMenuItem().set("Users");}
    private void onData(){Model.getInstance().getViewFactory().getAdminSelectMenuItem().set("Data");}

    private void onLogout() throws IOException {
        Stage st = (Stage) logout_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(st);
        Model.getInstance().getViewFactory().showLoginWindow();

    }
}
