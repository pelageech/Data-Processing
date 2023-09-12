package ru.nsu.ablaginin;

public class Main {
    public static void main(String[] args) {
        var th1 = new Printer("Privet1", "Vsem1", "Ya1", "Nitka1", "Jaba");
        var th2 = new Printer("Privet2", "Vsem2", "Ya2", "Nitka2", "Teraria");
        var th3 = new Printer("Privet3", "Vsem3", "Ya3", "Nitka3", "Moloko");
        var th4 = new Printer("Privet4", "Vsem4", "Ya4", "Nitka4", "Yabloko");

        th1.start();
        th2.start();
        th3.start();
        th4.start();
    }
}
