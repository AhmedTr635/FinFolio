package com.example.finfolio.Credits;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler {
    private ServerSocket serverSocket;

    public ServerHandler(int port) {
        try {
            // Create a ServerSocket to listen for client connections
            serverSocket = new ServerSocket(port);
            System.out.println("Server started and listening on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                // Accept incoming client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                // Create a new Clienthandler for this client
                Clienthandler clientHandler = new Clienthandler(clientSocket);
                // Start a new thread to handle communication with this client

                new Thread(clientHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Start the server on a specified port (e.g., 3002)
        ServerHandler serverhandler = new ServerHandler(3001);
        serverhandler.start();
    }
}
