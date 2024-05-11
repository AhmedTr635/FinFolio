package com.example.finfolio.Investissement.Controller;

import com.example.finfolio.Investissement.Entite.RealEstate;
import com.example.finfolio.Investissement.Service.RealEstateService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterREController implements Initializable {

    @FXML
    private TextField ROI;
    @FXML
    private Label superf;
    @FXML
    private Label iddd;
    @FXML
    private Label chbrs;

    @FXML
    private Button addImage;

    @FXML
    private Button ajouterRE;

    @FXML
    private TextField emplacement;

    @FXML
    private TextField id;

    @FXML
    private ImageView imageuploaded;

    @FXML
    private TextField name;

    @FXML
    private TextField nbrchambre;

    @FXML
    private Label saisie_addresse;

    @FXML
    private Label saisie_date;

    @FXML
    private Label saisie_montant;

    @FXML
    private Label saisie_nom;

    @FXML
    private TextField superficie;

    @FXML
    private TextField valeur;
    private byte[] imageBytes;

    @FXML
    void AddImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        // Set the initial directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        // Filter to show only image files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(imageuploaded.getScene().getWindow());
        if (selectedFile != null) {
            try {
                // Read the selected image file and display it
                FileInputStream inputStream = new FileInputStream(selectedFile);
                byte[] imageData = new byte[(int) selectedFile.length()];
                imageBytes = imageData;
                inputStream.read(imageData);
                inputStream.close();
                Image image = new Image(new ByteArrayInputStream(imageData));
                imageuploaded.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @FXML
    void ajouterRE(ActionEvent event) {
        // Handle adding a new real estate
        int id = Integer.parseInt(this.id.getText());
        String name = this.name.getText();
        String emplacement = this.emplacement.getText();
        String superficie = this.superficie.getText();
        String nbrchambre = this.nbrchambre.getText();
        String valeur = this.valeur.getText();
        String ROI = this.ROI.getText();
        if (name.isEmpty() || emplacement.isEmpty() || superficie.isEmpty() || nbrchambre.isEmpty() || valeur.isEmpty() || ROI.isEmpty()) {
            // Show an error message if any of the fields are empty
            saisie_nom.setText(name.isEmpty() ? "Veuillez saisir le nom de l'immobilier." : "");
            saisie_addresse.setText(emplacement.isEmpty() ? "Veuillez saisir l'adresse de l'immobilier." : "");
            saisie_montant.setText(valeur.isEmpty() ? "Veuillez saisir le montant de l'immobilier." : "");
            saisie_date.setText(ROI.isEmpty() ? "Veuillez saisir le ROI de l'immobilier." : "");
            showAlert("Erreur","Veuillez remplir tout les champs");
            return;

        }
        /*int id, String name, byte[] imageData, String emplacement, float ROI, double valeur, int nbChambres, float superficie, int nbrclick*/
        RealEstate realEstate = new RealEstate(id,name, imageBytes, emplacement, Float.parseFloat(ROI), Double.parseDouble(valeur), Integer.parseInt(nbrchambre), Float.parseFloat(superficie), 0);
        // Add the real estate to the database
        RealEstateService realEstateService = new RealEstateService();
        realEstateService.add(realEstate);
        // Show a success message
        showAlert("Succès", "Immobilier ajouté avec succès.");





    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void handleNumericInput(KeyEvent event, Label warningText) {
        TextField textField = (TextField) event.getSource();
        String character = event.getCharacter();
        if (!character.matches("[0-9]")) {
            event.consume(); // Consume the event to prevent the character from being entered
            if (textField != null) {
                textField.setStyle("-fx-border-color: red;"); // Change border color to red
                textField.setStyle("-fx-text-fill: red;"); // Change text color to red
                setWarningMessage(textField, warningText);
            }
        } else {
            if (textField != null) {
                textField.setStyle("-fx-border-color: blue;"); // Change border color to blue
                textField.setStyle("-fx-text-fill: black;"); // Change text color to black
                clearWarningMessage(textField, warningText);
            }
        }
    }
    private void handleStringInput(KeyEvent event, Label warningText) {
        TextField textField = (TextField) event.getSource();
        String character = event.getCharacter();
        if (character.matches("[a-zA-Z]") || character.isEmpty()) {
            // Allow alphabetic characters and empty string (backspace)
            if (textField != null) {
                textField.setStyle("-fx-border-color: blue;"); // Change border color to blue
                textField.setStyle("-fx-text-fill: black;"); // Change text color to black
                clearWarningMessage(textField,warningText);
            }
        } else {
            event.consume(); // Consume the event to prevent the character from being entered
            if (textField != null) {
                textField.setStyle("-fx-border-color: red;"); // Change border color to red
                textField.setStyle("-fx-text-fill: red;"); // Change text color to red
                setWarningMessage(textField, warningText);
            }
        }
    }
    private void clearWarningMessage(TextField textField,Label warningText){
        if (textField != null) {
            if (warningText != null) {
                warningText.setText(""); // Clear warning message
            }
        }
    }

    private void setWarningMessage(TextField textField, Label warningText){
        if (textField != null) {

            if (warningText != null) {
                //warningText.setTextContent(Color.RED);
                warningText.setText("Entrez une valeur valide !");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setOnKeyTyped(event -> handleNumericInput(event, iddd));
        name.setOnKeyTyped(event -> handleStringInput(event, saisie_nom));
        emplacement.setOnKeyTyped(event -> handleStringInput(event, saisie_addresse));
        superficie.setOnKeyTyped(event -> handleNumericInput(event, superf));
        nbrchambre.setOnKeyTyped(event -> handleNumericInput(event, chbrs));
        valeur.setOnKeyTyped(event -> handleNumericInput(event, saisie_montant));
        ROI.setOnKeyTyped(event -> handleNumericInput(event, saisie_date));

    }

}
