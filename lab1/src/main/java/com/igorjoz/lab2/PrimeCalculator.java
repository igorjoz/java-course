package com.igorjoz.lab2;

public class PrimeCalculator implements Runnable {
    private final TaskQueue taskQueue;
    private final ResultList resultList;

    public PrimeCalculator(TaskQueue taskQueue, ResultList resultList) {
        this.taskQueue = taskQueue;
        this.resultList = resultList;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Integer task = taskQueue.getNextTask();

            if (task == null) {
                break;
            }

            boolean isPrime = isPrime(task);
            resultList.addResult(isPrime);
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            System.out.println("Calculated number is not prime");
            return false;
        }
        //for (int i = 2; i * i <= number; i++) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                System.out.println("Calculated number is not prime");
                return false;
            }
        }
        System.out.println("Calculated number is prime");
        return true;
    }
}
