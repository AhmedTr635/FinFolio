package com.example.finfolio.Evenement;



import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Service.EvennementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
        }
    }


/*    @FXML
    void search_event(ActionEvent event) {
        String searchTerm = search_field.getText();

        // Fetch events from EvennementService based on the search term
        List<Evennement> events = EvennementService.getInstance().searchByName(searchTerm);

        // Display filtered events in TableView
        ObservableList<Evennement> eventList = FXCollections.observableArrayList(events);
        events_table.setItems(eventList);
    }*/



    // Other setter methods for date, place, etc.

    // EvennementService evs = new EvennementService();

    //   List<Evennement> events = evs.readAll();


     /*  for (Evennement event : events) {
                // Pass the values to the DepenseCellController
                event_date.setText(event.getDate().toString());
                event_name.setText(event.getNom());
                event_montant.setText(String.valueOf(event.getMontant()));
                event_address.setText((event.getAdresse()));


                // Ajoute la cellule au conteneur



    }
       event_container.getChildren().addAll(event_date,event_name,event_montant,event_address);
*/

}
