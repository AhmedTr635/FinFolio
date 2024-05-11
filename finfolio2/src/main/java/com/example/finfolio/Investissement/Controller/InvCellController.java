package com.example.finfolio.Investissement.Controller;

import com.example.finfolio.Investissement.Entite.Investissement;
import com.example.finfolio.Investissement.Service.InvestissementService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class InvCellController {

    public Button modifier;
    public Button deleteInv;
    public Investissement investissement;
    private InvestissementService investissementService=new InvestissementService();
    @FXML
    private AnchorPane BigRECell;

    @FXML
    private Label Roi;

    @FXML
    private Label dateAchat;

    @FXML
    private Button delete;

    @FXML
    private Label montant;

    @FXML
    private Label prixAchat;

    @FXML
    private Label re;

    @FXML
    private Label tax;

    @FXML
    private Button updateInv;

    @FXML
    void deleteInv(MouseEvent event) {
        //showAlert("Investissement supprimé", "Investissement supprimé avec succès");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(("Investissement supprimé"));
        alert.setHeaderText(("Investissement supprimé"));
        alert.setContentText("Voulez vous vraiment supprimé ?");

        // Show the alert and wait for user interaction
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("OK button clicked");
                alert.hide(); // Hide the alert when the OK button is clicked
                investissementService.delete(investissement);
                InvestissementUserController investissementUserController = new InvestissementUserController();
                /*investissementUserController.refresh();*/

            }
        });


    }
    public static void showAlert(String title, String headerText, String contentText) {
        // Create an alert with ERROR type
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        // Show the alert and wait for user interaction
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("OK button clicked");
                alert.hide(); // Hide the alert when the OK button is clicked
            }
        });
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void updateInv(MouseEvent event) {
        try {
            // Load the UpdateRE.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Investissement/UpdateRE.fxml"));
            AnchorPane updateREPane = loader.load();
            UpdateREController updateREController = loader.getController();
            updateREController.setData(investissement);
            InvestissementUserController investissementUserController = new InvestissementUserController();
            /*investissementUserController.refresh();*/

            // Create a dialog to display the UpdateRE pane
            Dialog<Void> dialog = new Dialog<>();
            dialog.getDialogPane().setContent(updateREPane);

            // Show the dialog
            dialog.showAndWait();
            dialog.setOnHidden(e -> dialog.close());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void setData(Investissement investissement) {
        this.investissement = investissement;

        this.Roi.setText("ROI : "+String.valueOf(investissement.getROI()));
        this.dateAchat.setText("Date Achat : "+investissement.getDateAchat().toString());
        this.montant.setText("Montant : "+String.valueOf(investissement.getMontant()));
        this.prixAchat.setText("Prix Achat : "+String.valueOf(investissement.getPrixAchat()));
        //this.re.setText("Emplacement: "+investissement.getRe().getEmplacement());
        this.tax.setText("Tax : "+String.valueOf(investissement.getTax()));
    }

}
