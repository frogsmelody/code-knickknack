package com.jason;

import java.util.ArrayList;
import java.util.List;

public class ThreadPoolPushExecutor {
    private Thread[] threads;

    private PushService pushService;
    private QueueService queueService;

    public ThreadPoolPushExecutor(int threadCount, PushService pushService, QueueService queueService) {
        threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            Worker worker = new Worker("PushThread-" + i);
            threads[i] = worker;
        }
        this.pushService = pushService;
        this.queueService = queueService;
        checkStart();
    }

    public ThreadPoolPushExecutor(int threadCount) {
        threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            Worker worker = new Worker("PushThread-" + i);
            threads[i] = worker;
        }
        checkStart();
    }

    private void checkStart() {
        for (Thread thread : threads) {
            if (thread.getState() == Thread.State.NEW) {
                thread.start();
            }
        }
    }

    public void updateThreadCount(int newThreadCount) {
        System.out.println("Change thread count to:" + newThreadCount);
        if (newThreadCount < 1) {
            throw new IllegalArgumentException(String.format("Invalid thread count %d,Thread count must be great than 1", newThreadCount));
        }
        if (threads.length == newThreadCount) {
            return;
        }
        Thread[] newThreads = new Thread[newThreadCount];
        if (newThreadCount > threads.length) {
            System.arraycopy(threads, 0, newThreads, 0, threads.length);
            for (int index = threads.length; index < newThreadCount; index++) {
                Worker worker = new Worker("PushThread-" + index);
                newThreads[index] = worker;
                worker.start();
            }
        } else {
            System.arraycopy(threads, 0, newThreads, 0, newThreadCount);
            for (int exceededIndex = newThreads.length; exceededIndex < threads.length; exceededIndex++) {
                Worker thread = (Worker) threads[exceededIndex];
                thread.stopRunning();
            }
        }
        threads = newThreads;
    }

    private final class Worker extends Thread {
        private boolean running = true;

        public Worker(String name) {
            super(name);
        }

        public void run() {
            while (running) {
                HotelChange hotelChange = queueService.takeHotelChange();
                if (hotelChange == null) {
                    return;
                }
                pushService.push(hotelChange);
            }
            System.out.println(String.format("%s, stop", this.getName()));
        }

        public void stopRunning() {
            running = false;
        }
    }

    public int activeThreads() {
        int active = 0;
        for (Thread thread : threads) {
            if (thread != null && thread.isAlive()) {
                active++;
            }
        }
        return active;
    }

    public List<String> getThreadStatus() {
        List<String> threadStatus = new ArrayList<String>();
        for (Thread thread : threads) {
            threadStatus.add(String.format("Thread Name :%s, State :%s", thread.getName(), thread.getState()));
        }
        return threadStatus;
    }
}
