package com.example.gestioncredit1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class appCredit extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(appCredit.class.getResource("ajouterCredit.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),906,550);
        stage.setTitle("gestionCredit");


        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}