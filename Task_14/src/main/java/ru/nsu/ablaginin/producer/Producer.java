package ru.nsu.ablaginin.producer;

public abstract class Producer<T> implements Runnable{
    private final Storage<T> storage;
    public Producer(Storage<T> storage) {
        this.storage = storage;
    }
    abstract void produce() throws InterruptedException;

    public void put(T t) throws InterruptedException {
        storage.put(t);
    }

    public T take() throws InterruptedException {
        return storage.take();
    }

    @Override
    public void run() {
        for (;;) {
            try {
                produce();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
