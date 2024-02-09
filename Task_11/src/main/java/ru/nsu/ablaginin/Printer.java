package ru.nsu.ablaginin;

import java.util.concurrent.Semaphore;

public class Printer extends Thread {
    private final Semaphore s1;
    private final Semaphore s2;

    public Printer(Semaphore s1, Semaphore s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
    @Override
    public void run() {
        for (int i = 10; i < 20; i++) {
            try {
                s2.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Child: " + i);
            s1.release();
        }
    }
}
