package com.example.finfolio.Investissement.Controller;

import com.example.finfolio.Investissement.Entite.Investissement;
import com.example.finfolio.Investissement.Entite.RealEstate;
import com.example.finfolio.Investissement.Entite.User;
import com.example.finfolio.Investissement.Service.InvestissementService;
import com.example.finfolio.Investissement.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddREController/* implements Initializable*/ {

    public Pane AddRe;
    public TextField montantInv;
    public Button ajoutInvRE;
    public Button updateInv;
    public Button DeleteInv;
    public Label nom;
    public Label nbChabres;
    public Label Roi;
    public Label valeur;
    public Label superficie;
    public RealEstate realEstate;
    public ImageView systeme;
    public Button pdf;
    public ImageView variable;


    @FXML
    private Button annuler;

    @FXML
    private Button confirmer;

    @FXML
    private Button droite;

    @FXML
    private Button gauche;

    @FXML
    private ImageView imageRE;

    @FXML
    void annulerPopUp(MouseEvent event) {

    }

    @FXML
    void confirmerPopUp(MouseEvent event) {

    }

    @FXML
    void droiteImage(MouseEvent event) {

    }

    @FXML
    void gaucheImage(MouseEvent event) {

    }
    Investissement inv=new Investissement();
    InvestissementService invS=new InvestissementService();
    User mhmd=new User();


//Test CRUD DigitalCoins


    String dateString="2023-03-01";
    //RealEstate mansion=new RealEstate(16,"miami", 9.92F,1950000,10,2000);
    public void addInv(ActionEvent actionEvent) throws SQLException {
        String dateString="2023-03-01";
        //String dateString1="15/02/2024";
        String dateString2 = "2024-02-26";

        // Define a formatter to parse the string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the string to a LocalDate object
        LocalDate date = LocalDate.parse(dateString, formatter);
        UserService usS=new UserService();
        //LocalDate date = LocalDate.parse(dateString);
        LocalDate date1 = LocalDate.parse(dateString2, formatter);
        //Investissement inv=new Investissement(178, montantInv.getText().isEmpty()?0:Double.parseDouble(montantInv.getText()),150000,date,2000,mansion,usS.readById(3),100);


        //Double montant= montantInv.getText().isEmpty()?0:Double.parseDouble(montantInv.getText());
        // aatini l controlleur eli fih lmontant
        Investissement invest=new Investissement();
        invest.setDateAchat(LocalDate.now());
        invest.setROI(realEstate.getROI());
        invest.setMontant(Double.parseDouble(montantInv.getText()));
        invest.setRe(realEstate);
        invest.setPrixAchat(realEstate.getValeur());
        invest.setTax(Double.parseDouble(montantInv.getText())*realEstate.getROI()*0.08);
        UserService userService=new UserService();
        //invest.setUser(userService.readById(3));


        invS.add(invest);
        System.out.println("Investissement ajouté");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Investissement ajouté");
        alert.setHeaderText("Investissement ajouté");
        alert.setContentText("Investissement ajouté");
        alert.showAndWait();
        Stage stage=(Stage) ajoutInvRE.getScene().getWindow();
        stage.close();
        /*try {
            Parent root1 = FXMLLoader.load(getClass().getResource("/com/example/demo1/InvestissementUser.fxml"));
            Scene scene1 = new Scene(root1);

            // Get the stage from the event source
            Stage stage1 = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene to the stage
            stage1.setScene(scene1);

            // Show the stage
            stage1.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/

    }

    public void UpdateInv(ActionEvent actionEvent) {
    }

    public void deleteInv(ActionEvent actionEvent) {
    }

   /* @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }*/
    public void setData(RealEstate re) {
        nom.setText(re.getEmplacement());
        nbChabres.setText(String.valueOf(re.getNbChambres()));
        Roi.setText(String.valueOf(re.getROI()));
        valeur.setText(String.valueOf(re.getValeur()));
        superficie.setText(String.valueOf(re.getSuperficie()));
    }
    public void setRe(RealEstate re){
        this.realEstate=re;
    }

    public void pdf(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo1/InvestissementUser.fxml"));
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
