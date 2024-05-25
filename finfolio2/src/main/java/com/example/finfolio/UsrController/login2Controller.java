package com.example.finfolio.UsrController;

import Models.Model;
import Views.AlerteFinFolio;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class login2Controller implements Initializable {
    public Label mail_label;
    public TextField mail_field;
    public PasswordField mot_de_passe_field;
    public Button login_btn;
    public Label error_label;
    public TextField captchaField;
    public Label capcthaError;

    public Button inscri_button;
    public Button mdp_btn;
    public Label mailError;
    @FXML
    private ImageView imgCaptcha;

    private String captchaCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //controle de saisie
        addInputControlListener(mail_field, mailError, "Email");

        mdp_btn.setVisible(false);
        Canvas captchaCanvas = new Canvas(200, 100); // Keep the canvas size consistent
        captchaCode = CaptchaLogin.generateCaptchaCode(6);
        GraphicsContext gc = captchaCanvas.getGraphicsContext2D();

        // Set background to white
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, captchaCanvas.getWidth(), captchaCanvas.getHeight());

        // Add noise (random lines)
        Random random = new Random();
        gc.setStroke(Color.LIGHTBLUE); // Adjust noise color to match the interface
        for (int i = 0; i < 10; i++) {
            gc.strokeLine(random.nextDouble() * captchaCanvas.getWidth(), random.nextDouble() * captchaCanvas.getHeight(),
                    random.nextDouble() * captchaCanvas.getWidth(), random.nextDouble() * captchaCanvas.getHeight());
        }

        // Randomize font attributes
        gc.setFill(Color.DARKBLUE); // Adjust font color to match the interface
        gc.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 30 + random.nextInt(10)));

        // Add captcha code with slight distortion
        for (int i = 0; i < captchaCode.length(); i++) {
            String ch = String.valueOf(captchaCode.charAt(i));
            gc.save();
            gc.translate(20 + i * 30 + random.nextInt(10), 50 + random.nextInt(20)); // Randomize position slightly
            gc.rotate(random.nextInt(10) - 5); // Rotate slightly
            gc.fillText(ch, 0, 0);
            gc.restore();
        }

        captchaField.clear();
        imgCaptcha.setImage(captchaCanvas.snapshot(null, null));

        login_btn.setOnAction(e -> {
            try {
                onLogin();
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        inscri_button.setOnAction(e->OnInscription());
        mdp_btn.setOnAction(e->mdpOublie());
    }
public void OnInscription()
{
    Stage st = (Stage) error_label.getScene().getWindow();
    Model.getInstance().getViewFactory().closeStage(st);
    Model.getInstance().getViewFactory().showSignUpWindow();

}
public void mdpOublie()
{
    Stage st = (Stage) error_label.getScene().getWindow();
    Model.getInstance().getViewFactory().closeStage(st);
    Model.getInstance().getViewFactory().showModeWindow();

}
    public void onLogin() throws NoSuchAlgorithmException, SQLException {

       /*Stage st2 = (Stage) error_label.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(st2);
        UserService userS = new UserService();


        //Model.getInstance().getViewFactory().showUserWindow();
        Model.getInstance().getViewFactory().showAdminWindow();*/

       if(validateEmailField(mail_field,mailError)){
       UserService userS = new UserService();
       String dateString;
       LocalDate dateFromString;
        String enteredCaptcha = captchaField.getText();
        if (enteredCaptcha.equals(captchaCode)) {
            User user = userS.getUserByEmail(mail_field.getText());
            if (user != null) {
                if (!user.getStatut().equals("desactive")) {
                    MessageDigest digest = MessageDigest.getInstance("SHA-1");
                    byte[] hash = digest.digest(mot_de_passe_field.getText().getBytes());
                    StringBuilder hexString = new StringBuilder();
                    for (byte b : hash) {
                        String hex = Integer.toHexString(0xff & b);
                        if (hex.length() == 1) {
                            hexString.append('0');
                        }
                        hexString.append(hex);
                    }


                    if (user.getPassword().equals(hexString.toString())) {

                        Model.getInstance().setUser(user);

                        switch (user.getRole()) {
                            case "user":
                                if(user.getStatut().equals("active"))
                                {Stage st = (Stage) error_label.getScene().getWindow();
                                Model.getInstance().getViewFactory().closeStage(st);
                                Model.getInstance().getViewFactory().showUserWindow();}
                                else {
                                } if(user.getStatut().equals("ban")){
                                Stage stb = (Stage) error_label.getScene().getWindow();
                                Model.getInstance().getViewFactory().closeStage(stb);
                                Model.getInstance().getViewFactory().showUserBanned();
                                AlerteFinFolio.alerteSucces("Vous avez un ban ,vous ne pouvez pas acceder aux investissements , evenements et profile","payement des credits");


                            }




                                break;
                            case "admin":
                                Stage st2 = (Stage) error_label.getScene().getWindow();
                                Model.getInstance().getViewFactory().closeStage(st2);
                                Model.getInstance().getViewFactory().showAdminWindow();


                                break;


                        }
                    } else{
                        AlerteFinFolio.alerte("");
                    mdp_btn.setVisible(true);}



                }

                else{
                    if (user.getDatepunition() != null)
                    {    dateFromString=user.getDatepunition();
                        System.out.println(dateFromString);
                        // dateFromString = LocalDate.parse(dateString);
                        //System.out.println(dateFromString);

                        // Objet LocalDate local
                        LocalDate currentDate = LocalDate.now();
                        System.out.println(currentDate);

                        // Comparaison des dates
                        if (dateFromString.isBefore(currentDate)) {
                            user.setStatut("active");
                            user.setPassword(user.getPassword());
                            user.setDatepunition(null);
                            userS.update(user);
                           Model.getInstance().getUser().setStatut("active");
                            AlerteFinFolio.alerteSucces("Votre compte n'est plus desactive","Staut du compte");

                        }
                        else  {
                            AlerteFinFolio.alerte("desactive");
                            System.out.println("la date de desactivation mezelet");}
                    }


                    //AlerteFinFolio.alerte("desactive");
                    }
            } else


                AlerteFinFolio.alerte("bd");
        }

         else {
            Canvas captchaCanvas = new Canvas(200, 100); // Keep the canvas size consistent
            captchaCode = CaptchaLogin.generateCaptchaCode(6);
            GraphicsContext gc = captchaCanvas.getGraphicsContext2D();

            // Set background to white
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, captchaCanvas.getWidth(), captchaCanvas.getHeight());

            // Add noise (random lines)
            Random random = new Random();
            gc.setStroke(Color.LIGHTBLUE); // Adjust noise color to match the interface
            for (int i = 0; i < 10; i++) {
                gc.strokeLine(random.nextDouble() * captchaCanvas.getWidth(), random.nextDouble() * captchaCanvas.getHeight(),
                        random.nextDouble() * captchaCanvas.getWidth(), random.nextDouble() * captchaCanvas.getHeight());
            }

            // Randomize font attributes
            gc.setFill(Color.DARKBLUE); // Adjust font color to match the interface
            gc.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 30 + random.nextInt(10)));

            // Add captcha code with slight distortion
            for (int i = 0; i < captchaCode.length(); i++) {
                String ch = String.valueOf(captchaCode.charAt(i));
                gc.save();
                gc.translate(20 + i * 30 + random.nextInt(10), 50 + random.nextInt(20)); // Randomize position slightly
                gc.rotate(random.nextInt(10) - 5); // Rotate slightly
                gc.fillText(ch, 0, 0);
                gc.restore();
            }

            captchaField.clear();
            imgCaptcha.setImage(captchaCanvas.snapshot(null, null));
            capcthaError.setText("Code Incorrecte");


        } }//*/
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
    private void addInputControlListener(TextField textField, Label errorLabel, String fieldName) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = true; // Initialise la validation à true


             if (textField == mail_field)
                isValid = validateEmailField(textField, errorLabel);

            if (!isValid) {
                errorLabel.getStyleClass().add("error-label");
            } else {
                errorLabel.getStyleClass().removeAll("error-label");
            }
        });
    }

}