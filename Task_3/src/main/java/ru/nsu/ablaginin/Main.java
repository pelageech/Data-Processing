package ru.nsu.ablaginin;

public class Main {
    static String s = "Thread 1: Hello!";
    public static void main(String[] args) throws InterruptedException {
        var th1 = new Thread(() -> Printer.print(s));
        th1.start();
        th1.join();
        s = "Thread 2: Hello!";
        var th2 = new Thread(() -> Printer.print(s));
        th2.start();
        th2.join();
        s = "Thread 3: Hello!";
        var th3 = new Thread(() -> Printer.print(s));
        th3.start();
        th3.join();
        s = "Thread 4: Hello!";
        var th4 = new Thread(() -> Printer.print(s));
        th4.start();
        th4.join();
    }
}
