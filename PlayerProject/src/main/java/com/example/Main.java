package com.example;
/**
 * Main class for the application that starts players in either the same or separate processes
 * based on the provided command-line argument.
 *
 * <p>
 * This class serves as the entry point to the application. It checks if the user has provided
 * a mode ('same' or 'separate') as a command-line argument and invokes the corresponding method
 * from the ProcessFactory class to start the players in the selected mode.
 * </p>
 *
 * <p>
 * Usage:
 * <pre>
 *     java Main <mode>
 * </pre>
 *
 * Valid modes:
 * <ul>
 *     <li><b>same</b>: Starts players in the same process.</li>
 *     <li><b>separate</b>: Starts players in separate processes.</li>
 * </ul>
 * </p>
 *
 * <p>
 * If the argument is invalid or missing, an error message is displayed, indicating the correct usage.
 * </p>
 *
 * Example:
 * <pre>
 *     start.bat same
 *     start.bat separate
 * </pre>
 *
 * @author Babitha Nadar
 * @version 1.0
 * @since 2025-02-26
 */
public class Main {
    public static void main(String[] args) {
        // Check if the user provided a mode: "same" or "separate"
        if (args.length != 1) {
            System.out.println("Usage: java Main <mode>");
            System.out.println("mode: 'same' or 'separate'");
            return;
        }

        String mode = args[0];

        if ("same".equalsIgnoreCase(mode)) {
            ProcessFactory.startPlayersInSameProcess();
        } else if ("separate".equalsIgnoreCase(mode)) {
            ProcessFactory.startPlayersInSeparateProcesses();
        } else {
            System.out.println("Invalid mode. Use 'same' or 'separate'.");
        }
    }
}
