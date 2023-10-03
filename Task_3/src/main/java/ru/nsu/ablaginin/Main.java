package ru.nsu.ablaginin;

public class Main {
    static String[] s = new String[]{"A", "B", "C", "D"};
    public static void main(String[] args) throws InterruptedException {
        var Printer = new Printer();
        var th1 = new Thread(() -> Printer.print(s));
            th1.start();
        s[0] = "B";
        var th2 = new Thread(() -> Printer.print(s));
            th2.start();
        s[1] = "C";
        var th3 = new Thread(() -> Printer.print(s));
            th3.start();
        s[2] = "D";
        var th4 = new Thread(() -> Printer.print(s));
        th4.start();
    }
}
