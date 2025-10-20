package com.example;

import java.io.*;
import java.net.*;

/**
 * The {@code PlayerServer} class represents a server-side player that listens for incoming connections
 * from a client (Player 1) over a socket. Once a connection is established, the server receives messages
 * from Player 1, processes them, and sends back responses with an incremented message counter.
 *
 * <p>
 * The server listens on port 12345 for an incoming connection from Player 1. After establishing the connection,
 * the server reads messages sent by Player 1, appends a message count to each received message, and sends it
 * back to Player 1. This exchange continues until a predefined message limit (10 messages) is reached.
 * </p>
 *
 *
 * @author Babitha Nadar
 * @version 1.0
 * @since 2025-02-26
 */
public class PlayerServer {
    public static void main(String[] args) {
        System.out.println("Player2 is available!");

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Waiting for Player1 connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Player2 connected to Player1!");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String receivedMessage;
            int messageCounter = 0;

            while ((receivedMessage = in.readLine()) != null && messageCounter < 10) {
                messageCounter++;
                System.out.println("Player2 received: " + receivedMessage);
                out.println(receivedMessage + " " + messageCounter);
                System.out.println("Player2 sent: " + receivedMessage+ " " + messageCounter);
            }
            System.out.println("Player2 has reached the message limit.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
