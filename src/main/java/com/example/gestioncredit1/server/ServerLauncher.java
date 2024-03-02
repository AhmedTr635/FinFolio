package com.example.gestioncredit1.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ServerLauncher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL serverFormUrl = getClass().getResource("/com/example/gestioncredit1/ServerForm.fxml");
        if (serverFormUrl == null) {
            System.err.println("ServerForm.fxml not found");
            return;
        }

        URL loginFormUrl = getClass().getResource("/com/example/gestioncredit1/LoginForm.fxml");
        if (loginFormUrl == null) {
            System.err.println("LoginForm.fxml not found");
            return;
        }

        primaryStage.setScene(new Scene(FXMLLoader.load(serverFormUrl)));

        primaryStage.setTitle("Server");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage.getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(loginFormUrl)));
        stage.setTitle("EChat");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
}
