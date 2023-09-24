package ru.nsu.ablaginin;

public class CubbyHole {
    private boolean available = false;
    public synchronized void printSecond(String s) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("first: " + s);
        available = true;
        notifyAll();
    }

    public synchronized void printFirst(String s) {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("second: " + s);
        available = false;
        notifyAll();
    }
}
