package com.example.finfolio.Evenement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


import com.example.finfolio.Entite.Don;
import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.DonService;
import com.example.finfolio.Service.EvennementService;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private TextField search_field;

    @FXML
    public TableView<Don> donationsTable;

    @FXML
    public TableColumn<Don, String> userNameColumn;

    @FXML
    public TableColumn<Don, String> userPrenomColumn;

    @FXML
    public TableColumn<Don, String> userEmailColumn;

    @FXML
    public TableColumn<Don, String> eventNameColumn;

    @FXML
    public TableColumn<Don, LocalDate> eventDateColumn;

    @FXML
    public TableColumn<Don, Float> amountColumn;

    @FXML
    private BarChart<String,Number > chart_don;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;





    @FXML
    void initialize() {

        event_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        event_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        event_address.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        event_montant.setCellValueFactory(new PropertyValueFactory<>("montant"));


        userNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getNom()));
        userPrenomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getPrenom()));
        userEmailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getEmail()));
        eventNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvennement().getNom()));
        eventDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEvennement().getDate()));
        amountColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getMontant_user()).asObject());


        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                search_event(new ActionEvent());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

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
                    deleteButton.getStyleClass().add("custom-button");
                    modifyButton.getStyleClass().add("custom-button");
                    event_action.getStyleClass().add("action-column");
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
        loadDonsFromDatabase();
        loadDonationsChart();


    }



    private void loadEventsFromDatabase() {

        List<Evennement> events = EvennementService.getInstance().readAll();
        ObservableList<Evennement> eventList = FXCollections.observableArrayList(events);
        events_table.setItems(eventList);
    }


    private void loadDonsFromDatabase() {

            List<Don> donations = DonService.getInstance().getDonationsWithDetails();
            ObservableList<Don> dons = FXCollections.observableArrayList(donations);
            donationsTable.setItems(dons);
        }





    @FXML
    void ajouter_event(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Evennement/Ajouter_event.fxml"));
            Parent root = loader.load();
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
        int eventId = event.getId();
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
        List<Evennement> events = EvennementService.getInstance().rechercherEvent(searchTerm);
        ObservableList<Evennement> eventList = FXCollections.observableArrayList(events);
        events_table.setItems(eventList);
    }

    private void loadDonationsChart() {
        // Fetch donations from DonService with associated event IDs
        List<Don> donations = DonService.getInstance().readAll();

        // Create a data series to hold donation amounts
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();

        // Populate the data series with donation amounts and associated event IDs
        for (Don donation : donations) {
            dataSeries.getData().add(new XYChart.Data<>(String.valueOf(donation.getEvennement().getId()), donation.getMontant_user()));
        }

        // Add the data series to the BarChart
        chart_don.getData().add(dataSeries);



        chart_don.getStylesheets().add(getClass().getResource("/Styles/eventCSS/admindash.css").toExternalForm());

    }







}
