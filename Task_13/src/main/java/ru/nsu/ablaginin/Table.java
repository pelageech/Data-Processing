package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.List;

public class Table {
    public static final int CAPACITY = 5;
    private final List<Fork> forks = new ArrayList<>(CAPACITY);
    private final List<Philosopher> philosophers = new ArrayList<>(CAPACITY);

    public Table() {
        for (int i = 0; i < CAPACITY; i++) {
            forks.add(new Fork());
        }

        for (int i = 0; i < CAPACITY; i++) {
            philosophers.add(new Philosopher(
                    "Phil " + i,
                    10,
                    forks.get(i),
                    forks.get((i+1) % CAPACITY)
            ));
        }
    }
    public void start() throws InterruptedException {
        for (var ph : philosophers) {
            ph.start();
        }
        for (var ph : philosophers) {
            ph.join();
        }
    }
}
