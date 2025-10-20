package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@code ProcessFactory} class provides methods for starting players either in the same process
 * or in separate processes. It handles the setup of the necessary threads or processes to facilitate
 * communication between the players, either within a single JVM or across separate Java processes.
 *
 * <p>
 * In the same-process mode, two players are created and run on separate threads, communicating through
 * blocking queues. In the separate-process mode, each player is started as a separate Java process
 * using the {@link ProcessBuilder}, with their interactions logged to separate files.
 * </p>
 *
 * <p>
 * This class abstracts the creation and management of player interactions, enabling flexible configurations
 * for running the players in different environments.
 * </p>
 *
 * @author Babitha Nadar
 * @version 1.0
 * @since 2025-02-26
 */
public class ProcessFactory {
    public static void startPlayersInSameProcess() {
        BlockingQueue<String> queue1 = new LinkedBlockingQueue<>();
        BlockingQueue<String> queue2 = new LinkedBlockingQueue<>();

        Player player1 = new Player("Player1", queue1, queue2, true);
        Player player2 = new Player("Player2", queue2, queue1, false);

        Thread thread1 = new Thread(player1);
        Thread thread2 = new Thread(player2);

        thread1.start();
        thread2.start();

    }

    public static void startPlayersInSeparateProcesses() {
        System.out.println("Starting players in separate processes...");
        try {
            ProcessBuilder serverProcess = new ProcessBuilder("java", "-cp", "target/player-communication-1.0-SNAPSHOT.jar", "com.example.PlayerServer");
            serverProcess.inheritIO();
            File serverOutput = new File("player2_output.log");
            serverProcess.redirectOutput(serverOutput);
            serverProcess.redirectError(serverOutput);
            Process server = serverProcess.start();

            ProcessBuilder clientProcess = new ProcessBuilder("java", "-cp", "target/player-communication-1.0-SNAPSHOT.jar", "com.example.PlayerClient");
            clientProcess.inheritIO();
            File clientOutput = new File("player1_output.log");
            clientProcess.redirectOutput(clientOutput);
            clientProcess.redirectError(clientOutput);
            Process client = clientProcess.start();

            // Wait for both processes to finish
            server.waitFor();
            client.waitFor();

            // Print log files to console
            System.out.println("\n--- Player 1 Log ---");
            printLogFile(clientOutput);

            System.out.println("\n--- Player 2 Log ---");
            printLogFile(serverOutput);

            System.out.println("Process completed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printLogFile(File logFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading log file: " + logFile.getName());
            e.printStackTrace();
        }
    }
}
