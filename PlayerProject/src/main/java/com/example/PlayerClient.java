package com.example;

import java.io.*;
import java.net.*;
/**
 * The {@code PlayerClient} class represents a client player that connects to a server (Player 2) over a socket connection.
 * It sends messages to the server and receives responses back in a loop, continuing the conversation until a predefined
 * message limit (10 messages) is reached.
 *
 * <p>
 * The player begins the communication by sending an initial message ("Hello") to the server. Then, for each subsequent
 * message, it appends a message count (starting from 1) and sends it to the server. After sending a message, the client
 * waits to receive a response from the server, which is printed to the logfile.
 * </p>
 *
 *
 * @author Babitha Nadar
 * @version 1.0
 * @since 2025-02-26
 */
public class PlayerClient {
    public static void main(String[] args) {
        System.out.println("Player1 is available!");
        try (Socket socket = new Socket("localhost", 12345)) {
            System.out.println("Connected to Player2!");
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            int messageCounter = 0;
            String message = "Hello "; //Initiator

            while (messageCounter < 10) {
                if(messageCounter == 0 ){
                    out.println(message);
                    System.out.println("Player1 Sent: " + message);
                }
                else {
                    out.println(message +" "+ messageCounter);
                    System.out.println("Player1 Sent: " + message + " " + messageCounter);
                }

                message = in.readLine();
                System.out.println("Player1 received: " + message);
                messageCounter++;
            }
            System.out.println("Player1 has reached the message limit.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
