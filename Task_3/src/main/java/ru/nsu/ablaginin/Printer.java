package ru.nsu.ablaginin;

public class Printer extends Thread {
    private final String[] message;

    public Printer(String... message) {
        this.message = message;
    }

    @Override
    public void run() {
        for (var s : message) {
            System.out.println(s);
        }
    }
}
