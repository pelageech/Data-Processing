package ru.nsu.ablaginin;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Philosopher extends Thread{
    private static AtomicInteger countEating = new AtomicInteger(0);
    private static final Object forks = new Object();
    private final Fork left;
    private final Fork right;
    private static AtomicInteger spaghetti;
    private final String name;

    public Philosopher(String name, int spaghetti, Fork left, Fork right) { // spaghetti > 0
        Philosopher.spaghetti = new AtomicInteger(spaghetti);
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
        if (!takeForks()) {
            return;
        }

        System.out.println(name + " is eating... Eating in the same time - " + countEating.addAndGet(1));
        Thread.sleep(
                TimeUnit.SECONDS.toMillis(1) * ThreadLocalRandom.current().nextInt(3, 5)
        );
        countEating.decrementAndGet();
        putForks();
    }

    // takeForks returns true, iff forks are taken.
    public boolean takeForks() throws InterruptedException {
        synchronized (forks) {
            // while we are waiting, spaghetti could be eaten
            while (spaghetti.get() > 0) {
                if (!left.take()) {
                    forks.wait();
                    continue;
                }

                System.out.println(" Philosopher " + name + " took left fork");
                if (!right.take()) {
                    left.put();
                    forks.wait();
                    continue;
                }
                System.out.println("Philosopher " + name + " took right fork");

                return true;
            }

            return false;
        }
    }

    private void putForks() {
        synchronized (forks) {
            putLeft();
            putRight();
        }
    }

    private void putLeft() {
        left.put();
        forks.notifyAll();
        System.out.println("Philosopher " + name + " put left fork");
    }

    private void putRight() {
        right.put();
        forks.notifyAll();
        System.out.println("Philosopher " + name + " put left fork");
    }

    @Override
    public void run() {
        while (spaghetti.get() > 0) {
            try {
                think();
                eat();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
