package com.example.finfolio.Evenement;



import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.ResourceBundle;


import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Service.EvennementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


public class ClientEventsController {

    public AnchorPane bg_container;
    @FXML
    public AnchorPane imnida_pane;
    @FXML
    private ResourceBundle resources;

    @FXML
    private ScrollPane scroll;

    @FXML
    private URL location;

    @FXML
    private AnchorPane admindash;

    @FXML
    private VBox chosen_event;

    @FXML
    private Label chosen_event_address;

    @FXML
    private Label chosen_event_date;

    @FXML
    private Label chosen_event_montant;

    @FXML
    private Label chosen_event_name;

    @FXML
    private ImageView chosen_event_pic;

    @FXML
    private Label event_address_up;

    @FXML
    private Label event_date_up;

    @FXML
    private Label event_montant_up;

    @FXML
    private Label event_name_up;

    @FXML
    private TextField searchfield;


    @FXML
    private Button btnsearch;

    /*   @FXML
       private AnchorPane event_container;

       @FXML
       private Label event_address;

       @FXML
       private Label event_name;
       @FXML
       private Label event_montant;
       @FXML

       private Label event_date;*/


    @FXML
    private VBox event_container;





    @FXML
    private Pane upcoming_event_container;

    @FXML
    void initialize() {

        upcomingEvent();
        loadEvents();


    }

    private  void upcomingEvent(){
        Evennement upcomingEvents = EvennementService.getInstance().showUpComingEvent();
        /*     upcoming_event_container.getChildren().clear();*/
        if (upcomingEvents != null){
            // Create UI components programmatically
            event_date_up.setText(upcomingEvents.getDate().toString());
            event_name_up.setText(upcomingEvents.getNom());
            event_montant_up.setText(String.valueOf(upcomingEvents.getMontant()));
            event_address_up.setText(upcomingEvents.getAdresse());

            // Add UI components to the container
            upcoming_event_container.getChildren().addAll();}
    }







    private void loadEvents() {



        // Suppose getEventsFromDatabase is a method to fetch event data from database.
        List<Evennement> events = EvennementService.getInstance().readAll();

        // Clear existing content.


        for (Evennement event : events) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Evennement/eventCell.fxml"));
                AnchorPane eventCell = fxmlLoader.load();

                // Set event data to the cell's controllers here.
                // You might need to get the controller from the loader and then
                // set the properties individually, like event name, date, place, etc.
                EventCellController controller = fxmlLoader.getController();
                controller.setEventData(event);
                //   controller.setEvnt(event);

                // ... Set other event details ...

                // Add the cell to the container.
                event_container.getChildren().addAll(eventCell);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }}



    @FXML
    void searchEvent(ActionEvent event) throws SQLException {
        String searchTerm = searchfield.getText();

        // Fetch events from EvennementService based on the search term
        List<Evennement> events = EvennementService.getInstance().rechercherEvent(searchTerm);

        // Clear existing content in the event container
        event_container.getChildren().clear();

        // Load matching events into the event container
        for (Evennement evnt : events) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Evennement/eventCell.fxml"));
                AnchorPane eventCell = fxmlLoader.load();

                // Set event data to the cell's controllers here.
                // You might need to get the controller from the loader and then
                // set the properties individually, like event name, date, place, etc.
                EventCellController controller = fxmlLoader.getController();
                controller.setEventData(evnt);

                // Add the cell to the container.
                event_container.getChildren().add(eventCell);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }
    }




}




