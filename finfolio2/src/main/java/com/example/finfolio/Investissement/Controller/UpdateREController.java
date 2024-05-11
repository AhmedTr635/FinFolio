package com.example.finfolio.Investissement.Controller;

import com.example.finfolio.Investissement.Entite.Investissement;
import com.example.finfolio.Investissement.Entite.RealEstate;
import com.example.finfolio.Investissement.Service.InvestissementService;
import com.example.finfolio.Investissement.Service.RealEstateService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class UpdateREController {

    @FXML
    private Label Roi;
    private Investissement invest;


    @FXML
    private Button ajoutInvRE;

    @FXML
    private TextField montantInv;

    @FXML
    private Label totalGain;

    @FXML
    private Label nom;

    @FXML
    private Label superficie;

    @FXML
    private Label valeur;

    @FXML
    private ImageView variable;
    InvestissementService investissementService = new InvestissementService();

    @FXML
    void UpdateInv(ActionEvent event) {
        if(montantInv.getText().isEmpty()){
            montantInv.setStyle("-fx-border-color: red");
        }
        else{
            montantInv.setStyle("-fx-border-color: none");
            if(Double.parseDouble(montantInv.getText())<invest.getMontant()){
                showAlert("Attention","Attention","Attention si vous allez vendre vous allez vendre avec le montant initiale");
                //invest.getUser().setSolde(invest.getUser().getSolde()+invest.getMontant()-Double.parseDouble(montantInv.getText()));
                /*invest.setMontant(Double.parseDouble(montantInv.getText()));
                invest.setDateAchat(LocalDate.now());
                investissementService.update(invest,invest.getId());*/
                Stage stage=(Stage) ajoutInvRE.getScene().getWindow();
                stage.close();

            }
            //invest.getUser().setSolde(invest.getUser().getSolde()+invest.getMontant()-Double.parseDouble(montantInv.getText()));
            invest.setMontant(Double.parseDouble(montantInv.getText()));
            invest.setDateAchat(LocalDate.now());
            investissementService.update(invest,invest.getId());
            Stage stage=(Stage) ajoutInvRE.getScene().getWindow();
            stage.close();

        }


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
    /*@FXML
    public void initialize() {

    }*/
    RealEstateService realEstateService = new RealEstateService();
    public void setData(Investissement inv) {
        invest=inv;
        RealEstate re =inv.getRe();
        nom.setText(re.getEmplacement());
        totalGain.setText(String.valueOf((inv.getMontant()*re.getROI()/100)*monthsBetween(inv.getDateAchat(), LocalDate.now())));
        Roi.setText(String.valueOf(re.getROI()));
        valeur.setText(String.valueOf(re.getValeur()));
        superficie.setText(String.valueOf(re.getSuperficie()));
        montantInv.setText(String.valueOf(inv.getMontant()));

    }
    public static long monthsBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            // Swap the dates if the first date is after the second date
            LocalDate temp = startDate;
            startDate = endDate;
            endDate = temp;
        }

        long years = ChronoUnit.YEARS.between(startDate, endDate);
        long months = ChronoUnit.MONTHS.between(startDate.plusYears(years), endDate);

        return years * 12 + months;
    }

}

