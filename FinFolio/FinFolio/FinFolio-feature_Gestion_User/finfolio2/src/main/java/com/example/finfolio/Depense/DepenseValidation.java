package com.example.finfolio.Depense;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DepenseValidation {
    private final DatePicker datePicker;
    private final TextField montantField;
    private final TextField typeField;
    private final Label dateErrorLabel;
    private final Label montantErrorLabel;
    private final Label typeErrorLabel;
    private final Button addButton;


    public DepenseValidation(DatePicker datePicker, TextField montantField, TextField typeField, Label dateErrorLabel, Label montantErrorLabel, Label typeErrorLabel, Button addButton) {
        this.datePicker = datePicker;
        this.montantField = montantField;
        this.typeField = typeField;
        this.dateErrorLabel = dateErrorLabel;
        this.montantErrorLabel = montantErrorLabel;
        this.typeErrorLabel = typeErrorLabel;
        this.addButton = addButton;
    }

    public void validateDate(LocalDate date) {
        if (date == null ) {
            dateErrorLabel.setText("Entrer une date.");
            disableButton();
        } else {
            dateErrorLabel.setText("");
            enableButton();
        }
    }

    public void validateMontant(String montantStr) {
        if (montantStr == null || montantStr.isEmpty()) {
            montantErrorLabel.setText("Entrer un montant ");
            disableButton();
        } else {
            try {
                Double.parseDouble(montantStr);
                montantErrorLabel.setText("");
                enableButton();
            } catch (NumberFormatException e) {
                montantErrorLabel.setText("utiliser que des chiffres.");
                disableButton();
            }
        }
    }

    public void validateType(String typeStr) {
        if (typeStr == null || typeStr.isEmpty()) {
            typeErrorLabel.setText("Entrer un type .");
            disableButton();
        } else {
            typeErrorLabel.setText("");
            enableButton();
        }
    }

    public boolean isAllValid() {
        validateDate(datePicker.getValue());
        validateMontant(montantField.getText());
        validateType(typeField.getText());
        return dateErrorLabel.getText().isEmpty() && montantErrorLabel.getText().isEmpty() && typeErrorLabel.getText().isEmpty();

    }

    private void clearErrorMessages() {
        dateErrorLabel.setText("");
        montantErrorLabel.setText("");
        typeErrorLabel.setText("");
    }

    private void disableButton() {
        addButton.setDisable(true);
    }

    private void enableButton() {
        addButton.setDisable(false);
    }
}
