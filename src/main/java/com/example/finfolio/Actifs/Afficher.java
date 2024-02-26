package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;

import entite.ActifsCourants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.effect.BoxBlur;
import javafx.stage.Stage;
import services.ActifsCservices;

public class Afficher<tableview> {
    @FXML
    private TextField research;

    @FXML
    private Button searchid;
    @FXML
    private Button updateid;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<ActifsCourants, Integer> ida;

    @FXML
    private TableColumn<ActifsCourants, Integer> montantid;

    @FXML
    private TableColumn<ActifsCourants, String> nameid;

    @FXML
    private TableView<ActifsCourants> tab1;

    @FXML
    private TableColumn<ActifsCourants, String> typeid;

    @FXML
    private TableColumn<ActifsCourants, Integer> userid;
    @FXML
    private Button addid;
    @FXML
    private Button deleteid;
    @FXML
    private Button bilan_id;

    //private BoxBlur blur;
    ObservableList<ActifsCourants> tableview= FXCollections.observableArrayList();
    @FXML
    void initialize() {
        ActifsCservices pc=new ActifsCservices();
        List<ActifsCourants> liste=pc.readAll();
        ObservableList<ActifsCourants> actifsC= FXCollections.observableArrayList(liste);
        ida.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameid.setCellValueFactory(new PropertyValueFactory<>("name"));
        montantid.setCellValueFactory(new PropertyValueFactory<>("montant"));
        typeid.setCellValueFactory(new PropertyValueFactory<>("type"));
        userid.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        tab1.setItems(actifsC);


    }



   /* public void setBlur(BoxBlur blur) {
        this.blur = blur;

        blur.setWidth(5);
        blur.setHeight(5);
        blur.setIterations(3);

    }
*/
    @FXML
    void deleteactif(ActionEvent event) {
        int selectedIndex = tab1.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {

            ActifsCourants a=tab1.getItems().get(selectedIndex);
            //int id = (int) tab1.getItems().get(selectedIndex).getUser_id();
            ActifsCservices ac=new ActifsCservices();
            ac.delete(a);
            tab1.getItems().remove(selectedIndex);


    }
}



    @FXML
    void adder(ActionEvent event)  {



            try {

                Parent root= FXMLLoader.load(getClass().getResource("/ajouter.fxml"));
                Scene scene=new Scene(root);
                Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show(); }
            catch (IOException e){throw new RuntimeException(e);}

    }

    @FXML
    void updateactif(ActionEvent event) {
        ActifsCourants selectedItem = tab1.getSelectionModel().getSelectedItem();

        try {


             FXMLLoader loader=new FXMLLoader(getClass().getResource("/update.fxml"));
            Parent root=loader.load();
            Update Controller=loader.getController();
            Controller.initData(selectedItem);
            Controller.setA(selectedItem);
            Scene scene=new Scene(root);
            Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show(); }
        catch (IOException e){throw new RuntimeException(e);}


    }
    @FXML
    void generer_bilan(ActionEvent event) {

    }







}
















