package ru.nsu.ablaginin.producer;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Storage<T> {
    private BlockingDeque<T> storage = new LinkedBlockingDeque<>();

    public void put(T t) throws InterruptedException {
        storage.putLast(t);
    }

    public T take() throws InterruptedException {
        return storage.takeFirst();
    }

    @Override
    public String toString() {
        return "ru.nsu.ablaginin.producer.Storage{" +
                "storage=" + storage +
                '}';
    }
}
