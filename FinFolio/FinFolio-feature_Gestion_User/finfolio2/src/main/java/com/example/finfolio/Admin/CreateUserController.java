package com.example.finfolio.Admin;

import Views.AlerteFinFolio;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {
    public TextField nom_fld;
    public TextField prenom_fld;
    public PasswordField mdp_fld;
    public TextField email_fld;
    public TextField tel_fld;
    public ChoiceBox role_box;
    public Button creer_btn;
    public Label error_lbl;
    @FXML
    private Label error_mail;

    @FXML
    private Label error_mdp;

    @FXML
    private Label error_nom;
    @FXML
    private Label error_prenom;

    @FXML
    private Label error_ntel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        addInputControlListener(nom_fld, error_nom, "Nom");
        addInputControlListener(prenom_fld, error_prenom, "Prénom");
        addInputControlListener(email_fld, error_mail, "Email");
        addInputControlListener(tel_fld, error_ntel, "Numéro de téléphone");
        addInputControlListener(mdp_fld, error_mdp, "Mot de passe");
        creer_btn.setOnAction(e-> {
            try {
                onEngistrez();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    private void addInputControlListener(TextField textField, Label errorLabel, String fieldName) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = true; // Initialise la validation à true

            // Vérifie chaque champ séparément
            if (textField == nom_fld) {
                isValid = validateField(textField, errorLabel, "Nom", 3, 32);
            } else if (textField == prenom_fld) {
                isValid = validateField(textField, errorLabel, "Prénom", 3, 32);
            } else if (textField == email_fld) {
                isValid = validateEmailField(textField, errorLabel);
            } else if (textField == tel_fld) {
                isValid = validateNumTelField(textField, errorLabel);
            } else if (textField == mdp_fld) {
                isValid = validatePassword((PasswordField) textField, errorLabel);
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
        isValid &= validateField(nom_fld, error_nom, "Nom", 3, 32);
        isValid &= validateField(prenom_fld, error_prenom, "Prénom", 3, 32);
        isValid &= validateEmailField(email_fld, error_mail);
        isValid &= validateNumTelField(tel_fld, error_ntel);
        isValid &= validatePassword((PasswordField) mdp_fld, error_mdp);
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
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z]).{8,}$")) {
            errorLabel.setText("Au moins  8 caractères et une majuscule");
            passwordField.getStyleClass().add("error");
            errorLabel.getStyleClass().add("error-label");
            return false;
        } else {
            errorLabel.setText("");
            passwordField.getStyleClass().removeAll("error");
            errorLabel.getStyleClass().removeAll("error-label");
            return true;
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
    void onEngistrez() throws SQLException, IOException, NoSuchAlgorithmException {
        //String imagePath = null;

        if (inputcontrol()) {

            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(mdp_fld.getText().getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Insert user data along with the image path into the database
            User user = new User(nom_fld.getText(), prenom_fld.getText(), email_fld.getText(), "+216"+tel_fld.getText(), hexString.toString(), "Mourouj", 0, 0, "admin", "20000", "active", "ss","vide");
            UserService userS = new UserService();
            if(userS.readAll().stream().anyMatch(us->us.getEmail().equals(email_fld.getText())))
                AlerteFinFolio.alerte("exist");
            else {
                userS.add(user);
                AlerteFinFolio.alerteSucces("Le compte d'admin a été crée avec succès","Creation du compte ");}


        }

    }

}
