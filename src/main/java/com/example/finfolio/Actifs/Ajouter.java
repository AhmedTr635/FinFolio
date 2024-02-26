package Controller;

import entite.ActifsCourants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ActifsCservices;

import java.io.IOException;

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
        int montant = Integer.parseInt(idmontant.getText());
        String name=idname.getText();
        String type=idtype.getText();
        int user= Integer.parseInt(iduser.getText());
        ActifsCourants a=new ActifsCourants(name,montant,type,user);
        ActifsCservices as=new ActifsCservices();
        as.add(a);
        try {

            Parent root= FXMLLoader.load(getClass().getResource("/afficher.fxml"));
            Scene scene=new Scene(root);
            Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

//            //FXMLLoader loader=new FXMLLoader(getClass().getResource("/afficher.fxml"));
//           // Parent root=loader.load();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    }


