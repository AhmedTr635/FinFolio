package com.example.finfolio.Credits;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Clienthandler implements Runnable {
    private final Socket clientSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public Clienthandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Initialize input and output streams for the client
            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new DataOutputStream(clientSocket.getOutputStream());

            // Continuously listen for messages from the client
            while (true) {
                String message = inputStream.readUTF();
                System.out.println("Message from client: " + message);

                // Broadcast the received message to all clients
                Server.broadcastMessage(message);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + clientSocket);
        } finally {
            try {
                // Close streams and socket when client disconnects
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        // Send a message to the client
        outputStream.writeUTF(message);
    }
}
