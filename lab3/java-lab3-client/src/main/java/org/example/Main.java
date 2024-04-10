package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ConnectionManager connection = new ConnectionManager();
        connection.openConnection();
        connection.start();

        Scanner input = new Scanner(System.in);

        System.out.println("Enter number of messages: ");
        int howManyMessages = Integer.parseInt(input.nextLine());
        connection.sendMessagesNumber(howManyMessages);

        for (int i = 0; i < howManyMessages; i++) {
            String messageContent = input.nextLine();
            connection.sendMessage(new Message(i, messageContent));
        }

        connection.join();
        connection.closeConnection();
    }
}