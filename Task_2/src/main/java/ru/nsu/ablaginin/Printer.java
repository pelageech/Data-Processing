package ru.nsu.ablaginin;

public class Printer extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("This is a child! Message: " + i);
        }
    }
}
