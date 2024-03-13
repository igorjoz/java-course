package com.igorjoz.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskQueue taskQueue = new TaskQueue();
        ResultList resultList = new ResultList();

        // 4 threads as a default
        int threadsCount = args.length > 0 ? Integer.parseInt(args[0]) : 4;
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            Thread thread = new Thread(new PrimeCalculator(taskQueue, resultList));
            threads.add(thread);
            thread.start();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter numbers to check if they are prime. Type 'exit' to stop.");

        while (true) {
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            try {
                int number = Integer.parseInt(input);
                taskQueue.addTask(number);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number or 'exit'.");
            }
        }

        // Interrupt every thread and wait for them to finish
        threads.forEach(Thread::interrupt);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Error waiting for thread to finish.");
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Application closed.");
        System.out.println("Results: " + resultList.getResults());
    }
}
