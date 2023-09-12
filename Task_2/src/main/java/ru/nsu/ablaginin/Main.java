package ru.nsu.ablaginin;

public class Main {
    public static void main(String[] args) {
        var th = new Printer();
        th.start();
        try {
            th.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("This is a parent! Message: " + i);
        }
    }
}
