package com.example.finfolio.Evenement;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


import com.example.finfolio.Entite.Don;
import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Service.DonService;
import com.example.finfolio.Service.EvennementService;
import com.example.finfolio.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class AjouterDonController {


    @FXML
    private ResourceBundle resources;
    private int idEvnt;


    public Evennement getEvnt() {
        return evnt;
    }


    public int getIdEvnt() {
        return idEvnt;
    }





    private Evennement evnt;

    public void setEvent(Evennement evnt) {
        this.evnt = evnt;
    }

    @FXML
    private URL location;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btn_annuler;

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

            Don donation = new Don(montant,us.readById(userId), es.readById(eventId));
            System.out.println(donation);

            donation.setMontant_user(montant);


            // Save the donation using the DonService
            DonService.getInstance().add(donation);

            Stage currentStage = (Stage) btnAdd.getScene().getWindow();
            currentStage.close();

            // Create a new stage for displaying the confirmation message
            Stage confirmationStage = new Stage();
            VBox vbox = new VBox();
            Label confirmationLabel = new Label("Don fait avec succès");
            confirmationLabel.setTextFill(Color.GREEN);
            vbox.getChildren().add(confirmationLabel);
            vbox.setAlignment(Pos.CENTER);
            Scene confirmationScene = new Scene(vbox, 200, 100);
            confirmationStage.setScene(confirmationScene);
            confirmationStage.show();

EmailController ec = new EmailController();
            ec.sendEmail("melekbouali37@gmail.com", "Invitation à l'événement", "Bonjour, vous êtes invité à participer à notre événement. Cordialement, Finfolio");


            // Optionally, display a success message or close the window

        }
    }




    @FXML
    void initialize() {

    }

}
