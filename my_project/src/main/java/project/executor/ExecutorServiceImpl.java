package project.executor;

import project.models.Task;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ExecutorServiceImpl {

    private Deque<Task> tasks;
    private Queue<ThreadWorkers> threads;
    private static final Map<Long, Runnable> users = new ConcurrentHashMap<>();

    public ExecutorServiceImpl(int threadCount) {
        this.tasks = new ConcurrentLinkedDeque<>();
        this.threads = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < threadCount; i++) {
            ThreadWorkers threadWorker = new ThreadWorkers();
            threadWorker.start();
            this.threads.add(threadWorker);
        }
    }

    public void submit(Long userId, Runnable task) {
        synchronized (tasks) {
            tasks.push(Task.builder().userId(userId).task(task).build());
            tasks.notify();
        }
    }

    public void clear(Long userId) {
        users.remove(userId);
    }

    private class ThreadWorkers extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                }
                Task task = tasks.poll();
                users.put(task.getUserId(), task.getTask());
                users.get(task.getUserId()).run();
            }
        }
    }

    public void notifyUser(Long userId) {
        if (users.containsKey(userId)) {
            synchronized (users.get(userId)) {
                users.get(userId).notify();
            }
        }
    }
}
