package com.example.finfolio.Evenement;

import Models.Model;
import com.example.finfolio.Entite.Don;
import com.example.finfolio.Service.DonService;
import com.example.finfolio.Service.EvennementService;
import com.example.finfolio.Service.TaxService;
import com.example.finfolio.Service.UserService;
import com.example.finfolio.UsrController.DashboardController;
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
            TaxService taxC = new TaxService();
            double sommetaxDep = taxC.sommeTaxByDepense();
            DashboardController dash =new DashboardController();

            Model.getInstance().getUser().setTotal_tax(sommetaxDep-montant);
            us.updatewTax(Model.getInstance().getUser());
            System.out.println("new montant "+Model.getInstance().getUser().setTotal_tax(sommetaxDep-montant));
/*
            dash.refreshDashboard();
*/
            Stage currentStage = (Stage) btnAdd.getScene().getWindow();
            currentStage.close();

            // Alert for don
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Don fait avec succès");
            alert.showAndWait();/*

EmailController ec = new EmailController();
            ec.sendEmail("siwarbouali27@gmail.com", "Invitation à l'événement", "Bonjour, vous êtes invité à participer à notre événement. Cordialement, Finfolio");

*//*
*/
            // Optionally, display a success message or close the window

        }
    }
    /*public void makeDonation(double donation) {
        String montantText = montant_field.getText();

        double montant = Float.parseFloat(montantText);

        TaxService taxC = new TaxService();
        double sommetaxDep = taxC.sommeTaxByDepense();
        Model.getInstance().setTax(sommetaxDep-donation);
        System.out.println("hiiii"+Model.getInstance().getTax());
        DashboardController dash =new DashboardController();
        //System.out.println(dash.getExpense_lbl());
        dash.setExpense_lbl(Double.toString(Model.getInstance().getTax()));
        System.out.println(("ghghg"+Model.getInstance().getTax()));
        dash.refreshDashboard(montant);
    }*/

   /* public void makeDonation(double donation) {
        // ... votre code pour faire un don

        // Mettre à jour la taxe dans le modèle
        TaxService taxC = new TaxService();
        double sommetaxDep = taxC.sommeTaxByDepense();
        Model.getInstance().setTax(sommetaxDep - donation);
        DashboardController dash =new DashboardController();

        // Appeler refreshDashboard pour mettre à jour l'affichage
        dash.refreshDashboard(mont);
    }*/
    // Méthode appelée lorsque le don est effectué
    public void handleDonation() {
        System.out.println("hyy");
        DashboardController dashboardController=new DashboardController();

        String montantText = montant_field.getText();
        System.out.println(montantText);
        double montant = Double.parseDouble(montantText);
        System.out.println(montant);

        // Par exemple, le montant du don est de 200 $
    }





}
