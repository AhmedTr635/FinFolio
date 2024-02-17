package com.example.finfolio;

import Models.Model;
import Views.ViewFactory;
import com.example.finfolio.Service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
        @Override
        public void start(Stage stage) throws IOException {

        Model.getInstance().getViewFactory().showLoginWindow();

        }

    }