package Views;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

public class AlerteFinFolio {
    public static Optional<ButtonType> alerte(String  string)
    {       Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(String.valueOf(AlerteFinFolio.class.getResource("/com/example/finfolio/Pics/icon.png"))));
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            if (string=="bd")
                alert.setContentText("Votre compte est introuvable  ");
            else
                if (string=="exist")
                    alert.setContentText("Ce compte existe");

                else
                    if (string.equals("desactive"))
                        alert.setContentText("Votre compte est desactive");
                    else
                alert.setContentText("Informations érronées");
            return alert.showAndWait();}
    public static Optional<ButtonType> alerteSucces(String  string,String title){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(String.valueOf(AlerteFinFolio.class.getResource("/com/example/finfolio/Pics/icon.png"))));
        alert.setTitle(title);
        alert.setHeaderText(null);
            alert.setContentText(string);
        return alert.showAndWait();
    }
    public static Optional<ButtonType> alertechoix(String  string,String title){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(String.valueOf(AlerteFinFolio.class.getResource("/com/example/finfolio/Pics/icon.png"))));
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(string);
        return alert.showAndWait();
    }
    }


