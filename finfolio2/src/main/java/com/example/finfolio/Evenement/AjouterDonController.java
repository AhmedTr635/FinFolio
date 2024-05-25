package com.example.finfolio.Evenement;

import Models.Model;
import com.example.finfolio.Depense.DashboardDepenseController;
import com.example.finfolio.Entite.Don;
import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Entite.Tax;
import com.example.finfolio.Service.DonService;
import com.example.finfolio.Service.EvennementService;
import com.example.finfolio.Service.TaxService;
import com.example.finfolio.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;


public class AjouterDonController {

    @FXML
    private Button btnAdd;


    @FXML
    public TextField montant_field;

    @FXML
    private Label event_id;

    @FXML
    private Label user_id;




    @FXML
    void annuler(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Close the stage
        stage.close();
    }
    public void setEventId(String id) {
        event_id.setText(id);
    }


    @FXML
    void faireundon(ActionEvent event) throws SQLException {

        // Get the donation amount from the TextField
        String montantText = montant_field.getText();
        String eventIdText = event_id.getText();
        String userIdText = user_id.getText();



        // Check if the montantText is not empty
        if (!montantText.isEmpty()) {
            // Parse the montantText to a double (assuming montant is a decimal value)
            float montant = Float.parseFloat(montantText);
            int eventId = Integer.parseInt(String.valueOf(eventIdText));
            int userId = Integer.parseInt(String.valueOf(1));
            EvennementService es =new EvennementService();
            UserService us = new UserService();

            // Create a new Don object

            Don donation = new Don(montant, Model.getInstance().getUser(), es.readById(eventId));


            // Save the donation using the DonService
            DonService.getInstance().add(donation);
            TaxService ts=new TaxService();
            double new_total_tax= Tax.calculateTotalTax( ts.readAll())-montant;
            Model.getInstance().getUser().setTotal_tax(ts.sommeTaxByDepense()-montant);
            us.updatewTax(Model.getInstance().getUser());

            /*if (new_total_tax<0) {


                DashboardDepenseController.getInstance().tax_depense.setText(String.format("%.2f", 0.0));
                // Create a new Don object
            }
            else{
                DashboardDepenseController.getInstance().tax_depense.setText(String.format("%.2f", new_total_tax));
            }*/

            Stage currentStage = (Stage) btnAdd.getScene().getWindow();
            currentStage.close();

            // Alert for don
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Don fait avec succès");
            alert.showAndWait();

            Evennement evnt = es.readById(eventId);
            String eventInfo = "L'evennement: " + evnt.getNom() +"Date: " + evnt.getDate() + ", Adresse: " + evnt.getAdresse();
            EmailController.sendInvitationEmail(Model.getInstance().getUser().getEmail(), "Invitation à l'événement", eventInfo);



        }
    }





}
