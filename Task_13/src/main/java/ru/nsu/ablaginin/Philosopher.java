package ru.nsu.ablaginin;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Philosopher extends Thread{
    private static AtomicInteger countEating = new AtomicInteger(0);
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
        spaghetti.decrementAndGet();
        countEating.decrementAndGet();
        putForks();
    }

    // takeForks returns true, iff forks are taken.
    public boolean takeForks() throws InterruptedException {
        byte taken = 0;

        for (;;) {
            if (spaghetti.get() <= 0) {
                return false;
            } else if (taken == 2) {
                return true;
            }
            synchronized (left) {
                while (!left.take()) {
                    if (taken == 1) {
                        putRight();
                        taken--;
                    }
                    left.wait();
                }
                taken++;
            }
            if (spaghetti.get() <= 0) {
                return false;
            } else if (taken == 2) {
                return true;
            }
            synchronized (right) {
                while (!right.take()) {
                    if (taken == 1) {
                        putLeft();
                        taken--;
                    }
                    right.wait();
                }
                taken++;
            }
        }
    }

    private void putForks() {
        putLeft();
        putRight();
    }

    private void putLeft() {
        synchronized (left) {
            left.put();
            left.notify();
        }
        System.out.println("Philosopher " + name + " put left fork");
    }

    private void putRight() {
        synchronized (right) {
            right.put();
            right.notify();
        }
        System.out.println("Philosopher " + name + " put right fork");
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
