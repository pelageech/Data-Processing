package ru.nsu.ablaginin;

public class Main {
    public static void main(String[] args) {
        var th = new Printer();

        th.start();
        try {
            th.join(2000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        th.interrupt();
    }
}
