package org.example;

import java.util.concurrent.*;

/**
 * Player class that can send and receive messages.
 */
public class Player implements Runnable {
    private final String name;
    private final BlockingQueue<String> inbox;
    private final BlockingQueue<String> partnerInbox;
    private int messageCount = 0;
    private final boolean isInitiator;

    public Player(String name, BlockingQueue<String> inbox, BlockingQueue<String> partnerInbox, boolean isInitiator) {
        this.name = name;
        this.inbox = inbox;
        this.partnerInbox = partnerInbox;
        this.isInitiator = isInitiator;
    }

    public void sendMessage(String message) throws InterruptedException {
        System.out.println(name + " sent: " + message);
        partnerInbox.put(message);
        messageCount++;

    }

    public void receiveMessage() throws InterruptedException {
        while (messageCount < 10) {
            String message = inbox.take();
            System.out.println(name + " received: " + message);

            if (messageCount < 10) { // Ensures exactly 10 messages are sent by initiator

                sendMessage(message + " " + (messageCount+1));
            }
        }
        System.out.println(name + " has reached the message limit.");
    }

    @Override
    public void run() {
        try {
            if (isInitiator) {
                sendMessage("Hello from " + name );
            }
            receiveMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

