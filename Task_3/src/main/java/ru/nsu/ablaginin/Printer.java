package ru.nsu.ablaginin;

public class Printer {
    public static synchronized void print(String message) {
        var m = String.valueOf(message);
        System.out.println(m);
    }
}
