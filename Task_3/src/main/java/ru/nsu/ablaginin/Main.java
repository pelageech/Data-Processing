package ru.nsu.ablaginin;

public class Main {
    static String[] s = new String[]{"A", "B", "C", "D"};
    public static void main(String[] args) throws InterruptedException {
        var Printer = new Printer();
        var th1 = new Thread(() -> Printer.print(s));
        synchronized (s) {
            th1.start();
            s.wait();
        }
        s[0] = "Q";
        var th2 = new Thread(() -> Printer.print(s));
        synchronized (s) {
            th2.start();
            s.wait();
        }
        s[1] = "W";
        var th3 = new Thread(() -> Printer.print(s));
        synchronized (s) {
            th3.start();
            s.wait();
        }
        s[2] = "E";
        var th4 = new Thread(() -> Printer.print(s));
        th4.start();
    }
}
