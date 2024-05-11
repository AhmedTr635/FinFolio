package com.example.finfolio.Credits;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiverController {

    @FXML
    private TextArea messageArea;

    @FXML
    private TextField messageInput;

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public void initialize() {
        messageArea.appendText("Welcome to the chat!\n");
        // Connect to the server and start receiving messages
        try {
            socket = new Socket("localhost", 3001);
            System.out.println("Connected to server.");

            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                while (true) {
                    try {
                        String message = inputStream.readUTF();

                        // Update UI on JavaFX Application Thread
                        Platform.runLater(() -> {
                            messageArea.appendText(message + "\n");
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ActionEvent actionEvent) {
        try {
            // Get the message from the input field
            String message = messageInput.getText();

            // Prepend "receiver:" to the message
            String formattedMessage = "receiver: " + message;

            // Send the formatted message to the server
            outputStream.writeUTF(formattedMessage);
            messageInput.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
