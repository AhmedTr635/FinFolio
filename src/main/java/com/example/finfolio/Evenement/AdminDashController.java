package com.example.finfolio.Evenement;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


import com.example.finfolio.Entite.Don;
import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Service.DonService;
import com.example.finfolio.Service.EvennementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AdminDashController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane big_container;

    @FXML
    private TableColumn<Evennement, Void> event_action;

    @FXML
    private TableColumn<Evennement, String> event_address;

    @FXML
    private TableColumn<Evennement, LocalDate> event_date;

    @FXML
    private TableColumn<Evennement, Float> event_montant;

    @FXML
    private TableColumn<Evennement, String> event_name;

    @FXML
    private TableView<Evennement> events_table;

    @FXML
    private BarChart<String, Float> bar_chart_event;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    @FXML
    private PieChart pi_chart_event;
    @FXML
    private HBox top_container;

    @FXML
    private Button ajouter_btn;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_field;




    @FXML
    void initialize() {

        event_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        event_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        event_address.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        event_montant.setCellValueFactory(new PropertyValueFactory<>("montant"));

        // Add cell factory for Action column
        event_action.setCellFactory(column -> {
            return new TableCell<>() {
                final Button deleteButton = new Button("Supprimer");
                final Button modifyButton = new Button("Modifier");

                {
                    deleteButton.setOnAction(event -> {
                        Evennement e = getTableView().getItems().get(getIndex());
                        handleDeleteEvent(e);
                    });

                    modifyButton.setOnAction(event -> {
                        Evennement e = getTableView().getItems().get(getIndex());
                        System.out.println(e);
                        handleModifyEvent(e);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox buttonBox = new HBox(deleteButton, modifyButton);
                        setGraphic(buttonBox);
                    }
                }
            };
        });

        loadEventsFromDatabase();


    }



    private void loadEventsFromDatabase() {
        // Fetch events from EvennementService
        List<Evennement> events = EvennementService.getInstance().readAll();

        // Display events in TableView
        ObservableList<Evennement> eventList = FXCollections.observableArrayList(events);
        events_table.setItems(eventList);
    }


    @FXML
    void ajouter_event(ActionEvent event) {
        try {
            // Load the FXML file for AjouterDon interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Evennement/Ajouter_event.fxml"));
            Parent root = loader.load();


            // Create a new stage for the AjouterDon interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter Evennement");
            stage.setOnHidden(e -> refreshTableView());

            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void refreshTableView() {
        loadEventsFromDatabase();
    }



    @FXML
    void handleDeleteEvent(Evennement event) {
        int eventId = event.getId(); // Assuming getId() returns the event's ID
        EvennementService.getInstance().delete(eventId);

        refreshTableView();
    }


    private void handleModifyEvent(Evennement event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Evennement/ModifierEvent.fxml"));
            Parent root = loader.load();

            ModifierEventController controller = loader.getController();
            controller.initData(event);

            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifier Evennement");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHidden(e -> refreshTableView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





    @FXML
    void search_event(ActionEvent event) throws SQLException {
        String searchTerm = search_field.getText();

        // Fetch events from EvennementService based on the search term
        List<Evennement> events = EvennementService.getInstance().rechercherEvent(searchTerm);

        // Display filtered events in TableView
        ObservableList<Evennement> eventList = FXCollections.observableArrayList(events);
        events_table.setItems(eventList);
    }





}
