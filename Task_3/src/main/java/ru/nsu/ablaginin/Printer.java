package ru.nsu.ablaginin;

public class Printer {
    public void print(String message) {
        synchronized (message) {
            System.out.println(message);
            message.notify();
        }
    }
}
