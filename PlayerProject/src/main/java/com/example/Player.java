package com.example;

import java.util.concurrent.BlockingQueue;

/**
 * The {@code Player} class represents a player in a messaging simulation. Each player sends and receives
 * messages to/from a partner player via two {@link BlockingQueue} instances. The player can act as an initiator,
 * starting the messaging process, or a responder, receiving and responding to messages.
 *
 * The class implements {@link Runnable}, which allows players to be run on separate threads, making the interaction
 * between players asynchronous.
 *
 * <p>
 * A player sends messages to the partner in a loop until a predefined limit (10 messages) is reached. The initiator
 * starts by sending the first message, and the responder continues the conversation by replying with modified
 * messages until the limit is hit.
 * </p>
 *
 * <p>
 * The messages are exchanged between players using a {@link BlockingQueue}, which handles synchronization and
 * thread safety for message transfer.
 * </p>
 *
 * <p>
 * Example of how two players can interact:
 * <pre>
 *     Player player1 = new Player("Player1", inbox1, inbox2, true); // Initiator
 *     Player player2 = new Player("Player2", inbox2, inbox1, false); // Responder
 * </pre>
 * </p>
 *
 * @author Babitha Nadar
 * @version 1.0
 * @since 2025-02-26
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
                sendMessage("Hello "); //Initiator
            }
            receiveMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
