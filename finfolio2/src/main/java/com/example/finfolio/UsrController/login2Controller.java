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
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
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
    @FXML
    private ImageView imgCaptcha;

    private String captchaCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Canvas captchaCanvas = new Canvas(150, 70);
        captchaCode = CaptchaLogin.generateCaptchaCode(6);
        GraphicsContext gc = captchaCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, captchaCanvas.getWidth(), captchaCanvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 24));
        gc.fillText(captchaCode, 10, 35);
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
    Model.getInstance().getViewFactory().showMotDepasseOublieWindow();


}
    public void onLogin() throws NoSuchAlgorithmException, SQLException {

       /*Stage st2 = (Stage) error_label.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(st2);
        UserService userS = new UserService();

        User u1= userS.getUserByEmail("trabelsi.dali@esprit.tn");
        Model.getInstance().setUser(u1);
        //Model.getInstance().getViewFactory().showUserWindow();
        Model.getInstance().getViewFactory().showAdminWindow();
*/
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
                    } else
                        AlerteFinFolio.alerte("");


                }

                else{
                    if (!user.getDatepunition().equals("vide"))
                    {    dateString=user.getDatepunition();
                        System.out.println(dateString);
                         dateFromString = LocalDate.parse(dateString);
                        System.out.println(dateFromString);

                        // Objet LocalDate local
                        LocalDate currentDate = LocalDate.now();
                        System.out.println(currentDate);

                        // Comparaison des dates
                        if (dateFromString.isBefore(currentDate)) {
                            user.setStatut("active");
                            user.setPassword(user.getPassword());
                            user.setDatepunition("vide");
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

         else if (!(enteredCaptcha.equals(captchaCode))) {
            Canvas captchaCanvass = new Canvas(150, 70);
            captchaCode = CaptchaLogin.generateCaptchaCode(6);
            GraphicsContext gc = captchaCanvass.getGraphicsContext2D();
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, captchaCanvass.getWidth(), captchaCanvass.getHeight());
            gc.setFill(Color.BLACK);
            gc.setFont(new Font("Arial", 30));
            gc.fillText(captchaCode, 10, 35);
            captchaField.clear();
            imgCaptcha.setStyle("-fx-border-color: black;-fx-border-width: 1px;");
            imgCaptcha.setImage(captchaCanvass.snapshot(null, null));
            capcthaError.setText("Code incorrecte");


        }        //*/
    }



}