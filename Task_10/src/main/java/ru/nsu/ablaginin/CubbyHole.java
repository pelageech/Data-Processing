package ru.nsu.ablaginin;

public class CubbyHole {
    private boolean available = true;
    public synchronized void printSecond(String s) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("Child: " + s);
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
        System.out.println("Parent: " + s);
        available = false;
        notifyAll();
    }
}
