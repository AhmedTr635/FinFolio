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
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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
    }
public void OnInscription()
{
    Stage st = (Stage) error_label.getScene().getWindow();
    Model.getInstance().getViewFactory().closeStage(st);
    Model.getInstance().getViewFactory().showSignUpWindow();

}
    public void onLogin() throws NoSuchAlgorithmException, SQLException {


        UserService userS = new UserService();
        String enteredCaptcha = captchaField.getText();
        if (enteredCaptcha.equals(captchaCode)) {
            User user = userS.getUserByEmail(mail_field.getText());
            if (user != null) {
                Model.getInstance().setUser(user);


                if (user.getPassword().equals(mot_de_passe_field.getText())) {

                    switch (user.getRole()) {
                        case "user":
                            Stage st = (Stage) error_label.getScene().getWindow();
                            Model.getInstance().getViewFactory().closeStage(st);
                            Model.getInstance().getViewFactory().showUserWindow();


                            break;
                        case "admin":
                            Stage st2 = (Stage) error_label.getScene().getWindow();
                            Model.getInstance().getViewFactory().closeStage(st2);
                            Model.getInstance().getViewFactory().showAdminWindow();


                            break;


                    }
                } else {
                   AlerteFinFolio.alerte("");
                }

            } else {


                AlerteFinFolio.alerte("bd");
            }
        } else if (!(enteredCaptcha.equals(captchaCode))) {
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


        }
    }
    /*QSqlQueryModel * Client::recherche_par_cin(QString val)
    {
        QSqlQueryModel * model=new QSqlQueryModel;
        model->setQuery("SELECT * FROM G_CLIENT where CIN_C Like '%"+val+"%' or NOM_C Like '%"+val+"%' or PRENOM_C Like '%"+val+"%'");
        return model;
    }*/


}