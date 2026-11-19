/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.messageapp;

/**
 *
 * @author Student
 */

import java.util.Scanner;

public class MessageApp {

    private static MessageManager manager = new MessageManager(1000);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        runMessages(input, "Test User");
        input.close();
    }

    public static void runMessages(Scanner input, String sender) {

        int choice;

        do {
            System.out.println("\n================================");
            System.out.println("       QUICKCHAT MESSAGE APP");
            System.out.println("================================");
            System.out.println("1. Send Messages");
            System.out.println("2. Display Sent Messages");
            System.out.println("3. Message Statistics");
            System.out.println("4. Stored Messages");
            System.out.println("5. Exit");

            choice = readNumber(input, "Select an option: ");

            switch (choice) {
                case 1:
                    sendMessages(input, sender);
                    break;

                case 2:
                    System.out.println(manager.getSentMessagesReport());
                    break;

                case 3:
                    System.out.println("Messages sent: "
                            + manager.getSentCount());
                    System.out.println("Messages disregarded: "
                            + manager.getDisregardedCount());
                    System.out.println("Messages stored: "
                            + manager.getStoredCount());
                    break;

                case 4:
                    storedMessagesMenu(input);
                    break;

                case 5:
                    System.out.println("Thank you for using QuickChat!");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 5);
    }

    private static void sendMessages(Scanner input, String sender) {

        int number = readNumber(input,
                "How many messages would you like to enter? ");

        if (number <= 0) {
            System.out.println("Enter a number greater than zero.");
            return;
        }

        for (int i = 0; i < number; i++) {

            System.out.println("\nMESSAGE " + (i + 1));

            String recipient;
            Message message;

            do {
                System.out.print("Enter recipient cellphone (+27): ");
                recipient = input.nextLine();

                message = new Message(i + 1, sender,
                        recipient, "Temporary message");

                if (!message.isRecipientValid()) {
                    System.out.println(message.checkRecipientCell());
                }

            } while (!message.isRecipientValid());

            String messageText;

            do {
                System.out.print("Enter your message: ");
                messageText = input.nextLine();

                message = new Message(i + 1, sender,
                        recipient, messageText);

                if (!message.isMessageValid()) {
                    System.out.println(message.checkMessageLength());
                }

            } while (!message.isMessageValid());

            System.out.println("\nMESSAGE DETAILS");
            System.out.println(message.printMessageDetails());

            System.out.println("\n1. Send message");
            System.out.println("0. Disregard message");
            System.out.println("2. Store message");

            int option = readNumber(input, "Select an option: ");

            switch (option) {
                case 1:
                    System.out.println(message.sentMessage(1));
                    manager.addSentMessage(message);
                    break;

                case 0:
                    System.out.println(message.sentMessage(0));
                    manager.addDisregardedMessage(message);
                    break;

                case 2:
                    System.out.println(manager.addStoredMessage(message));
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void storedMessagesMenu(Scanner input) {

        String option;

        do {
            System.out.println("\n================================");
            System.out.println("         STORED MESSAGES");
            System.out.println("================================");
            System.out.println("A. Display sender and recipient");
            System.out.println("B. Display longest message");
            System.out.println("C. Search by message ID");
            System.out.println("D. Search by recipient");
            System.out.println("E. Delete using message hash");
            System.out.println("F. Display full message report");
            System.out.println("X. Return to main menu");

            System.out.print("Select an option: ");
            option = input.nextLine().trim().toUpperCase();

            switch (option) {
                case "A":
                    System.out.println(
                            manager.displaySendersAndRecipients());
                    break;

                case "B":
                    System.out.println(
                            manager.displayLongestMessage());
                    break;

                case "C":
                    System.out.print("Enter message ID: ");
                    String id = input.nextLine();
                    System.out.println(manager.searchByMessageID(id));
                    break;

                case "D":
                    System.out.print("Enter recipient cellphone: ");
                    String recipient = input.nextLine();
                    System.out.println(manager.searchByRecipient(recipient));
                    break;

                case "E":
                    System.out.print("Enter message hash: ");
                    String hash = input.nextLine();
                    System.out.println(manager.deleteByMessageHash(hash));
                    break;

                case "F":
                    System.out.println(
                            manager.displayFullStoredReport());
                    break;

                case "X":
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (!option.equals("X"));
    }

    private static int readNumber(Scanner input, String prompt) {

        while (true) {
            System.out.print(prompt);

            if (input.hasNextInt()) {
                int number = input.nextInt();
                input.nextLine();
                return number;
            }

            System.out.println("Please enter a valid whole number.");
            input.nextLine();
        }
    }
}
