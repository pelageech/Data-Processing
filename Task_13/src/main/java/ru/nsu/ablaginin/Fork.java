package ru.nsu.ablaginin;

import java.util.concurrent.Semaphore;

public class Fork {
    private Semaphore semaphore = new Semaphore(1);

    public boolean take() throws InterruptedException {
        return semaphore.tryAcquire();
    }

    public void put() {
        semaphore.release();
    }
}
