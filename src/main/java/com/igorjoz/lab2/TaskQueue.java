package com.igorjoz.lab2;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {
    private final Queue<Integer> queue = new LinkedList<>();

    public synchronized void addTask(Integer number) {
        queue.add(number);
        notify();
    }

    public synchronized Integer getNextTask() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return queue.poll();
    }
}

