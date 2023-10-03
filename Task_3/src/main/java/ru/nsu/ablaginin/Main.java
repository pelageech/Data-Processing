package ru.nsu.ablaginin;

public class Main {
    static String s = "Thread 1: Hello!";
    public static void main(String[] args) throws InterruptedException {
        var Printer = new Printer();
        var th1 = new Thread(() -> Printer.print(s));
        synchronized (s) {
            th1.start();
            s.wait();
        }
        s = "Thread 2: Hello!";
        var th2 = new Thread(() -> Printer.print(s));
        synchronized (s) {
            th2.start();
            s.wait();
        }
        s = "Thread 3: Hello!";
        var th3 = new Thread(() -> Printer.print(s));
        synchronized (s) {
            th3.start();
            s.wait();
        }
        s = "Thread 4: Hello!";
        var th4 = new Thread(() -> Printer.print(s));
        th4.start();
    }
}
