package com.example.finfolio.Portfolio.Controller.ActifsCourants;

import Models.Model;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Portfolio.Entite.ActifsCourants;
import com.example.finfolio.Service.ActifsCservices;
import com.example.finfolio.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Ajouter {

    @FXML
    private Button idbtn;

    @FXML
    private TextField idmontant;

    @FXML
    private TextField idname;

    @FXML
    private TextField idtype;

    @FXML
    private TextField iduser;

    @FXML
    void addactif(ActionEvent event) {

        if (idmontant.getText().isEmpty() || idname.getText().isEmpty() || idtype.getText().isEmpty()  ) {

            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez remplir tous les champs.");
            return;}

        if (!idmontant.getText().matches("\\d+(\\.\\d+)?")  ) {

            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez entrer des valeurs numériques valides pour le montant et l'utilisateur.");
            return;
        }

        if (!idname.getText().matches("[a-zA-Z]+") || !idtype.getText().matches("[a-zA-Z]+")) {

            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Les champs 'nom' et 'type' doivent contenir uniquement des lettres.");
            return;
        }
        int montant = Integer.parseInt(idmontant.getText());
        String name=idname.getText();
        String type=idtype.getText();
        //User user = fetchUserById(rsgetInt("user_id"));

        ActifsCourants a=new ActifsCourants(name,montant,type, Model.getInstance().getUser());
        ActifsCservices as=new ActifsCservices();
        as.add(a);
       /* Parent root=loader.load();
        Update Controller=loader.getController();*/

        Stage stage = (Stage) idbtn.getScene().getWindow();
        stage.close();

//            //FXMLLoader loader=new FXMLLoader(getClass().getResource("/afficher.fxml"));
//           // Parent root=loader.load();


    }

    public User fetchUserById(int userId) throws SQLException {

        UserService us= new UserService();
        return us.readAll().stream().filter(u->u.getId()==userId).findFirst().get();
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    }


