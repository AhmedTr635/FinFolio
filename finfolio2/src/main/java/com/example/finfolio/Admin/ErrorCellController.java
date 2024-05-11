package com.example.finfolio.Admin;

import com.example.finfolio.Service.ErrorService;
import com.example.finfolio.UsrController.EmailingApi;
import com.example.finfolio.Entite.Error;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ErrorCellController {
    @FXML
    private Button confirmer_id;
    @FXML
    private Label id;

    @FXML
    private Label user_id;

    @FXML
    private Label user_name;

    @FXML
    private Button show_id;
    @FXML
    private Label error_date;




    @FXML
    void showproblem(ActionEvent event) {
        Error error = ErrorService.getInstance().fetchErrorByUserId(Integer.parseInt(user_id.getText()));

        try {


            FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/finfolio/Admin/ProblemCell.fxml"));
            Parent root=loader.load();
            ProblemCellController Controller=loader.getController();
            Controller.setData(error);
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show(); }
        catch (IOException e){throw new RuntimeException(e);}


    }

    public void setErrorData(Error error) {
        id.setText(String.valueOf(error.getId()));
        user_name.setText(error.getUser().getNom());
        user_id.setText(String.valueOf(error.getUser().getId()));
        error_date.setText(error.getTimestamp().toString());


    }

    @FXML
    void senEmail(ActionEvent event) throws SQLException {
        EmailingApi em=new EmailingApi();
        Error error = ErrorService.getInstance().fetchErrorByUserId(Integer.parseInt(user_id.getText()));
        em.sendProbleme(error.getUser().getEmail(), "Votre reclamation va etre prise en consideration");
        System.out.println("email success");


    }


}
