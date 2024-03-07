package com.example.finfolio.Service;

import com.example.finfolio.Entite.Message;
import com.example.finfolio.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageService {
    private Connection connection;

    public MessageService() {
        connection = DataSource.getInstance().getCnx();
    }

    public void insertMessage(Message message) {
        String query = "INSERT INTO messages (sender_id, receiver_id, message) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, message.getSender_id());
            preparedStatement.setInt(2, message.getReceiver_id());
            preparedStatement.setString(3, message.getMessage());
           // preparedStatement.setTimestamp(4, message.getTimestamp());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Message added successfully!");
            } else {
                System.out.println("Failed to add message!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding message: " + e.getMessage());
        }
    }


    public int countReceivedMessagesByUserId(int userId) {
        String query = "SELECT COUNT(*) AS messageCount FROM messages WHERE receiver_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("messageCount");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting received messages: " + e.getMessage());
        }
        return 0;
    }
}
