package com.example.finfolio.Investissement.Controller;

import com.example.finfolio.Investissement.Entite.RealEstate;
import com.example.finfolio.Investissement.Service.RealEstateService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifierREController implements Initializable {
    public RealEstate re=new RealEstate();

    public ModifierREController() {
    }

    @FXML
    private TextField ROI;

    @FXML
    private Button addImage;

    @FXML
    private Button ajouterRE;

    @FXML
    private Label chbrs;

    @FXML
    private Label iddd;

    @FXML
    private ImageView imageDefaut;

    @FXML
    private ImageView imageuploaded;

    @FXML
    private TextField name;

    @FXML
    private Button quit;

    @FXML
    private Label saisie_addresse;

    @FXML
    private Label saisie_date;

    @FXML
    private Label saisie_montant;

    @FXML
    private Label saisie_nom;

    @FXML
    private Label superf;

    @FXML
    private TextField valeur;
    public byte[] imageBytes;

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
        //int id = Integer.parseInt(this.id.getText());
        String name = this.name.getText();
        //String emplacement = this.emplacement.getText();
        //String superficie = this.superficie.getText();
        //String nbrchambre = this.nbrchambre.getText();
        String valeur = this.valeur.getText();
        String ROI = this.ROI.getText();
        if (name.isEmpty() ||  valeur.isEmpty() || ROI.isEmpty() ) {
            // Show an error message if any of the fields are empty
            saisie_nom.setText(name.isEmpty() ? "Veuillez saisir le nom de l'immobilier." : "");
            saisie_montant.setText(valeur.isEmpty() ? "Veuillez saisir le montant de l'immobilier." : "");
            saisie_date.setText(ROI.isEmpty() ? "Veuillez saisir le ROI de l'immobilier." : "");
            showAlert("Erreur","Veuillez remplir tout les champs");
            return;

        }
        /*int id, String name, byte[] imageData, String emplacement, float ROI, double valeur, int nbChambres, float superficie, int nbrclick*/
        RealEstate realEstate = new RealEstate(re.getId(), name, imageBytes, re.getEmplacement(), Float.parseFloat(ROI), Double.parseDouble(valeur), re.getNbChambres(), re.getSuperficie(), re.getNbrclick());
        // Add the real estate to the database
        RealEstateService realEstateService = new RealEstateService();
        realEstateService.update(realEstate, re.getId());
        // Show a success message
        showAlert("Succès", "Immobilier modifier avec succès.");

    }

    @FXML
    void retourDashAdmin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("com/example/demo1/RealEstateAdmin.fxml"));
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

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
    public void initData(RealEstate realEstate) {
        this.re = realEstate;

        // Now you can use 're' to populate your UI components
        // For example:
        name.setText(re.getName());
        ROI.setText(String.valueOf(re.getROI()));
        valeur.setText(String.valueOf(re.getValeur()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("haw hne"+re);
        name.setText(re.getName());
        ROI.setText(String.valueOf(re.getROI()));
        valeur.setText(String.valueOf(re.getValeur()));
        if(re.getImageData()!=null){
            imageuploaded.setImage(new Image(new ByteArrayInputStream(re.getImageData())));
        }
        //imageuploaded.setImage(new Image(new ByteArrayInputStream(re.getImageData())));

        name.setOnKeyTyped(event -> handleStringInput(event, saisie_nom));
        valeur.setOnKeyTyped(event -> handleNumericInput(event, saisie_montant));
        ROI.setOnKeyTyped(event -> handleNumericInput(event, saisie_date));

        RealEstate real=new RealEstate();
        real.setName(name.getText());
        real.setROI(Float.parseFloat(ROI.getText()));
        real.setValeur(Double.parseDouble(valeur.getText()));
        real.setId(re.getId());
        real.setEmplacement(re.getEmplacement());
        real.setNbChambres(re.getNbChambres());
        real.setSuperficie(re.getSuperficie());
        real.setNbrclick(re.getNbrclick());
        real.setImageData(imageBytes);

    }
}
