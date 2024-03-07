package com.example.finfolio.UsrController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CellsController implements Initializable {

    public Label solde_lbl;



    @FXML
    private Label mail_lbl;

    @FXML
    private Label nbcredits_lbl;

    @FXML
    private Label nom_lbl;

    @FXML
    private Label note_lbl;

    @FXML
    private Label prenom_lbl;

    @FXML
    private Label role_lbl;

    @FXML
    private Button suppbtn;

    @FXML
    private Label tel_lbl;

    private User user;
    public CellsController (User user)
    {
        this.user=user;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nom_lbl.setText(user.getNom());
        prenom_lbl.setText(user.getPrenom());
        mail_lbl.setText(user.getEmail());
        tel_lbl.setText(user.getNumtel());
        role_lbl.setText(user.getRole());
        note_lbl.setText(String.valueOf(user.getRate()));
        nbcredits_lbl.setText(String.valueOf(user.getNbcredit()));
        solde_lbl.setText(user.getSolde());
        suppbtn.setOnAction(e-> {
            try {
                onSupprimer();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });



    }
    private void onSupprimer() throws SQLException {
        UserService userService = new UserService();
        userService.delete(user);

    }
}
