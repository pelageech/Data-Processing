package ru.nsu.ablaginin;

import ru.nsu.ablaginin.producer.*;
import ru.nsu.ablaginin.product.A;
import ru.nsu.ablaginin.product.B;
import ru.nsu.ablaginin.product.C;
import ru.nsu.ablaginin.product.Widget;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Storage<A> aStorage = new Storage<>();
        Storage<B> bStorage = new Storage<>();
        Storage<C> cStorage = new Storage<>();
        Storage<Widget> widgetStorage = new Storage<>();

        var a = new Thread(new ProducerA(aStorage));
        a.start();
        var b = new Thread(new ProducerB(bStorage));
        b.start();
        var c = new Thread(new ProducerC(cStorage));
        c.start();
        var widget = new Thread(new ProducerWidget(widgetStorage, aStorage, bStorage, cStorage));
        widget.start();

        var printer = new Thread(() -> {
            for (;;) {
                try {
                    System.out.printf("Produced widget (id=%d)\n", widgetStorage.take().id());
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        printer.start();

        new Scanner(System.in).nextLine();
        a.interrupt();
        b.interrupt();
        c.interrupt();
        widget.interrupt();
        printer.interrupt();
    }
}
