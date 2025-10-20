package org.example;

import java.util.concurrent.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
        private static final int MESSAGE_LIMIT = 10;

        public static void main(String[] args) {
            if (args.length > 0 && args[0].equals("multi")) {
                runMultiProcessMode();
            } else {
                runMultiThreadedMode();
            }
        }

        private static void runMultiThreadedMode() {

            BlockingQueue<String> queue1 = new LinkedBlockingQueue<>();
            BlockingQueue<String> queue2 = new LinkedBlockingQueue<>();

            Player player1 = new Player("Player1", queue1, queue2, true);
            Player player2 = new Player("Player2", queue2, queue1, false);

            Thread thread1 = new Thread(player1);
            Thread thread2 = new Thread(player2);

            thread1.start();
            thread2.start();


        }

    private static void runMultiProcessMode() {
        try {
            Process player1 = new ProcessBuilder("java", "-cp", "target/classes", "PlayerProcess", "Player1", "5000", "5001").start();
            Thread.sleep(1000); // Ensure Player1 starts before Player2
            Process player2 = new ProcessBuilder("java", "-cp", "target/classes", "PlayerProcess", "Player2", "5001", "5000").start();

            player1.waitFor();
            player2.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}