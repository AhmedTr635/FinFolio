package com.example.finfolio.Credits;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 3001;
    private static List<Clienthandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Create a new ClientHandler for this client
                Clienthandler clientHandler = new Clienthandler(clientSocket);
                clients.add(clientHandler); // Add clientHandler to the list of clients

                // Start a new thread to handle communication with this client
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcastMessage(String message) throws IOException {
        // Broadcast the message to all connected clients
        for (Clienthandler client : clients) {
            client.sendMessage(message);
        }
    }
}
