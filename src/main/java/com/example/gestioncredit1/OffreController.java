package com.example.gestioncredit1;

import Entity.Offre;
import Service.OffreService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import Entity.Credit;
import Service.CreditService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OffreController {
    @FXML
    private Button ajouterCredit;


    @FXML
    private TextField Montant_offre_toadd;
    @FXML
    private Button myoffres;
    @FXML
    private TextField interet_offre_toadd;

    @FXML
    private Button searchbutton;



    @FXML
    private Button Offre_Credit;

    @FXML
    private AnchorPane credit_dispaly;

    @FXML
    private VBox thevbox_all_dispaly;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox credit_vbox;

    @FXML
    private TextField userid_toadd;

    @FXML
    private Button vosoffres;

    @FXML
    private Button update_offre;

    @FXML
    private Button envoyer_offre;

    @FXML
    private TextField itf_intret_Ma;

    @FXML
    private DatePicker tf_date_D;

    @FXML
    private DatePicker tf_date_F;

    @FXML
    private TextField tf_iduser;

    @FXML
    private TextField tf_intretMin;

    @FXML
    private TextField tf_montant;


    @FXML
    private TextField  searchField;


    @FXML
    private ScrollPane creditScrollPane;

    @FXML
    private VBox creditInfoVBox;
    @FXML
    private Button set_credit;

    private ObservableList<Credit> observableList;
    private ObservableList<Offre> observableList2;
    private Credit[] credits;


    private void loadCredits() {
        CreditService creditService = new CreditService();
        List<Credit> creditList = creditService.readAllCredits();
        credits = creditList.toArray(new Credit[0]);
    }

//      User s1 = new User(resultSet.getInt("user_id"));
//      UserService s11 = new UserService();

    public void refreshTable() {
        OffreService of = new OffreService();
        observableList2.clear();
        observableList2.addAll(of.getAllOffres());
    }
    @FXML
    void initialize() {
        loadCredits(); // Initialize the credits array
        loadCreditAnchorpane(); // Load the initial display of credits
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Perform the search with the new value
            List<Credit> searchResults = performSearch(newValue);
            // Update the display with the search results
            updateDisplay(searchResults);
        });
    }

    private void loadCreditAnchorpane() {
        CreditService creditService = new CreditService();
        List<Credit> credits = creditService.readAllCredits();

        double layoutY = 10; // Initial Y position

        for (Credit credit : credits) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("creditAnchorpane.fxml"));
                AnchorPane creditAnchorPane = loader.load();

                // Get the controller of the loaded FXML
                CreditAnchorpane controller = loader.getController();

                // Fetch the user name from the Credit object
                String userName = credit.getUserName();
                String user_id = String.valueOf(credit.getUser().getId());// Assuming you have a method to get user name from Credit

                // Initialize the CreditAnchorpane with data, including the user name
                controller.initialize(
                        String.valueOf(credit.getId()),
                        String.valueOf(credit.getMontant()),
                        String.valueOf(credit.getInteretMax()),
                        String.valueOf(credit.getInteretMin()),
                        credit.getDateD(),
                        credit.getDateF(),
                        String.valueOf(credit.getUser().getId()), // Fetch user ID from User object associated with Credit
                        userName, // Pass creditID
                        String.valueOf(credit.getId()),
                        user_id// Pass user name
                );

                // Set layout position for the current anchor pane
                creditAnchorPane.setLayoutY(layoutY);

                // Increment layoutY for the next anchor pane
                layoutY += creditAnchorPane.getPrefHeight() + 10; // Add some spacing between anchor panes

                // Add the credit anchor pane to the VBox (creditInfoVBox)
                creditInfoVBox.getChildren().add(creditAnchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void navigateToMesOffres(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MesOffres.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Mes Offres"); // Set the title of the stage

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void offre_credit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sendOffer.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Send Offer"); // Set the title of the stage

            // Show the stage
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
}
    @FXML
    void handleSearchButtonAction(ActionEvent event) {

        }


    private List<Credit> performSearch(String query) {
        List<Credit> searchResults = new ArrayList<>();

        // If the query is empty, return all credits
        if (query.isEmpty()) {
            return new ArrayList<>(Arrays.asList(credits));
        }

        // Filter the list of credits based on the search query
        for (Credit credit : credits) { // Assuming 'credits' is the list of all credits
            if (matchesSearchCriteria(credit, query)) {
                searchResults.add(credit);
            }
        }

        return searchResults;
    }

    private boolean matchesSearchCriteria(Credit credit, String query) {
        // Convert the query to lower case for case-insensitive comparison
        String lowercaseQuery = query.toLowerCase();

        // Check if the credit is null
        if (credit == null) {
            return false;
        }

        // Check if the query matches the montant of the credit
        String montant = String.valueOf(credit.getMontant());
        if (montant.toLowerCase().contains(lowercaseQuery)) {
            return true;
        }

        // Check if the query matches the intretMax of the credit
        String intretMax = String.valueOf(credit.getInteretMax());
        if (intretMax.toLowerCase().contains(lowercaseQuery)) {
            return true;
        }

        // Check if the query matches the intretMin of the credit
        String intretMin = String.valueOf(credit.getInteretMin());
        if (intretMin.toLowerCase().contains(lowercaseQuery)) {
            return true;
        }

        // Check if the query matches the user's name
        String userName = credit.getUserName().toLowerCase(); // Assuming the method to get the user's name is getUserName()
        if (userName.contains(lowercaseQuery)) {
            return true;
        }

        // Check if the query matches the date of the credit
        String date = credit.getDateD().toLowerCase(); // Assuming the method to get the date is getDate()
        if (date.contains(lowercaseQuery)) {
            return true;
        }

        return false;
    }




    private void updateDisplay(List<Credit> searchResults) {
        creditInfoVBox.getChildren().clear(); // Clear the current display

        // Add anchor panes for each search result
        for (Credit credit : searchResults) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("creditAnchorpane.fxml"));
                AnchorPane creditAnchorPane = loader.load();

                // Get the controller of the loaded FXML
                CreditAnchorpane controller = loader.getController();

                // Fetch the user name from the Credit object
                int user_id = credit.getUser().getId();

                String userName = credit.getUserName(); // Assuming you have a method to get user name from Credit

                // Initialize the CreditAnchorpane with data, including the user name
                controller.initialize(
                        String.valueOf(credit.getId()),
                        String.valueOf(credit.getMontant()),
                        String.valueOf(credit.getInteretMax()),
                        String.valueOf(credit.getInteretMin()),
                        credit.getDateD(),
                        credit.getDateF(),
                        String.valueOf(credit.getUser().getId()), // Fetch user ID from User object associated with Credit
                        userName, // Pass creditID
                        String.valueOf(credit.getId()),
                        String.valueOf(user_id)
                );// Pass user name

                // Add the credit anchor pane to the VBox (creditInfoVBox)
                creditInfoVBox.getChildren().add(creditAnchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void search_credit(KeyEvent event) {
        String query = searchField.getText(); // Get the search query from the text field

        // Perform the search
        List<Credit> searchResults = performSearch(query);

        // Update the display with the search results
        updateDisplay(searchResults);
    }

    public void ajouterCredit(ActionEvent actionEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("ajouterCredit.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ajouterCredit.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
