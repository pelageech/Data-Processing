package ru.nsu.ablaginin;

public class Printer extends Thread {
    private boolean running = true;

    @Override
    public void interrupt() {
        running = false;
    }

    @Override
    public void run() {
        for (long i = 0; running; i++) {
            System.out.println("Message " + i);
        }
    }
}
