package com.example.finfolio.UsrController;

import Models.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label checking_account_num;
    public Label savings_bal;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView transactions_listview;
    public TextField payeefld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_btn;
    public WebView chart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Model.getInstance().getUser().getStatut().equals("ban"))
        {
            user_name.setStyle("-fx-fill: red;");
            user_name.setText("Salut , "+ Model.getInstance().getUser().getPrenom()+" Il faut payer vos credits");

        }
            else
        user_name.setText("Salut , "+ Model.getInstance().getUser().getPrenom());

         checking_bal.setText(Model.getInstance().getUser().getSolde()+"DT");
        login_date.setText("Aujourd'hui ,"+ LocalDate.now());
        WebEngine webEngine = chart.getEngine();
        String url1 = getClass().getResource("/chart.html").toExternalForm();
        webEngine.load(url1 + "?symbol=" + "BTC" + "&interval=" + "1m");

    }


    @FXML
    void onSend(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/finfolio/User/ChatBot.fxml")));

            // Create a new scene
            Scene scene = new Scene(root);

            // Create a new stage
            Stage newStage = new Stage();
            newStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/finfolio/Pics/icon.png"))));

            newStage.setScene(scene);
            // Show the new stage
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
