package com.example.finfolio.Depense;

import Models.Model;
import com.example.finfolio.Entite.Depense;
import com.example.finfolio.Entite.Tax;
import com.example.finfolio.Service.DepenseService;
import com.example.finfolio.Service.TaxService;
import com.example.finfolio.Service.UserService;
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

public class depenseModifyContoller implements Initializable {
    @FXML
    public Button Modifier;
    @FXML

    public Button btnAnnuler;
    @FXML

    public TextField txtType;
    @FXML

    public TextField txtMontant;
    @FXML

    public Label montantErrorLabel;
    @FXML

    public Label typeErrorLabel;
    @FXML

    public DatePicker txtDate;
    @FXML

    public Label dateErrorLabel;

    @FXML
    public TextField type;



    @FXML
    public TextField montant;
    @FXML
    public Label iddep;



    // Depense object to hold the data
    private Depense depense;
    private DepenseValidation validation;




    public Depense getDepense() {
        return depense;
    }




    public void setDepense(Depense depense) {
        this.depense = depense;
    }


    //initialization
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    validation();
        Modifier.setOnAction(event -> {
            if (validation.isAllValid()) {

                // Call service method to add data
                try {
                    onClickModifier();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    // Method to initialize data
    public void initData(Depense depense) {
        try{
            iddep.setText(Double.toString(depense.getId()));

            txtMontant.setText(Double.toString(depense.getMontant()));
            // Populate fields with data from the Depense object
            txtType.setText(depense.getType());
            txtDate.setValue(depense.getDate());

        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    // Method to handle modification and close the window


    public void onClickAnnuler(ActionEvent actionEvent) {
        Stage stage = (Stage) txtType.getScene().getWindow();

        stage.close();
    }


    public void onClickModifier() throws SQLException {
        DepenseService depenseService = new DepenseService();

        int iddepense = (int) Double.parseDouble(iddep.getText());
        Depense dep=depenseService.readById(iddepense);
        String type = txtType.getText();
        float montant = (float) Double.parseDouble(txtMontant.getText()); // Assuming montant is a numeric value
        LocalDate selectedDate = txtDate.getValue();
        UserService us =new UserService();
        double montantTax=    montant*0.14;
        int taxid=dep.getTax().getId();
        Tax t =new Tax (taxid,montantTax,"depense","jdjd");
        TaxService ts =new TaxService();
        Depense depa = new Depense(ts.readById(taxid),selectedDate,type, montant, Model.getInstance().getUser());
        ts.update(t, taxid);
        // Call a method in your service class to add the data to the database
        depenseService.update(depa,this.depense.getId());
        Stage stage = (Stage) Modifier.getScene().getWindow();
        stage.close();


    }
    public void validation(){
        validation = new DepenseValidation(txtDate, txtMontant, txtType, dateErrorLabel, montantErrorLabel, typeErrorLabel, Modifier);

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
