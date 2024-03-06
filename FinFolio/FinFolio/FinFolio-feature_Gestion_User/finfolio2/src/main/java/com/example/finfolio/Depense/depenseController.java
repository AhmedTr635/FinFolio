package com.example.finfolio.Depense;


import Models.Model;
import com.example.finfolio.Entite.Depense;
import com.example.finfolio.Entite.Tax;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.DepenseService;
import com.example.finfolio.Service.TaxService;
import com.example.finfolio.Service.UserService;
import com.example.finfolio.UsrController.DashboardController;
import com.google.zxing.qrcode.decoder.Mode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class depenseController  implements Initializable {




    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAnnuler;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtMontant;

    @FXML
    private TextField txtType;

    @FXML
    private Label dateErrorLabel;

    @FXML
    private Label montantErrorLabel;

    @FXML
    private Label typeErrorLabel;

    private DepenseValidation validation;



    //initialisation de l'ajout depense
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        validation();
        btnAdd.setOnAction(event -> {
            if (validation.isAllValid()) {

                // Call service method to add data
                try {
                    onClickAdd();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    //ajouter depense
    @FXML
    void onClickAdd() throws SQLException {

        String type = txtType.getText();
        float montant = (float) Double.parseDouble(txtMontant.getText()); // Assuming montant is a numeric value
        LocalDate selectedDate = txtDate.getValue();
        double montantTax=    montant*0.14;
        Tax t=new Tax(montantTax,"depense","opt");
        TaxService ts =new TaxService();
        int taxId = ts.addAndGetId(t); // Assuming addAndGetId returns the auto-generated ID after insertion


        // Check if a date is selected
        Depense dep = new Depense(ts.readById(taxId), selectedDate,type,  montant, Model.getInstance().getUser());
        // Call a method in your service class to add the data to the database
        DepenseService depenseService = new DepenseService();

        depenseService.add(dep);

        Model.getInstance().getUser().setTotal_tax( Tax.calculateTotalTax(ts.readAll()));
        UserService us =new UserService();
        us.updatewTax( Model.getInstance().getUser());
        Stage stage = (Stage) btnAdd.getScene().getWindow();

        stage.close();
    }


    @FXML
    void onClickAnnuler(ActionEvent event) {
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();


        stage.close();
    }

    //Validation du formulaire d'ajout dépense
    public void validation (){
        validation = new DepenseValidation(txtDate, txtMontant, txtType, dateErrorLabel, montantErrorLabel, typeErrorLabel, btnAdd);

        txtDate.valueProperty().addListener((ov, oldValue, newValue) -> {
            validation.validateDate(newValue);
        });

        txtMontant.textProperty().addListener((ov, oldValue, newValue) -> {

            validation.validateMontant(newValue);
        });

        txtType.textProperty().addListener((ov, oldValue, newValue) -> {
            validation.validateType(newValue);
        });
    }
}

