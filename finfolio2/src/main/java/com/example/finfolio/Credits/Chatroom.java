package com.example.finfolio.Credits;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Chatroom {
    @FXML
    private TextArea messageArea;

    @FXML
    private TextField messageInput;

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public void initialize() {
        try {
            // Connect to the server
            socket = new Socket("localhost", 3001);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Connected to server.");
            // Start a new thread to receive messages
            new Thread(() -> {
                while (true) {
                    try {
                        String message = inputStream.readUTF();
                        messageArea.appendText(message + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        // Get the message from the input field
        String message = messageInput.getText();

        // Prepend "sender:" to the message
        String formattedMessage = "You: " + message;

        // Send the formatted message to the server
        outputStream.writeUTF(formattedMessage + "\n");
        messageInput.clear();
    }
}
