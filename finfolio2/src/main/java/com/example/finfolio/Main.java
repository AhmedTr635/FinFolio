package com.example.finfolio;

import Models.Model;
import Views.AlerteFinFolio;
import Views.ViewFactory;
import com.example.finfolio.Service.UserService;
import com.example.finfolio.UsrController.EmailingApi;
import com.example.finfolio.UsrController.ModifierUserController;
import com.example.finfolio.UsrController.QRCodeApi;
import com.google.zxing.WriterException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
        @Override
        public void start(Stage stage) throws IOException, WriterException {

        Model.getInstance().getViewFactory().showLoginWindow();


        }

    }