package ru.nsu.ablaginin;

public class Main {
    public static void main(String[] args) {
        CubbyHole cubbyHole = new CubbyHole();
        var th = new Printer(cubbyHole);
        th.start();

        for (int i = 10; i < 20; i++) {
            cubbyHole.printFirst(String.valueOf(i));
        }
    }
}
