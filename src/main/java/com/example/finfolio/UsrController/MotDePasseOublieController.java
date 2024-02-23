package com.example.finfolio.UsrController;

import Models.Model;
import Views.AlerteFinFolio;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import com.google.zxing.WriterException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;



public class MotDePasseOublieController implements Initializable {
    public TextField codeEntered;
    @FXML
    private Label PasswordError;

    @FXML
    private Button UpdateBtn;

    @FXML
    private Label emailError;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passField1;

    @FXML
    private TextField passField2;

    @FXML
    private Button recoverPasswordBtn;

    @FXML
    private Button upadtePassword;
    private  String recoveryCode="";


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
            }
        });
    }

    public void handleRecoverPassword() throws SQLException, IOException, WriterException {
        UserService userService = new UserService();
        if (userService.readAll().stream().anyMatch(u->u.getEmail().equals(emailField.getText()))) {
            emailError.setText("");
            EmailingApi e = new EmailingApi();
            recoveryCode = generateRecoveryCode(5);
            QRCodeApi qr=new QRCodeApi();
            qr.GenereQrCode(recoveryCode);
            e.sendEmailWithAttachment(emailField.getText(),"Authentification","Scanner le QrCode", QRCodeApi.getPath());

            codeEntered.setVisible(true);
            UpdateBtn.setVisible(true);
        } else {
            emailError.setText("Utilisateur n'existe pas");
        }
    }

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
        public void updatePassword() throws SQLException, IOException {
            if (!(passField1.getText().equals(passField2.getText()))) {
                emailError.setText("Mots de passes ne sont pas identiques");

            }
            else{
                emailError.setText("");
                UserService userService = new UserService();

                User currentUser = userService.getUserByEmail(emailField.getText());
                currentUser.setPassword(passField2.getText());
                userService.update(currentUser);
                AlerteFinFolio.alerteSucces("Votre mot de passe a été changé avec succès");
                Stage st = (Stage) emailField.getScene().getWindow();
                Model.getInstance().getViewFactory().closeStage(st);
                Model.getInstance().getViewFactory().showLoginWindow();
            }
        }

    }




