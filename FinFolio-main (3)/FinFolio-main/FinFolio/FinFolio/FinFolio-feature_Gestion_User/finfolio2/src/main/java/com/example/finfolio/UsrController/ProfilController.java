package com.example.finfolio.UsrController;

import Models.Model;
import Views.AlerteFinFolio;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfilController implements Initializable {
    public PasswordField mdpFldC;
    public Label NomLabel;
    public ImageView image;
    public Label errornom;
    public Label errorprenom;
    public Label erroremail;
    public Label numTelerror;
    public Label telError;
    public Label mdpError;
    public Label mdpcerror;
    public Button changeeBtn;

    @FXML
    private TextField emailFld;

    @FXML
    private TextField mdpFld;

    @FXML
    private TextField nomFld;

    @FXML
    private TextField numTelFld;

    @FXML
    private TextField prenomFld;

    @FXML
    private Button sauvegarderBtn;
    private String imagePath=Model.getInstance().getUser().getImage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initProfil();

        // Ajout d'écouteurs de changement de texte pour tous les champs de texte
        addInputControlListener(nomFld, errornom, "Nom");
        addInputControlListener(prenomFld, errorprenom, "Prénom");
        addInputControlListener(emailFld, erroremail, "Email");
        addInputControlListener(numTelFld, numTelerror, "Numéro de téléphone");
        addInputControlListener(mdpFld, mdpError, "Mot de passe");
        addInputControlListener(mdpFldC, mdpcerror, "Confirmation de mot de passe");

        sauvegarderBtn.setOnAction(e-> {
            try {
                onSauvegarder();
            } catch (SQLException | NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        });
        changeeBtn.setOnAction(e-> {
            try {
                onImporter();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    void initProfil() {
        NomLabel.setText(Model.getInstance().getUser().getNom() + " " + Model.getInstance().getUser().getPrenom());
        displayImage(Model.getInstance().getUser().getImage());
        nomFld.setText(Model.getInstance().getUser().getNom());
        prenomFld.setText(Model.getInstance().getUser().getPrenom());
        String n = Model.getInstance().getUser().getNumtel().substring(4);
        numTelFld.setText(n);
        emailFld.setText(Model.getInstance().getUser().getEmail());

    }

    void onSauvegarder() throws SQLException, NoSuchAlgorithmException {
        String pass;
        if (inputcontrol()) {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(mdpFld.getText().getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            if (mdpFld.getText().isEmpty())
                pass=Model.getInstance().getUser().getPassword();
            else
                pass=hexString.toString();




            User user = new User(Model.getInstance().getUser().getId(),nomFld.getText(), prenomFld.getText(), emailFld.getText(), "+216" + numTelFld.getText(), pass, "Mourouj", 0, 2, "user", Model.getInstance().getUser().getSolde(),Model.getInstance().getUser().getStatut() , imagePath,Model.getInstance().getUser().getDatepunition());
            UserService userS = new UserService();
            userS.update(user);
            AlerteFinFolio.alerteSucces("Vos données sont modifiées","Modification des données");
        }
    }

    private void addInputControlListener(TextField textField, Label errorLabel, String fieldName) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = true; // Initialise la validation à true

            // Vérifie chaque champ séparément
            if (textField == nomFld) {
                isValid = validateField(textField, errorLabel, "Nom", 3, 32);
            } else if (textField == prenomFld) {
                isValid = validateField(textField, errorLabel, "Prénom", 3, 32);
            } else if (textField == emailFld) {
                isValid = validateEmailField(textField, errorLabel);
            } else if (textField == numTelFld) {
                isValid = validateNumTelField(textField, errorLabel);
            } else if (textField == mdpFld) {
                isValid = validatePassword((PasswordField) textField, errorLabel);
            } else if (textField == mdpFldC) {
                isValid = validatePasswordConfirmation((PasswordField) textField, mdpFld, errorLabel);
            }

            // Ajout de la classe error-label si le champ est invalide
            if (!isValid) {
                errorLabel.getStyleClass().add("error-label");
            } else {
                errorLabel.getStyleClass().removeAll("error-label");
            }
        });
    }


    private boolean inputcontrol() {
        boolean isValid = true;
        isValid &= validateField(nomFld, errornom, "Nom", 3, 32);
        isValid &= validateField(prenomFld, errorprenom, "Prénom", 3, 32);
        isValid &= validateEmailField(emailFld, erroremail);
        isValid &= validateNumTelField(numTelFld, numTelerror);
        isValid &= validatePassword((PasswordField) mdpFld, mdpError);
        isValid &= validatePasswordConfirmation(mdpFldC, mdpFld, mdpcerror);
        return isValid;
    }

    private boolean validateField(TextField textField, Label errorLabel, String fieldName, int minLength, int maxLength) {
        String value = textField.getText().trim();
        if (value.isEmpty()) {
            errorLabel.setText("Veuillez entrer votre " + fieldName);
            textField.getStyleClass().add("error");
            errorLabel.getStyleClass().add("error-label");
            return false;
        } else if (value.length() < minLength || value.length() > maxLength) {
            errorLabel.setText(fieldName + " doit être entre " + minLength + " et " + maxLength + " caractères");
            return false;
        } else {
            errorLabel.setText("");
            textField.getStyleClass().removeAll("error");
            errorLabel.getStyleClass().removeAll("error-label");
            return true;
        }
    }

    private boolean validateEmailField(TextField emailField, Label errorLabel) {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            errorLabel.setText("Veuillez entrer votre email.");
            emailField.getStyleClass().add("error");
            errorLabel.getStyleClass().add("error-label");
            return false;
        } else if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            errorLabel.setText("Email non valide");
            return false;
        } else {
            errorLabel.setText("");
            emailField.getStyleClass().removeAll("error");
            errorLabel.getStyleClass().removeAll("error-label");
            return true;
        }
    }

    private boolean validateNumTelField(TextField numTelField, Label errorLabel) {
        String numTel = numTelField.getText().trim();
        if (!numTel.matches("\\d{8}")) {
            errorLabel.setText("Numéro de téléphone doit être exactement 8 chiffres");
            numTelField.getStyleClass().add("error");
            errorLabel.getStyleClass().add("error-label");
            return false;
        } else {
            errorLabel.setText("");
            numTelField.getStyleClass().removeAll("error");
            errorLabel.getStyleClass().removeAll("error-label");
            return true;
        }
    }

    private boolean validatePassword(PasswordField passwordField, Label errorLabel) {
        String password = passwordField.getText();
        if (passwordField.getText().isEmpty())
        {errorLabel.setText("");
            passwordField.getStyleClass().removeAll("error");
            errorLabel.getStyleClass().removeAll("error-label");
            return true;
        }
        else if (!password.matches("^(?=.*[A-Z])(?=.*[a-z]).{8,}$")) {
            errorLabel.setText("Mot de Passe doit être composé au moins de 8 caractères et une majuscule");
            passwordField.getStyleClass().add("error");
            errorLabel.getStyleClass().add("error-label");
            return false;
        }


            else {
            errorLabel.setText("");
            passwordField.getStyleClass().removeAll("error");
            errorLabel.getStyleClass().removeAll("error-label");
            return true;
        }
    }

    private boolean validatePasswordConfirmation(PasswordField confirmationField, TextField passwordField, Label errorLabel) {
        String confirmation = confirmationField.getText();
        String password = passwordField.getText();
        if (!confirmation.equals(password)) {
            errorLabel.setText("Les mots de passe ne correspondent pas");
            confirmationField.getStyleClass().add("error");
            errorLabel.getStyleClass().add("error-label");
            return false;
        } else {
            errorLabel.setText("");
            confirmationField.getStyleClass().removeAll("error");
            errorLabel.getStyleClass().removeAll("error-label");
            return true;
        }
    }
    private void onImporter() throws IOException {

        String selectedImagePath = importImage();

        // If an image is selected, update the imagePath and the ImageView
        if (selectedImagePath != null) {
            imagePath = selectedImagePath;
            displayImage(imagePath); // Update the ImageView with the new image
        }

    }
    private String importImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // An image was selected
            return selectedFile.getAbsolutePath();
        } else {
            // No image was selected
            return null;
        }
    }
    void displayImage(String imagePath) {
        if (imagePath != null) {
            File file = new File(imagePath);
            Image image2 = new Image(file.toURI().toString());
            image.setImage(image2);
        } else {
            // Set a default image if no image path is provided
            image.setImage(new Image(getClass().getResource("/com/example/finfolio/Pics/simpleUr.png").toString()));
        }
    }
}
