package com.example.finfolio.UsrController;

import Models.Model;
import Views.AlerteFinFolio;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import com.google.zxing.WriterException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class SMSController implements Initializable {
    public TextField emailField;
    public Label emailError;
    
    public Button recoverPasswordBtn;
    public Label PasswordError;
    public Button UpdateBtn;
    public PasswordField passField1;
    public PasswordField passField2;
    public Button upadtePassword;
    public TextField codeEntered;
    public Label mailerror;
    public Label errormdc;
    private  String recoveryCode;


    public  String generateRecoveryCode(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //controle de saisie
        addInputControlListener(emailField, emailError, "Email");
        addInputControlListenerPassword(passField1,emailError,"");
        codeEntered.setVisible(false);
        UpdateBtn.setVisible(false);
        passField1.setVisible(false);
        passField2.setVisible(false);
        upadtePassword.setVisible(false);
        recoverPasswordBtn.setOnAction(e-> {
            try {
                handleRecoverPassword();
            } catch (SQLException | IOException | WriterException ex) {
                ex.printStackTrace();
            }
        });
        UpdateBtn.setOnAction(e-> {
            try {
                handleUpdateButton();
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        upadtePassword.setOnAction(e-> {
            try {
                updatePassword();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void handleRecoverPassword() throws SQLException, IOException, WriterException {
        if(validateEmailField(emailField,emailError))
        {UserService userService = new UserService();
            if (userService.readAll().stream().anyMatch(u->u.getEmail().equals(emailField.getText()))) {
                User u=userService.getUserByEmail(emailField.getText());
                String sous_chaine = u.getNumtel().substring(8, 12);
                AlerteFinFolio.alerteSucces("Un code est envoyé à ****"+sous_chaine,"Récupération par SMS");
                emailError.setText("");
               TwilioAPI tp=new TwilioAPI();
                recoveryCode = generateRecoveryCode(5);
                tp.sendSMS(u.getNumtel(),recoveryCode);
                recoveryCode = generateRecoveryCode(5);
                System.out.println(recoveryCode);
                codeEntered.setVisible(true);
                UpdateBtn.setVisible(true);
            } else {
                emailError.setText("Utilisateur n'existe pas");
            }
        }}

    public void handleUpdateButton() throws NoSuchAlgorithmException, SQLException, IOException {
        if (!(codeEntered.getText().equals(recoveryCode))) {
            PasswordError.setText("Code Invalide");
        }else {
            PasswordError.setText("");
            recoverPasswordBtn.setVisible(false);
            codeEntered.setVisible(false);
            UpdateBtn.setVisible(false);
            emailField.setVisible(false);
            passField1.setVisible(true);
            passField2.setVisible(true);
            upadtePassword.setVisible(true);

        }
    }
    public void updatePassword() throws SQLException, IOException, NoSuchAlgorithmException {
        if (validatePassword(passField1,emailError))
        {

            if (!(passField1.getText().equals(passField2.getText()))) {
                errormdc.setText("Mots de passes ne sont pas identiques");

            }
            else{

                emailError.setText("");
                UserService userService = new UserService();
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                byte[] hash = digest.digest(passField1.getText().getBytes());
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }
                User currentUser = userService.getUserByEmail(emailField.getText());
                currentUser.setPassword(hexString.toString());
                userService.update(currentUser);
                AlerteFinFolio.alerteSucces("Votre mot de passe a été changé avec succès","Changement du mot de passe");
                Stage st = (Stage) emailField.getScene().getWindow();
                Model.getInstance().getViewFactory().closeStage(st);
                Model.getInstance().getViewFactory().showLoginWindow();
            }
        }}
    private void addInputControlListener(TextField textField, Label errorLabel, String fieldName) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = true; // Initialise la validation à true


            if (textField == emailField)
                isValid = validateEmailField(textField, errorLabel);

            if (!isValid) {
                errorLabel.getStyleClass().add("error-label");
            } else {
                errorLabel.getStyleClass().removeAll("error-label");
            }
        });
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
    private void addInputControlListenerPassword(TextField textField, Label errorLabel, String fieldName) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = true; // Initialise la validation à true


            if (textField == passField1)
                isValid = validatePassword((PasswordField) textField, errorLabel);

            if (!isValid) {
                errorLabel.getStyleClass().add("error-label");
            } else {
                errorLabel.getStyleClass().removeAll("error-label");
            }
        });
    }
}

