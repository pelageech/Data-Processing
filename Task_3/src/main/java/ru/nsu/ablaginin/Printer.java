package ru.nsu.ablaginin;

public class Printer {
    public synchronized void print(String message) {
        var m = String.valueOf(message);
        System.out.println(m);
    }
}
