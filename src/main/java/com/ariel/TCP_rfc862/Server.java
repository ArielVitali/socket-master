package com.ariel.TCP_rfc862;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(PORT)) {
            System.out.println("Server up & running on port " + PORT + "...");

            while (true) {
                Socket clientSocket = ss.accept();
                new Thread(() -> {
                  try {
                    handleClient(clientSocket);
                  } catch (IOException e) {
                    System.out.println("Error in main: " + e.getMessage());
                  }
                }).start();
            }
        } catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) throws IOException {
        try {
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            String clientAddress = clientSocket.getInetAddress().getHostAddress();
            String threadName = Thread.currentThread().getName();

            System.out.printf("Client %s connected! Handled by thread: %s%n", clientAddress, threadName);

            // Read client message
            byte[] message = in.readAllBytes();
            System.out.println("Message received from " + clientAddress + ": " + message.toString());

            // Echo back message to client
            System.out.println("Server received message - responding: " + message.toString());
            out.write(message);

            System.out.println("Client " + clientAddress + " disconnected");

            in.close();
            out.close();
            clientSocket.close();

        } catch (Exception e) {
            System.err.println("Error in IN: " + clientSocket.getInputStream());
            System.err.println("Error in inetAddress: " + clientSocket.getInetAddress());
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}
