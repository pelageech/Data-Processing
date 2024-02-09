package ru.nsu.ablaginin;

import java.util.concurrent.Semaphore;

public class Fork {
    private Semaphore semaphore = new Semaphore(1);

    public void take() throws InterruptedException {
        semaphore.acquire();
    }

    public void put() {
        semaphore.release();
    }
}
