package com.example.finfolio.Investissement.Controller;

import com.example.finfolio.Investissement.Entite.DigitalCoins;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DigitalCoinCellController {

    @FXML
    private Label ROI;

    @FXML
    private Label code;

    @FXML
    private Label dateAchat;

    @FXML
    private Label montant;

    @FXML
    private Label pixAchat;

    @FXML
    private Button sell;
    public DigitalCoins digitalCoins;
    public void setData(DigitalCoins dc) {
        this.digitalCoins = dc;
        code.setText(dc.getCode());
        dateAchat.setText(dc.getDateAchat().toString());
        montant.setText(String.valueOf(dc.getMontant()));
        pixAchat.setText(String.valueOf(dc.getPrixAchat()));
        ROI.setText(String.valueOf(dc.getROI()));


    }

    @FXML
    void sell(ActionEvent event) {
        if (digitalCoins.getDateVente() == null)
        {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/DigitalCoinSell.fxml"));
                //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/AddRE.fxml"));
                Parent root = fxmlLoader.load();/*FXMLLoader.load(getClass().getResource("/com/example/demo1/AddRE.fxml"));*/
                Scene scene = new Scene(root);

                // Get the stage from the event source
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                DigitalCoinSellController digitalCoinSellController = fxmlLoader.getController();
                digitalCoinSellController.setData(digitalCoins);



            /*AddREController addREController = new AddREController();
            addREController.nom.setText(realEstate.getEmplacement());
            addREController.superficie.setText(String.valueOf(realEstate.getSuperficie()));
            addREController.nbChabres.setText(String.valueOf(realEstate.getNbChambres()));
            addREController.valeur.setText(String.valueOf(realEstate.getValeur()));
            addREController.Roi.setText(String.valueOf(realEstate.getROI()));*/


                // Set the scene to the stage
                stage.setScene(scene);

                // Show the stage
                stage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            sell.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Erreur de vente");
            alert.setContentText("Vous avez déjà vendu cette monnaie");
            alert.showAndWait();

        }

    }

    @FXML
    void sell1(MouseEvent event) {


    }

}
