package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entite.ActifsCourants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.ActifsCservices;

public class Update {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button confirmid;

    @FXML
    private Label id_montant;

    @FXML
    private Label id_name;

    @FXML
    private Label id_type;





    @FXML
    private TextField montant_id;

    @FXML
    private TextField name_id;

    @FXML
    private TextField type_id;

    private ActifsCourants a;


    public void setA(ActifsCourants a) {
        this.a = a;
    }

    @FXML
    void updateActif(ActionEvent event) {


        int montant = Integer.parseInt(montant_id.getText());
        String name=name_id.getText();
        String type=type_id.getText();


        ActifsCservices as=new ActifsCservices();
        as.update(this.a,name,montant,type);

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



    public void initData(ActifsCourants selectedItem) {
        id_name.setText(selectedItem.getName());
        id_type.setText(selectedItem.getType());
        id_montant.setText(String.valueOf(selectedItem.getMontant()));
        name_id.setText(selectedItem.getName());
        type_id.setText(selectedItem.getType());
        montant_id.setText(String.valueOf(selectedItem.getMontant()));


    }




}


