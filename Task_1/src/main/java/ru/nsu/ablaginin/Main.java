package ru.nsu.ablaginin;

public class Main {
    public static void main(String[] args) {
        var th = new Printer();
        th.start();

        for (int i = 0; i < 10; i++) {
            System.out.println("This is a parent! Message: " + i);
        }
    }
}
