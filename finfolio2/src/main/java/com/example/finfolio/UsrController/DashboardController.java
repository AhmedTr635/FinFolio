package com.example.finfolio.UsrController;

import Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
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

    }
}
