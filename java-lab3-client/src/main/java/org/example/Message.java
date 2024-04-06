package org.example;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PORT = 12345;

        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Sending 'ready' to server...");
            out.writeObject("ready");

            System.out.print("Enter the number of messages to send: ");
            int n = scanner.nextInt();
            scanner.nextLine(); // Czyści bufor skanera

            System.out.println("Sending the number of messages (" + n + ") to server...");
            out.writeObject(n);

            // Wysyłanie n obiektów Message do serwera
            for (int i = 0; i < n; i++) {
                System.out.print("Enter message content: ");
                String content = scanner.nextLine();
                Message message = new Message(i, content);
                System.out.println("Sending message: " + content);
                out.writeObject(message);
            }

            System.out.println("Sending 'finished' to server...");
            out.writeObject("finished");

            System.out.println("Client finished sending messages. Closing connection.");

        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
