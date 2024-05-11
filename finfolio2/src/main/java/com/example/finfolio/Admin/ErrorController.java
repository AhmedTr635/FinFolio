package com.example.finfolio.Admin;

import com.example.finfolio.Service.ErrorService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import com.example.finfolio.Entite.Error;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ErrorController {


    @FXML
    private VBox error_container;


    @FXML
    void initialize() throws SQLException {


        loadErrors();

    }
    private void loadErrors() throws SQLException {

        List<Error> errors = ErrorService.getInstance().readAll();

        for (Error error : errors) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/finfolio/Admin/ErreurCell.fxml"));
                AnchorPane errorCell = fxmlLoader.load();

                ErrorCellController controller = fxmlLoader.getController();
                controller.setErrorData(error);


                error_container.getChildren().addAll(errorCell);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }}


}
