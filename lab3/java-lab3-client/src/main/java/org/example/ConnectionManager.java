package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionManager extends Thread {
    private static final String host = "localhost";
    private static final int port = 1234;

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private int sendMessageCount;
    private int howManyMessages;

    private void sendObject(Object dataToSend) {
        if (outputStream != null) {
            try {
                outputStream.writeObject(dataToSend);
            }
            catch (IOException e) {
                System.out.println("Unable to send data");
            }
        }
    }

    public ConnectionManager()  {
        this.socket = null;
        this.outputStream = null;
        this.inputStream = null;
        this.sendMessageCount = 0;
        this.howManyMessages = 0;
    }

    public void openConnection()  {
        try {
            this.socket = new Socket(host, port);
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {
            System.out.println("Cannot connect to server");
        }
    }

    public void closeConnection() {
        if (socket != null) {
            try {
                socket.close();
            }
            catch (IOException e) {
                System.out.println("An error occurred while closing connection");
            }
        }
    }

    public void sendMessage(Message message) {
        if (sendMessageCount == howManyMessages)
            return;

        sendObject(message);
        this.sendMessageCount++;
    }

    public void sendMessagesNumber(Integer messagesNumber) {
        sendObject(messagesNumber);
        this.howManyMessages = messagesNumber;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object input = inputStream.readObject();

                if (input == null)
                    break;

                String serverMessage = (String)input;
                System.out.println("SERVER: " + serverMessage);

                if(serverMessage.equals("finished"))
                    break;
            }
            catch (IOException e) {
                System.out.println("Connection lost");
                break;
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
