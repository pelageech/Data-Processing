package ru.nsu.ablaginin;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        var th = new Printer();
        var e = Executors.newSingleThreadScheduledExecutor();

        th.start();
        e.schedule(th::interrupt, 2, TimeUnit.SECONDS);
        e.shutdown();
    }
}
