package com.example.finfolio.Credits;

import Models.Model;
import com.example.finfolio.Entite.Credit;
import com.example.finfolio.Entite.Offre;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.CreditService;
import com.example.finfolio.Service.MessageService;
import com.example.finfolio.Service.OffreService;
import com.example.finfolio.UsrController.UserController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class OffreController   {
    @FXML
    private VBox barchart_vbox;

    private AreaChart<?, ?> zareaChart;

    @FXML
    private Label soldeLabel;
    @FXML
    private Button ajouterCredit;

    @FXML
    private Label amessagesLabel;

    @FXML
    private Button offres_to_me;
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

    @FXML
    private BarChart<?, ?> dali12;

    private ObservableList<Credit> observableList;
    private ObservableList<Offre> observableList2;
    private Credit[] credits;


    //private MessageService messageService = new MessageService();



    private void loadCredits() {
        CreditService creditService = new CreditService();
        List<Credit> creditList = creditService.readAllCredits();
        credits = creditList.toArray(new Credit[0]);

        ////////////////////////

    }



    private void updateReceivedMessageCountLabel() {


            // Get the user ID of the current user (replace 1 with the actual user ID)
            int userId = 1; // Assuming user ID 1 for demonstration purposes
        MessageService m = new MessageService();
            // Call the MessageService to get the count of received messages for the user
            int receivedMessageCount = m.countReceivedMessagesByUserId(userId);
           // System.out.println(receivedMessageCount);
            // Update the messagesLabel with the count of received messages
            amessagesLabel.setText("" + receivedMessageCount);


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
        zareaChart = (AreaChart<?, ?>) barchart_vbox.getChildren().get(0);

        updateReceivedMessageCountLabel();
        loadCredits(); // Initialize the credits array
        loadCreditAnchorpane(); // Load the initial display of credits
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Perform the search with the new value
            List<Credit> searchResults = performSearch(newValue);
            // Update the display with the search results
            updateDisplay(searchResults);
        });
        ajouterCredit.setOnAction(s -> onCredits());

        updateAreaChart(); // Update the area chart
        OffreService offreService = new OffreService();
       User user=Model.getInstance().getUser();
        double solde = offreService.getSoldeForUser(user.getId());

        // Update the text of the soldeLabel
        soldeLabel.setText(String.format("%.3f $", solde));

    }


    private void loadCreditAnchorpane() {
        CreditService creditService = new CreditService();
        List<Credit> credits = creditService.readAllCredits();

        double layoutY = 10; // Initial Y position
        int currentUserId=1;


        for (Credit credit : credits) {
            // Check if the credit belongs to the current user
            if (credit.getUser().getId() == currentUserId) {
                continue; // Skip this credit
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/creditAnchorpane.fxml"));
                AnchorPane creditAnchorPane = loader.load();

                // Get the controller of the loaded FXML
                CreditAnchorpane controller = loader.getController();

                // Fetch the user name from the Credit object
                String userName = credit.getUserName();
                String user_id = String.valueOf(credit.getUser().getId());// Assuming you have a method to get user name from Credit


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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/mesOffres.fxml"));
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

        // Add anchor panes for each search result, excluding credits belonging to user 1
        for (Credit credit : searchResults) {
            if (credit.getUser().getId() != 1) { // Exclude credits belonging to user 1
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/creditAnchorpane.fxml"));
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
                            userName, // Pass user name
                            String.valueOf(credit.getId()),
                            String.valueOf(user_id)
                    );

                    // Add the credit anchor pane to the VBox (creditInfoVBox)
                    creditInfoVBox.getChildren().add(creditAnchorPane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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


    public void ajouterCredit() {

      /*  try {
            OffreController offreController = new OffreController();

// Set the MessageService for OffreController


            Parent root = FXMLLoader.load(getClass().getResource("/com/example/finfolio/User/ajouterOffre.fxml"));
            ajouterCredit.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }*/
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Credits");



    }
    private void onCredits(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set("Credits");
    }


    @FXML
    void offres_to_me(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/offrespourvos.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("offres pour moi"); // Set the title of the stage

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void updateAreaChart() {
        loadCredits(); // Ensure that the credits array is initialized before updating the area chart

        int countGreaterThan5000 = 0;
        int countGreaterThan10000 = 0;
        int countGreaterThan150000 = 0;

        // Count the number of credits with montant > 5000, 10000, and 150000
        for (Credit credit : credits) {
            if (credit.getMontant() > 5000) {
                countGreaterThan5000++;
            }
            if (credit.getMontant() > 10000) {
                countGreaterThan10000++;
            }
            if (credit.getMontant() > 150000) {
                countGreaterThan150000++;
            }
        }

        // Clear previous data and add new series to the area chart
        zareaChart.getData().clear();

        // Create series for the area chart
        XYChart.Series series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Montant > 5000", countGreaterThan5000));
        series.getData().add(new XYChart.Data<>("Montant > 10000", countGreaterThan10000));
        series.getData().add(new XYChart.Data<>("Montant > 150000", countGreaterThan150000));

        // Add the series to the area chart
        zareaChart.getData().add(series);
    }



    public void reciver(javafx.scene.input.MouseEvent mouseEvent) {


    }

    @FXML
    void recivemessg(ActionEvent event) {
        try {
            // Load the receiver FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/reciver.fxml"));
            Parent root = loader.load();

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Receiver");

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }





}


}
