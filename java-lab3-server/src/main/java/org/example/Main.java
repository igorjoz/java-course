package org.example;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        ServerManager serverManager = new ServerManager();
        serverManager.setup();
        serverManager.start();


        serverManager.join();
    }
}