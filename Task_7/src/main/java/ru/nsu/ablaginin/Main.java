package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {
    private static final long ITER = 100000000;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.exit(666);
        }

        var numThreads = Integer.parseInt(args[0]);

        var tasks = new ArrayList<FutureTask<Double>>();
        for (int i = 0; i < numThreads; i++) {
            tasks.add(new FutureTask<>(new PiCalc(i+1, numThreads, ITER)));
            (new Thread(tasks.get(i))).start();
        }

        var pi = 0.;
        try {
            for (var d : tasks) {
                pi += d.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("My Pi is " + pi*4);
        System.out.println("Mt pi is " + Math.PI);
        System.out.println("Delta is " + (Math.PI - pi*4));
    }
}
