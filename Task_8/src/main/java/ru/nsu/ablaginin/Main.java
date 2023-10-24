package ru.nsu.ablaginin;

import sun.misc.Signal;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.exit(666);
        }

        var numThreads = Integer.parseInt(args[0]);
        var picalc = new Pi(numThreads);

        Signal signal = new Signal("INT");
        Signal.handle(signal, sig -> picalc.stop());

        Double pi = 0.;
        var execSigINT = Executors.newSingleThreadScheduledExecutor();
        execSigINT.schedule(() -> Signal.raise(signal), 10, TimeUnit.SECONDS);

        try {
            pi = picalc.start();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("My Pi is " + pi);
        System.out.println("Mt pi is " + Math.PI);
        System.out.println("Delta is " + (Math.PI - pi));
        execSigINT.shutdown();
    }
}
