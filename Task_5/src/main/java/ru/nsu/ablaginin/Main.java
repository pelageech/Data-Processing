package ru.nsu.ablaginin;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        var th = new Printer();

        Timer timer = new Timer();

        th.start();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                th.interrupt();
            }
        }, 2000);
    }
}
