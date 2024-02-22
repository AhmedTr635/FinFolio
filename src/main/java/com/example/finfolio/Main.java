package com.example.finfolio;

import Models.Model;
import Views.ViewFactory;
import com.example.finfolio.Service.UserService;
import com.example.finfolio.UsrController.EmailingApi;
import com.example.finfolio.UsrController.QRCodeApi;
import com.google.zxing.WriterException;
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
        public void start(Stage stage) throws IOException, WriterException {

        Model.getInstance().getViewFactory().showLoginWindow();
           /*EmailingApi e=new EmailingApi();
            QRCodeApi qr=new QRCodeApi();
            qr.GenereQrCode("aaa");
            e.sendEmailWithAttachment("trabelsi.ahmed@esprit.tn","QRCode","Scanner",qr.getPath());*/

        }

    }