package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ServerManager extends Thread {
    private static final int port = 1234;

    private final Random random;
    private final ArrayList<ClientHandler> clientList;
    private ServerSocket serverSocket;


    public ServerManager() {
        this.clientList = new ArrayList<>();
        this.random = new Random();
    }

    public void setup() {
        try {
            this.serverSocket = new ServerSocket(port);
        }
        catch (IOException e) {
            System.out.println("Unable to setup server");
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, Math.abs(random.nextInt()) % 100000);
                clientHandler.setup();
                clientHandler.start();
                clientList.add(clientHandler);
            } catch (IOException e) {
                System.out.println("An error occurred while client connecting");
            }
        }
    }
}
