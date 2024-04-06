package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final Socket socket;
    private final int clientId;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ClientStatus status;
    private int messagesCount;
    private int howManyMessages;
    private boolean listenForClientInput;

    private void changeStatus(ClientStatus newStatus) {
        if (newStatus == status)
            return;

        String message = null;

        switch (newStatus) {
            case READY:
                message = "ready";
                break;
            case READY_FOR_MESSAGES:
                message = "ready for messages";
                break;
            case FINISHED:
                message = "finished";
                this.listenForClientInput = false;
                System.out.println("Finished listening for client " + this.clientId);
                break;
        }

        this.status = newStatus;

        try {
            outputStream.writeObject(message);
        }
        catch (IOException e) {
            System.out.println("Cannot send data to client");
        }
    }

    private void processInput(Object input) {
        switch (this.status) {
            case NONE:
                break;
            case READY:
                howManyMessages = (Integer)input;
                changeStatus(ClientStatus.READY_FOR_MESSAGES);
                System.out.println("CLIENT " + clientId + ": declared to send: " + howManyMessages + " messages");
                break;
            case READY_FOR_MESSAGES:
                messagesCount++;
                Message message = (Message)input;
                System.out.println("CLIENT " + clientId + ": send message nr: " + message.getNumber() + ", content: " + message.getContent());

                if(messagesCount == howManyMessages)
                    changeStatus(ClientStatus.FINISHED);

                break;
            case FINISHED:
                break;
            default:
                System.out.println("Unknown input");
                break;
        }
    }

    public ClientHandler(Socket socket, int id) {
        this.status = ClientStatus.NONE;
        this.clientId = id;
        this.socket = socket;
        this.messagesCount = 0;
        this.howManyMessages = 0;
        this.listenForClientInput = true;

        System.out.println("\n----- NEW USER CONNECTED WITH ID: " + id + " -----");
    }

    public void setup() {
        try {
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e) {
            System.out.println("Cannot create ClientHandler in/out streams");
        }
    }

    @Override
    public void run() {
        changeStatus(ClientStatus.READY);

        while (listenForClientInput) {
            try {
                Object input = inputStream.readObject();

                if (input == null)
                    break;

                processInput(input);
            }
            catch (IOException e) {
                System.out.println("Client broke the connection");
                break;
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
