package com.example.finfolio.Evenement;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import com.example.finfolio.Entite.Don;
import com.example.finfolio.Service.DonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class AdminDonController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Don, Float> don_montant;

    @FXML
    private TableColumn<Don, Integer> event_id;



    @FXML
    private TableColumn<Don, Integer> user_id;

    @FXML
    private TableView<Don> don_table;

    @FXML
    private BarChart<String,Number > chart_don;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    void initialize() {
        loadDonations();
        loadDonationsChart();
    }


    private void loadDonations() {
        // Fetch donations from DonService
        Don d = new Don();
        List<Don> donations = DonService.getInstance().readAll();

        // Create an ObservableList from the list of donations
        ObservableList<Don> donationList = FXCollections.observableArrayList(donations);

        // Set the items of the TableView to the ObservableList of donations
        don_table.setItems(donationList);

        // Associate each TableColumn with the appropriate property of the Donation object
        don_montant.setCellValueFactory(new PropertyValueFactory<>("montant_user"));
        event_id.setCellValueFactory(new PropertyValueFactory<>("evenement_id")); // Assuming you have a method to get the event name from the donation
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id")); // Assuming you have a method to get the user ID from the donation



    }


    private void loadDonationsChart() {
        // Fetch donations from DonService with associated event IDs
        List<Don> donations = DonService.getInstance().readAll();

        // Create a data series to hold donation amounts
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();

        // Populate the data series with donation amounts and associated event IDs
        for (Don donation : donations) {
            dataSeries.getData().add(new XYChart.Data<>(String.valueOf(donation.getEvennement()), donation.getMontant_user()));
        }

        // Add the data series to the BarChart
        chart_don.getData().add(dataSeries);

        // Set labels for X and Y axis
        xAxis.setLabel("Event ID");
        yAxis.setLabel("Donation Amount");

        chart_don.getStylesheets().add(getClass().getResource("/CSS/admindash.css").toExternalForm());

    }

}
