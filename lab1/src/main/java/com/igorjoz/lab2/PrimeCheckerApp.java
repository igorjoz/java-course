package com.igorjoz.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrimeCheckerApp {
    public static void main(String[] args) {
        TaskQueue taskQueue = new TaskQueue();
        ResultList resultList = new ResultList();

        int threadsCount = Integer.parseInt(args[0]);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            Thread thread = new Thread(new PrimeCalculator(taskQueue, resultList));
            threads.add(thread);
            thread.start();
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a number to check (or 'exit' to finish): ");
            String input = scanner.nextLine();

            if ("exit".equals(input)) {
                break;
            }

            int number = Integer.parseInt(input);
            taskQueue.addTask(number);
        }

        threads.forEach(Thread::interrupt);
        
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Results: " + resultList.getResults());
    }
}
