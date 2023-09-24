package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static final long ITER = 1000000000;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.exit(666);
        }

        var numThreads = Integer.parseInt(args[0]);
        var executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Double>> tasks = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            var task = new PiCalc(i+1, numThreads, ITER);
            var future = executor.submit(task);
            tasks.add(future);
        }

        var pi = 0.;
        for (var fut : tasks) {
            try {
                pi += fut.get();
            } catch (ExecutionException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        pi *= 4;

        System.out.println("My Pi is " + pi);
        System.out.println("Mt pi is " + Math.PI);
        System.out.println("Delta is " + (Math.PI - pi));
        executor.shutdown();
    }
}
