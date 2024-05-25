package com.example.finfolio.UsrController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Models.Model;
import Views.AlerteFinFolio;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SignUpController  implements Initializable {


    public Button retour_btn;
    @FXML
    private Button engistrez_fld;

    @FXML
    private Label error_mail;

    @FXML
    private Label error_mdp;

    @FXML
    private Label error_nom;

    @FXML
    private Label error_ntel;

    @FXML
    private Label error_prenom;

    @FXML
    private TextField mail_fld;

    @FXML
    private PasswordField modp_field;

    @FXML
    private TextField nom_fld;

    @FXML
    private TextField numTelfld;

    @FXML
    private TextField prenom_fld;
    private String imagePath = "C:\\Users\\PC\\Desktop\\PI\\finfolio2\\src\\main\\resources\\com\\example\\finfolio\\Pics\\simpleUr.png";
    @FXML
    private Button importImageBtn;







    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addInputControlListener(nom_fld, error_nom, "Nom");
        addInputControlListener(prenom_fld, error_prenom, "Prénom");
        addInputControlListener(mail_fld, error_mail, "Email");
        addInputControlListener(numTelfld, error_ntel, "Numéro de téléphone");
        addInputControlListener(modp_field, error_mdp, "Mot de passe");


        importImageBtn.setOnAction(e-> {
            try {
                onImporter();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        engistrez_fld.setOnAction(e -> {
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

        retour_btn.setOnAction(e-> {
            try {
                retour();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


    }
    private void onImporter() throws IOException {

        String selectedImagePath = importImage();

        // If an image is selected, update the imagePath
        if (selectedImagePath != null)
            imagePath = selectedImagePath;


    }

    void onEngistrez() throws SQLException, IOException, NoSuchAlgorithmException {
        //String imagePath = null;

        if (inputcontrol()) {

            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(modp_field.getText().getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Insert user data along with the image path into the database
            LocalDate localDate = LocalDate.of(1111, 11, 11);

            User user = new User(nom_fld.getText(), prenom_fld.getText(), mail_fld.getText(), "+216"+numTelfld.getText(), hexString.toString(), "Mourouj", 0, 2, "user", "20000", "active", imagePath,localDate);
            UserService userS = new UserService();
            if(userS.readAll().stream().anyMatch(us->us.getEmail().equals(mail_fld.getText())))
                AlerteFinFolio.alerte("exist");
            else {
            userS.add(user);
            AlerteFinFolio.alerteSucces("Votre compte a été crée avec succès","Creation du compte ");
                Stage st = (Stage) error_mdp.getScene().getWindow();
                Model.getInstance().getViewFactory().closeStage(st);
                Model.getInstance().setUser(user);
                Model.getInstance().getViewFactory().showUserWindow();
                Model.getInstance().setUser(user);
            }


        }

    }
    private void retour() throws IOException {
        Stage st = (Stage) error_mdp.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(st);
        Model.getInstance().getViewFactory().showLoginWindow();


    }
    private void addInputControlListener(TextField textField, Label errorLabel, String fieldName) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = true; // Initialise la validation à true

            // Vérifie chaque champ séparément
            if (textField == nom_fld) {
                isValid = validateField(textField, errorLabel, "Nom", 3, 32);
            } else if (textField == prenom_fld) {
                isValid = validateField(textField, errorLabel, "Prénom", 3, 32);
            } else if (textField == mail_fld) {
                isValid = validateEmailField(textField, errorLabel);
            } else if (textField == numTelfld) {
                isValid = validateNumTelField(textField, errorLabel);
            } else if (textField == modp_field) {
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
        isValid &= validateEmailField(mail_fld, error_mail);
        isValid &= validateNumTelField(numTelfld, error_ntel);
        isValid &= validatePassword((PasswordField) modp_field, error_mdp);
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


}