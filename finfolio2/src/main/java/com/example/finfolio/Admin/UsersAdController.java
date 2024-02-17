package com.example.finfolio.Admin;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class UsersAdController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button chercher_btn;

    @FXML
    private TextField cherecher_fld;

    @FXML
    private Button confiermer_btn;

    @FXML
    private TextField nbrCredits_fld;

    @FXML
    private TextField nom_fld;

    @FXML
    private TextField note_fld;

    @FXML
    private TextField numtel_fld;

    @FXML
    private TextField prenom_fld;

    @FXML
    private ChoiceBox<?> role_chbx;

    @FXML
    private ListView<User> users_listview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserService us=new UserService();
        ObservableList<User>users = FXCollections.observableArrayList();
        try {
            users.addAll(us.readAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


            users_listview.setItems(users);

    }
}
