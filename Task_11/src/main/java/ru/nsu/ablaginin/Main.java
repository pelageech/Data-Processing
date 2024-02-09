package ru.nsu.ablaginin;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(0);
        var th = new Printer(s1, s2);
        th.start();

        for (int i = 0; i < 10; i++) {
            try {
                s1.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Parent: " + i);
            s2.release();
        }
    }
}
