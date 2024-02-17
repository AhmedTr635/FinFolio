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
                alert.setContentText("compte Introuvable or desactivé ");
            else
                if (string=="exist")
                    alert.setContentText("compte existe");

                else
                alert.setContentText("Informations érronées");
            return alert.showAndWait();}
}
