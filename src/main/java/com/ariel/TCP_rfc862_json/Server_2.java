package com.ariel.TCP_rfc862_json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;
import org.apache.commons.math3.primes.Primes;

public class Server_2 {
  private static final int PORT = 8080;

  public static void main(String[] args) {
    try (ServerSocket ss = new ServerSocket(PORT, 5,InetAddress.getByName("0.0.0.0"))) {
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
      String message;
      BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      BufferedWriter output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

      System.out.printf("Client %s connected!%n", clientSocket.getInetAddress().getHostAddress());

      while ((message = input.readLine()) != null){ //read until client disconnects
        JSONObject json;

        try {
            json = new JSONObject(message);
            if (!json.has("method") || !json.has("number") || !json.getString("method").equals("isPrime")) {
              sendMalformedResponse(output);
              break;
            }

            System.out.printf("Request received %s %n", json);

            if (!json.getString("method").equals("isPrime")) {
              sendMalformedResponse(output);
              continue;
            }

            Object numberValue = json.get("number");
            boolean isPrime = false;


            if (numberValue instanceof Integer) {
              isPrime = Primes.isPrime((Integer) numberValue);
            } else if (numberValue instanceof Double || numberValue instanceof Float) {
              isPrime = false;
            } else if(numberValue.toString().contains(".")){
              isPrime = false;
            } else if (numberValue instanceof Number) {
              BigInteger bigInt = new BigInteger(numberValue.toString());
              isPrime = bigInt.isProbablePrime(10);
            } else {
              sendMalformedResponse(output);
              break;
            }

            JSONObject response = new JSONObject();
            response.put("method","isPrime");
            response.put("prime",isPrime);

            output.write(response.toString() + "\n");
            output.flush();
            System.out.println("Response sent: " + response);

        } catch (Exception e) {
          sendMalformedResponse(output);
          break;
        }

    }
    } finally {
      System.out.println("Client disconnected.");
      clientSocket.close();
    }
  }

  private static void sendMalformedResponse(BufferedWriter output) throws IOException {
    output.write("malformed\n");
    output.flush();
  }
}