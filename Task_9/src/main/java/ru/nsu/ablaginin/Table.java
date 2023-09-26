package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final List<Fork> forks = new ArrayList<>(5);
    private final List<Philosopher> philosophers = new ArrayList<>(5);

    public Table() {
        for (int i = 0; i < 5; i++) {
            forks.add(new Fork());
        }

        for (int i = 0; i < 5; i++) {
            philosophers.add(new Philosopher(
                    "Phil " + i,
                    4,
                    forks.get(i),
                    forks.get((i+1) % 5)
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
