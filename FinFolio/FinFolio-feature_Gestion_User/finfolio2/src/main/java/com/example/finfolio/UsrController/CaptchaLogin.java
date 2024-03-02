package com.example.finfolio.UsrController;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Random;

public class CaptchaLogin {


    private static final int WIDTH = 150;
    private static final int HEIGHT = 70;
    public static String generateCaptchaCode(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }
}
