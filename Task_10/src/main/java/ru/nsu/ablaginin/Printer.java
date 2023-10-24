package ru.nsu.ablaginin;

public class Printer extends Thread {
    private final CubbyHole hole;

    public Printer(CubbyHole cubbyHole) {
        hole = cubbyHole;
    }
    @Override
    public void run() {
        for (int i = 10; i < 20; i++) {
            hole.printSecond(String.valueOf(i));
        }
    }
}
