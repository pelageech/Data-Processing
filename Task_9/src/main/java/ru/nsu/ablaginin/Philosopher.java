package ru.nsu.ablaginin;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Philosopher extends Thread{
    private final Fork left;
    private final Fork right;
    private int spaghetti;
    private final String name;

    public Philosopher(String name, int spaghetti, Fork left, Fork right) { // spaghetti > 0
        this.spaghetti = spaghetti;
        this.left = left;
        this.right = right;
        this.name = name;
    }

    public void think() throws InterruptedException {
        System.out.println(name + " is thinking...");
        Thread.sleep(
                TimeUnit.SECONDS.toMillis(1) * ThreadLocalRandom.current().nextInt(1, 6)
        );
    }

    public void eat() throws InterruptedException {
        if (spaghetti > 0) {
            takeForks();
            System.out.println(name + " is eating...");
            Thread.sleep(
                    TimeUnit.SECONDS.toMillis(1) * ThreadLocalRandom.current().nextInt(3, 5)
            );
            spaghetti--;
            putForks();
        }
    }

    public synchronized void takeForks() throws InterruptedException {
        left.take();
        right.take();
    }

    public void putForks() {
        left.put();
        right.put();
    }

    @Override
    public void run() {
        while (spaghetti > 0) {
            try {
                think();
                eat();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
