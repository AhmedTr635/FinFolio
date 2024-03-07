package com.example.finfolio.Credits;

import Models.Model;
import com.example.finfolio.Entite.Offre;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.OffreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class Offrespourvos {

    @FXML
    private TableView<Offre> offresTable;

    @FXML
    private TableColumn<Offre, Integer> idOfferColumn;

    @FXML
    private TableColumn<Offre, Double> montantColumn;

    @FXML
    private TableColumn<Offre, Double> interetColumn;

    @FXML
    private TableColumn<Offre, Integer> userIdColumn;

    @FXML
    private TableColumn<Offre, Integer> creditIdColumn;

    @FXML
    private TableColumn<Offre, String> userNameColumn;

    @FXML
    private Button acceptButton;

    @FXML
    private Button deleteButton;

    private OffreService offreService = new OffreService();

    @FXML
    void initialize() {
        User user = Model.getInstance().getUser();
        // Fetch offers for the specific owner ID (replace 1 with the actual owner ID)
        List<Offre> ownerOffers = offreService.getOffersByCreditOwnerId(user.getId());

        // Populate the TableView with owner offers
        ObservableList<Offre> observableList = FXCollections.observableList(ownerOffers);
        offresTable.setItems(observableList);

        // Set cell value factories for table columns
        idOfferColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        interetColumn.setCellValueFactory(new PropertyValueFactory<>("interet"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        creditIdColumn.setCellValueFactory(new PropertyValueFactory<>("credit_id"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
    }

    @FXML
    void acceptOffre(ActionEvent event) {
        // Handle accepting the selected offer
        Offre selectedOffer = offresTable.getSelectionModel().getSelectedItem();
        if (selectedOffer != null) {
            // Implement accept offer functionality
        }
    }

    @FXML
    void deleteOffre(ActionEvent event) {
        // Handle deleting the selected offer
        Offre selectedOffer = offresTable.getSelectionModel().getSelectedItem();
        if (selectedOffer != null) {
            // Implement delete offer functionality
        }
    }
}
