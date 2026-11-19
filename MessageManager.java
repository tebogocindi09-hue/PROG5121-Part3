/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.messageapp;

/**
 *
 * @author Student
 */


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MessageManager {

    private String[] sentMessages;
    private String[] disregardedMessages;
    private String[] storedMessages;
    private String[] messageHashes;
    private String[] messageIDs;
    private Message[] storedMessageObjects;

    private int sentCount;
    private int disregardedCount;
    private int storedCount;

    public MessageManager(int size) {
        sentMessages = new String[size];
        disregardedMessages = new String[size];
        storedMessages = new String[size];
        messageHashes = new String[size];
        messageIDs = new String[size];
        storedMessageObjects = new Message[size];

        sentCount = 0;
        disregardedCount = 0;
        storedCount = 0;
    }

    public void addSentMessage(Message message) {
        if (sentCount < sentMessages.length) {
            sentMessages[sentCount] = message.printMessageDetails();
            sentCount++;
        }
    }

    public void addDisregardedMessage(Message message) {
        if (disregardedCount < disregardedMessages.length) {
            disregardedMessages[disregardedCount]
                    = message.printMessageDetails();
            disregardedCount++;
        }
    }

    public String addStoredMessage(Message message) {
        if (storedCount >= storedMessages.length) {
            return "Stored message limit reached.";
        }

        String result = message.storeMessage();

        if (result.equals("Message successfully stored.")) {
            storedMessages[storedCount] = message.printMessageDetails();
            messageHashes[storedCount] = message.getMessageHash();
            messageIDs[storedCount] = message.getMessageID();
            storedMessageObjects[storedCount] = message;
            storedCount++;
        }

        return result;
    }

    public int getSentCount() {
        return sentCount;
    }

    public int getDisregardedCount() {
        return disregardedCount;
    }

    public int getStoredCount() {
        return storedCount;
    }

    public String getSentMessagesReport() {
        if (sentCount == 0) {
            return "No sent messages available.";
        }

        String report = "";

        for (int i = 0; i < sentCount; i++) {
            report += sentMessages[i] + "\n\n";
        }

        return report;
    }

    // A: Display sender and recipient of stored messages.
    public String displaySendersAndRecipients() {
        if (storedCount == 0) {
            return "No stored messages available.";
        }

        String report = "";

        for (int i = 0; i < storedCount; i++) {
            Message message = storedMessageObjects[i];

            report += "Sender: " + message.getSender()
                    + "\nRecipient: " + message.getRecipient()
                    + "\n--------------------------\n";
        }

        return report;
    }

    // B: Display the longest stored message.
    public String displayLongestMessage() {
        if (storedCount == 0) {
            return "No stored messages available.";
        }

        Message longest = storedMessageObjects[0];

        for (int i = 1; i < storedCount; i++) {
            if (storedMessageObjects[i].getMessage().length()
                    > longest.getMessage().length()) {
                longest = storedMessageObjects[i];
            }
        }

        return longest.printMessageDetails();
    }

    // C: Search using a message ID.
    public String searchByMessageID(String id) {
        for (int i = 0; i < storedCount; i++) {
            if (messageIDs[i].equals(id)) {
                return "Message found:\n"
                        + "Recipient: "
                        + storedMessageObjects[i].getRecipient()
                        + "\nMessage: "
                        + storedMessageObjects[i].getMessage();
            }
        }

        return "Message ID not found.";
    }

    // D: Search for all messages for a recipient.
    public String searchByRecipient(String recipient) {
        String report = "";
        boolean found = false;

        for (int i = 0; i < storedCount; i++) {
            if (storedMessageObjects[i].getRecipient()
                    .equalsIgnoreCase(recipient)) {
                report += storedMessageObjects[i].printMessageDetails()
                        + "\n--------------------------\n";
                found = true;
            }
        }

        return found ? report : "No messages found for that recipient.";
    }

    // E: Delete a message using its hash.
    public String deleteByMessageHash(String hash) {
        for (int i = 0; i < storedCount; i++) {
            if (messageHashes[i].equalsIgnoreCase(hash)) {

                String fileName = "message_" + messageIDs[i] + ".json";

                try {
                    Files.deleteIfExists(Paths.get(fileName));
                } catch (IOException exception) {
                    return "Could not delete the JSON file: "
                            + exception.getMessage();
                }

                for (int j = i; j < storedCount - 1; j++) {
                    storedMessages[j] = storedMessages[j + 1];
                    messageHashes[j] = messageHashes[j + 1];
                    messageIDs[j] = messageIDs[j + 1];
                    storedMessageObjects[j] = storedMessageObjects[j + 1];
                }

                storedCount--;

                storedMessages[storedCount] = null;
                messageHashes[storedCount] = null;
                messageIDs[storedCount] = null;
                storedMessageObjects[storedCount] = null;

                return "Message successfully deleted.";
            }
        }

        return "Message hash not found.";
    }

    // F: Display the full stored-message report.
    public String displayFullStoredReport() {
        if (storedCount == 0) {
            return "No stored messages available.";
        }

        String report = "";

        for (int i = 0; i < storedCount; i++) {
            report += storedMessageObjects[i].printMessageDetails()
                    + "\n--------------------------\n";
        }

        return report;
    }
}
