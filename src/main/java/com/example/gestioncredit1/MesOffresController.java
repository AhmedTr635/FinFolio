package com.example.gestioncredit1;

import Entity.Credit;
import Entity.Offre;
import Service.OffreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;
import java.util.Optional;

public class MesOffresController {

    @FXML
    private Button Update_offre;

    @FXML
    private TextField my_id_offre;

    @FXML
    private TextField myuser_id;

    @FXML
    private TextField my_icreditid;
////////////////////////////////////////

    @FXML
    private TableView<Offre> tableau_Offre;

    @FXML
    private TableColumn<Offre, Double> montant_offre;

    @FXML
    private TableColumn<Offre, Integer> user_id_offre;

    @FXML
    private TableColumn<Offre, Integer> credit_id_offre1;

    @FXML
    private TableColumn<Offre, Double> intret_offre;

    @FXML
    private TableColumn<Offre, Integer> id_offre;

    @FXML
    private Button delete_offre;
    @FXML
    private TextField interetInput;
    @FXML
    private TextField montantInput;

    private ObservableList<Offre> observableList2;

    public void initialize() {
        OffreService offreService = new OffreService();
        observableList2 = FXCollections.observableList(offreService.getAllOffres());

        tableau_Offre.setItems(observableList2);

        id_offre.setCellValueFactory(new PropertyValueFactory<>("id"));
        montant_offre.setCellValueFactory(new PropertyValueFactory<>("montant"));
        intret_offre.setCellValueFactory(new PropertyValueFactory<>("interet"));
        user_id_offre.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        credit_id_offre1.setCellValueFactory(new PropertyValueFactory<>("credit_id"));

        tableau_Offre.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Populate input fields with selected offre's details
                Offre selectedOffre = tableau_Offre.getSelectionModel().getSelectedItem();
                montantInput.setText(String.valueOf(selectedOffre.getMontant()));
                interetInput.setText(String.valueOf(selectedOffre.getInteret()));

                // Hide other fields
                my_id_offre.setVisible(false);
                myuser_id.setVisible(false);
                my_icreditid.setVisible(false);
            }
        });
    }


    @FXML
    void Update_offre(ActionEvent event) {
        // Get the selected offer
        Offre selectedOffre = tableau_Offre.getSelectionModel().getSelectedItem();

        if (selectedOffre == null) {
            showAlert("Veuillez sélectionner une offre à mettre à jour.", Alert.AlertType.ERROR);
            return;
        }

        // Prompt the user for confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Voulez-vous vraiment mettre à jour cette offre ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed, proceed with the update
            try {
                // Get updated montant and intret values from the input fields
                double updatedMontant = Double.parseDouble(montantInput.getText());
                double updatedIntret = Double.parseDouble(interetInput.getText());

                // Update the offer object with the new values
                selectedOffre.setMontant(updatedMontant);
                selectedOffre.setInteret(updatedIntret);

                // Call the service to update the offer in the database
                OffreService offreService = new OffreService();
                boolean isUpdated = offreService.updateOffre(selectedOffre);

                // Show appropriate message based on update success or failure
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                if (isUpdated) {
                    alert.setContentText("Offre mise à jour avec succès.");
                } else {
                    alert.setContentText("Échec de la mise à jour de l'offre.");
                }
                alert.showAndWait();

                // Refresh the table view to reflect the changes
                refreshTable();
            } catch (NumberFormatException e) {
                showAlert("Veuillez entrer des valeurs valides pour le montant et l'intérêt.", Alert.AlertType.ERROR);
            }
        }
    }




    @FXML
    void delete_offre(ActionEvent event) {
        Offre selectedOffre = tableau_Offre.getSelectionModel().getSelectedItem();

        if (selectedOffre == null) {
            showAlert("Veuillez sélectionner une offre à supprimer.", Alert.AlertType.ERROR);
            return;
        }

        int offreId = selectedOffre.getId();
        OffreService offreService = new OffreService();
        boolean isDeleted = offreService.deleteOffre(offreId);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        if (isDeleted) {
            alert.setContentText("Offre supprimée avec succès.");
        } else {
            alert.setContentText("Échec de la suppression de l'offre. Aucune offre trouvée avec l'ID spécifié.");
        }
        alert.showAndWait();

        refreshTable();
    }

    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void refreshTable() {
        OffreService of = new OffreService();
        observableList2.clear();
        observableList2.addAll(of.getAllOffres());
    }





}

