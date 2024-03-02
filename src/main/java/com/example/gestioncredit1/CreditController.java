package com.example.gestioncredit1;

import Entity.Credit;
import Entity.User;
import Service.CreditService;
import Service.MessageService;
import Service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreditController {


    @FXML
    private Label idErrorMessageLabel;

    @FXML
    private Label montantErrorMessageLabel;

    @FXML
    private Label intretMaxErrorMessageLabel;

    @FXML
    private Label intretMinErrorMessageLabel;

////////////////////////////////////////////error/////////////////////////////
    // @FXML
    // private VBox boxContainer;
    ///////////////////////////////////////////

    @FXML
    private Button ButtonAj;
    @FXML
    private Button update;
    @FXML
    private AnchorPane pane;

    @FXML
    private Button delete;

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


    //////////////////////////////navigation////////////////////////
    @FXML
    private Button ajouterCredit;

    @FXML
    private Button ajouter_offre;

    ////////////////////////tableView///////////////////////
    @FXML
    private TableView<Credit> tableau;

    @FXML
    private TableColumn<Credit, Integer> id_user;

    @FXML
    private TableColumn<Credit, Double> Montant;


    @FXML
    private TableColumn<Credit, Double> intretMax;


    @FXML
    private TableColumn<Credit, Double> intretMin;


    @FXML
    private TableColumn<Credit, String> dateD;


    @FXML
    private TableColumn<Credit, String> dateF;

    private ObservableList<Credit> observableList;

    public void refreshTable() {
        CreditService cr = new CreditService();
        int userId = 1;
        observableList.clear();
        observableList.addAll(cr.getCreditsByUserId(userId ));
    }

    public void initialize() {
        CreditService cr = new CreditService();



        // Replace 1 with the ID of the currently logged-in user
        int userId = 1;
        observableList = FXCollections.observableList(cr.getCreditsByUserId(userId));

        tableau.setItems(observableList);

        id_user.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        Montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        intretMax.setCellValueFactory(new PropertyValueFactory<>("interetMax"));
        intretMin.setCellValueFactory(new PropertyValueFactory<>("interetMin"));
        dateD.setCellValueFactory(new PropertyValueFactory<>("dateD"));
        dateF.setCellValueFactory(new PropertyValueFactory<>("dateF"));

        tf_date_D.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now())); // Disable dates before today
            }
        });

        tf_date_F.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now())); // Disable dates before today
            }
        });


        tableau.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Populate input fields with selected credit's details
                Credit selectedCredit = tableau.getSelectionModel().getSelectedItem();

                tf_iduser.setText(String.valueOf(selectedCredit.getUser().getId()));
                tf_date_D.setValue(LocalDate.parse(selectedCredit.getDateD()));
                tf_date_F.setValue(LocalDate.parse(selectedCredit.getDateF()));
                tf_montant.setText(String.valueOf(selectedCredit.getMontant()));
                itf_intret_Ma.setText(String.valueOf(selectedCredit.getInteretMax()));
                tf_intretMin.setText(String.valueOf(selectedCredit.getInteretMin()));
            }
        });


//        // Load the details of the first credit into the box layout when the application starts
//        Credit firstCredit = tableau.getItems().get(0); // Assuming the first credit is at index 0
//        loadCreditDetails(firstCredit);
//
//        // Add listener to TableView selection model
//        tableau.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                // Load the details of the selected credit into the box layout
//                loadCreditDetails(newSelection);
//            }
//        });
    }

//    private void loadCreditDetails(Credit credit) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("box.fxml"));
//            VBox box = loader.load();
//            CreditBoxController boxController = loader.getController();
//            boxController.setCredit(credit);
//            boxContainer.getChildren().clear();
//            boxContainer.getChildren().add(box);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    @FXML
    void ajouterButton(ActionEvent event) {
        try {
            int userId = 1; // Set user ID to 1
            LocalDate dateD = tf_date_D.getValue(); // Get the selected start date
            LocalDate dateF = tf_date_F.getValue(); // Get the selected end date
            double montant = Double.parseDouble(tf_montant.getText());
            double intM = Double.parseDouble(itf_intret_Ma.getText());
            double intMi = Double.parseDouble(tf_intretMin.getText());
                  UserService userService = new UserService();
            // Retrieve the User object corresponding to the provided ID
            User user = userService.getUserByid(userId);
            if (user == null) {
                // Handle case where user is not found
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Utilisateur non trouvé.");
                alert.showAndWait();
                return;
            }

            // Assuming Credit class has a constructor that accepts these parameters
            Credit c1 = new Credit(montant, intM, intMi, dateD.toString(), dateF.toString(), user);
            CreditService cs = new CreditService();
            cs.addi(c1);

            // Show alert after successful addition
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Crédit ajouté avec succès à votre compte. Vous pouvez désormais recevoir des offres de crédit d'autres clients. Merci!!");
            alert.showAndWait(); // Wait for the user to close the alert
            refreshTable();
        } catch (NumberFormatException e) {
            // Handle the case where the input fields are not valid numbers
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer des valeurs numériques valides.");
            alert.showAndWait(); // Wait for the user to close the alert
            refreshTable();
        }
    }



    @FXML
    void delete_button(ActionEvent event) {
        // Retrieve the selected credit from the table
        Credit selectedCredit = tableau.getSelectionModel().getSelectedItem();

        // Check if a row is selected in the table
        if (selectedCredit == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un crédit à supprimer.");
            alert.showAndWait();
            return;
        }

        // Get the credit ID from the selected item
        int creditId = selectedCredit.getId();

        // Call the CreditService to delete the credit from the database
        CreditService cs = new CreditService();
        boolean isDeleted = cs.deleteCredit(creditId);

        // Show alert based on deletion result
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        if (isDeleted) {
            alert.setContentText("Crédit supprimé avec succès.");
        } else {
            alert.setContentText("Échec de la suppression du crédit. Aucun crédit trouvé avec l'ID spécifié.");
        }
        alert.showAndWait();

        refreshTable(); // Refresh the table to reflect the changes
    }


    @FXML
    void update_button(ActionEvent event) {
        try {
            // Retrieve updated values from input fields
            int id_user = Integer.parseInt(tf_iduser.getText());
            LocalDate dateD = tf_date_D.getValue();
            LocalDate dateF = tf_date_F.getValue();
            double montant = Double.parseDouble(tf_montant.getText());
            double intM = Double.parseDouble(itf_intret_Ma.getText());
            double intMi = Double.parseDouble(tf_intretMin.getText());

            // Get the selected credit from the table
            Credit selectedCredit = tableau.getSelectionModel().getSelectedItem();

            // Check if a row is selected in the table
            if (selectedCredit == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un crédit à mettre à jour.");
                alert.showAndWait();
                return;
            }

            // Get the credit ID from the selected item
            int creditId = selectedCredit.getId();

            // Convert LocalDate to String in the desired format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateDString = (dateD != null) ? dateD.format(formatter) : null;
            String dateFString = (dateF != null) ? dateF.format(formatter) : null;

            // Call the CreditService to update the credit in the database
            CreditService cs = new CreditService();
            cs.modifierCredit(montant, intM, intMi, dateDString, dateFString, creditId);

            // Show alert after successful update
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Crédit mis à jour avec succès.");
            alert.showAndWait();

            refreshTable(); // Refresh the table to reflect the changes
        } catch (NumberFormatException e) {
            // Handle the case where the input fields contain invalid numeric values
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer des valeurs numériques valides.");
            alert.showAndWait();
        }
    }


    @FXML
    void CreditScene(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ajouterCredit.fxml"));
            ajouterCredit.getScene().setRoot(root);

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void OffreScene(ActionEvent event) {
        try {
            OffreController offreController = new OffreController();

// Set the MessageService for OffreController


            Parent root = FXMLLoader.load(getClass().getResource("ajouterOffre.fxml"));
            ajouterCredit.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }

    ///////////////////////////////////////////////////
    @FXML
    void intretMax_input(KeyEvent event) {
        handleNumericInput(itf_intret_Ma, event);
    }

    @FXML
    void Montant_input(KeyEvent event) {
        handleNumericInput(tf_montant, event);
    }

    @FXML
    void user_id_input(KeyEvent event) {
        handleNumericInput(tf_iduser, event);
    }

    @FXML
    void intretMin_input(KeyEvent event) {
        handleNumericInput(tf_intretMin, event); // Assuming you'll implement this method later
    }

    private void handleNumericInput(TextField textField, KeyEvent event) {
        String character = event.getCharacter();
        if (!character.matches("[0-9]")) {
            event.consume(); // Consume the event to prevent the character from being entered
            if (textField != null) {
                textField.setStyle("-fx-border-color: red;"); // Change border color to red
                textField.setStyle("-fx-text-fill: red;"); // Change text color to red
                // Set error message text color to red
                switch (textField.getId()) {
                    case "tf_iduser":
                        idErrorMessageLabel.setTextFill(Color.RED); // Change text color to red
                        idErrorMessageLabel.setText("Veuillez entrer un numéro valide.");
                        break;
                    case "tf_montant":
                        montantErrorMessageLabel.setTextFill(Color.RED); // Change text color to red
                        montantErrorMessageLabel.setText("Veuillez entrer un numéro valide.");
                        break;
                    case "itf_intret_Ma":
                        intretMaxErrorMessageLabel.setTextFill(Color.RED); // Change text color to red
                        intretMaxErrorMessageLabel.setText("Veuillez entrer un numéro valide.");
                        break;
                    case "tf_intretMin":
                        intretMinErrorMessageLabel.setTextFill(Color.RED); // Change text color to red
                        intretMinErrorMessageLabel.setText("Veuillez entrer un numéro valide.");
                        break;
                }
            }
        } else {
            if (textField != null) {
                textField.setStyle("-fx-border-color: blue;"); // Change border color to blue
                textField.setStyle("-fx-text-fill: black;"); // Change text color to black
                // Clear error message text
                switch (textField.getId()) {
                    case "tf_iduser":
                        idErrorMessageLabel.setText("");
                        break;
                    case "tf_montant":
                        montantErrorMessageLabel.setText("");
                        break;
                    case "itf_intret_Ma":
                        intretMaxErrorMessageLabel.setText("");
                        break;
                    case "tf_intretMin":
                        intretMinErrorMessageLabel.setText("");
                        break;
                }
            }
        }
    }
}




////////////////////////////////////////////////////
