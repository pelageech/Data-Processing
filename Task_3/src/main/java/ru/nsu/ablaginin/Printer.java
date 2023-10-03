package ru.nsu.ablaginin;

import java.util.Arrays;

public class Printer {
    public synchronized void print(String[] message) {
        synchronized (message) {
            System.out.println(Arrays.stream(message).toList());
            message.notify();
        }
    }
}
