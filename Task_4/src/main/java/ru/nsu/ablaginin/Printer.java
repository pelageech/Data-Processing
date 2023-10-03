package ru.nsu.ablaginin;

import java.util.concurrent.TimeUnit;

public class Printer extends Thread {
    public static final int tout = 333;

    @Override
    public void run() {
        try {
            for (long i = 0; ; i++) {
                TimeUnit.MILLISECONDS.sleep(tout);
                System.out.println("Message " + i);
            }
        } catch (InterruptedException ignored) {

        }
        System.out.println("INTERRUPTED");
    }
}
