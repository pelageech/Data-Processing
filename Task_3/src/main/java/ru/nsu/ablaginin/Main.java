package ru.nsu.ablaginin;

import java.util.Arrays;

public class Main {
    static String[] s = new String[]{"A", "B", "C", "D"};
    public static void main(String[] args) {
        var th1 = new Printer(Arrays.stream(s).toList());
        th1.start();
        s[0] = "AA";
        var th2 = new Printer(Arrays.stream(s).toList());
            th2.start();
        s[1] = "BB";
        var th3 = new Printer(Arrays.stream(s).toList());
            th3.start();
        s[2] = "CC";
        var th4 = new Printer(Arrays.stream(s).toList());
        th4.start();
    }
}
