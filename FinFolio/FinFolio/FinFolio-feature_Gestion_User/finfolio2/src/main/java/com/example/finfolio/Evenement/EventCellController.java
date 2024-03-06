package com.example.finfolio.Evenement;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Service.DonService;
import com.example.finfolio.Service.EvennementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EventCellController {

    @FXML
    public AnchorPane events_pane;


    @FXML
    private Button btnpart;

    @FXML
    public Label event_date;

    @FXML
    private Label event_goal;

    @FXML
    private Label event_name;

    @FXML
    private Label event_id;

    @FXML
    private Label event_place;

    @FXML
    private Button preced_btn;

    @FXML
    private AnchorPane description_pane;

    @FXML
    private ProgressBar progress_bar;

    @FXML
    private Rating event_rating;

    private int eventId;

    @FXML
    private TextArea description_event;

    public ProgressBar getProgress_bar() {
        return progress_bar;
    }

    public void setProgress_bar(ProgressBar progress_bar) {
        this.progress_bar = progress_bar;
    }

    @FXML
    void initialize() {
        btnpart.setOnAction(e -> participer_event());
        // Hide the description pane initially

        description_pane.setVisible(false);

        // Handle click on the event cell
        events_pane.setOnMouseClicked(event -> {
            // Show the description pane and hide the event pane
            description_pane.setVisible(!description_pane.isVisible());
        });

        // Handle click on the precedent button
        preced_btn.setOnAction(event -> {
            // Show the event pane and hide the description pane
            description_pane.setVisible(false);
            events_pane.setVisible(true);
        });



        event_rating.ratingProperty().addListener((observable, oldValue, newValue) -> {
            // Save the new rating to the database when the user selects the number of stars
            RatingApi.saveRating(eventId, newValue.intValue());
        });
    }

    @FXML
    void participer_event() {
        try {
            // Load the FXML file for AjouterDon interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Evennement/faire-un-don.fxml"));
            Parent root = loader.load();
            AjouterDonController adc = loader.getController();
            String id = event_id.getText();
            adc.setEventId(id);



            // Create a new stage for the AjouterDon interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Faire un don");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setEventData(Evennement event) {
        event_name.setText(event.getNom());
        event_date.setText(event.getDate().toString()); // Adjust as necessary for your Date representation
        event_place.setText(event.getAdresse());
        event_goal.setText(String.format("$%,.2f", event.getMontant()));
        event_id.setText(String.valueOf(event.getId()));
        description_event.setText(event.getDescription());
        float totalDonations = DonService.getInstance().getTotalDonationsForEvent(event.getId());
        float progressPercentage = (totalDonations / event.getMontant()) * 100;
        progress_bar.setProgress(progressPercentage / 100);

        eventId = event.getId();

        // Load ratings from the file
        int rating = RatingApi.loadRating(eventId);
        event_rating.setRating(rating);

    }





    }






