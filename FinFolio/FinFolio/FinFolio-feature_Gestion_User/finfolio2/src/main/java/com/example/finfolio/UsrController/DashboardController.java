package com.example.finfolio.UsrController;

import Models.Model;
import com.example.finfolio.Depense.DashboardDepenseController;
import com.example.finfolio.Depense.depenseController;
import com.example.finfolio.Entite.Depense;
import com.example.finfolio.Service.DepenseService;
import com.example.finfolio.Service.TaxService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private static DashboardController instance;

    public static DashboardController getInstance() {
        return instance;
    }

    public DashboardController() {
        instance = this;
    }

    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label checking_account_num;
    public Label savings_bal;
    public Label income_lbl = new Label();
    @FXML
    public Label expense_lbl=new Label();
    public ListView transactions_listview;

    public TextField payeefld;
    public TextField amount_fld;
    public TextArea message_fld;


    public Label getExpense_lbl() {
        return expense_lbl;
    }

    public void setExpense_lbl(String expense) {
        expense_lbl.setText(expense);
    }

    public Button send_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("im hereee" + Model.getInstance().getUser());



        if (Model.getInstance().getUser().getStatut().equals("ban")) {
            user_name.setStyle("-fx-fill: red;");
            user_name.setText("Salut , " + Model.getInstance().getUser().getPrenom() + " Il faut payer vos credits");

        } else
            user_name.setText("Salut , " + Model.getInstance().getUser().getPrenom());

        checking_bal.setText(Model.getInstance().getUser().getSolde() + "DT");
        login_date.setText("Aujourd'hui ," + LocalDate.now());
       /* send_btn.setOnAction(e -> {
            try {
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });*/

    }




    @FXML
    void onSend(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/finfolio/User/ChatBot.fxml")));

            // Create a new scene
            Scene scene = new Scene(root);

            // Create a new stage
            Stage newStage = new Stage();
            newStage.setScene(scene);
            // Show the new stage
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}