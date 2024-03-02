package com.example.gestioncredit1;

import Entity.Message;
import Service.CreditService;
import Service.MessageService;
import Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.text.BreakIterator;

public class Chatroom {
    @FXML
    private TextArea messageArea;

    @FXML
    private TextField messageInput;



 CreditService creditService = new CreditService();
    private String creditID;
   private int userIdd =1;


    UserService a1 = new UserService();






    public void setCreditID(String creditID) {
        this.creditID = creditID;


    }


    public void sendMessage(ActionEvent actionEvent) {

        // Get the message from the input field
        String message = messageInput.getText();

        // Get the sender's user ID from the application context or any other source
         // Replace getUserId() with your actual method to retrieve the sender's user ID

        // Get the receiver's user ID from the credit details
        int receiverUserId = creditService.getReceiverUserIdByCreditId(creditID);// Replace getReceiverIdFromCreditDetails() with your actual method to retrieve the receiver's user ID from credit details
         MessageService messageService = new MessageService();
        // Now you have the senderId, receiverId, and message, you can send the message to the receiver


        // Clear the message input field after sending the message

        insertMessage(userIdd, receiverUserId, message);



    }

    private void insertMessage(int senderUserId, int receiverUserId, String message) {
        // Assuming you have a MessageService class to handle message-related operations
        MessageService messageService = new MessageService();

        // Create a new message object with sender ID, receiver ID, and message content
        Message newMessage = new Message(senderUserId, receiverUserId, message);

        // Call a method in your MessageService to insert the message into your database
        messageService.insertMessage(newMessage);

        // You may also want to update the UI to show that the message has been sent
        messageArea.appendText("You: " + message + "\n");
    }



}
