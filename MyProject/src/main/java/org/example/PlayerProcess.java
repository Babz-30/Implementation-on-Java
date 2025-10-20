package org.example;
import java.io.*;
import java.net.*;

/**
 * PlayerProcess for multi-process mode.
 * Communicates via sockets.
 */
class PlayerProcess {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Usage: java PlayerProcess <name> <receivePort> <sendPort>");
            return;
        }

        String name = args[0];
        int receivePort = Integer.parseInt(args[1]);
        int sendPort = Integer.parseInt(args[2]);
        int messageCount = 0;

        try (ServerSocket serverSocket = new ServerSocket(receivePort);
             Socket socket = new Socket("localhost", sendPort);
             BufferedReader reader = new BufferedReader(new InputStreamReader(serverSocket.accept().getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            // Player 1 sends the first message
            if (name.equals("Player1")) {
                writer.println("Hello from " + name);
            }

            while (messageCount < 10) {
                String message = reader.readLine();
                if (message != null) {
                    System.out.println(name + " received: " + message);
                    writer.println(message + " " + messageCount);
                    messageCount++;
                }
            }
        }
    }
}