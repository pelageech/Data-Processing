package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Pi {
    private final CyclicBarrier barrier;
    private final ExecutorService executor;
    public static final int BARRIER_WAIT_COND = 70000000;
    private boolean sigint = false;
    private volatile boolean working = true;
    private final int parties;

    public Pi(int parties) {
        this.parties = parties;

        executor = Executors.newFixedThreadPool(parties);
        barrier = new CyclicBarrier(parties, new BarrierExit());
    }

    public Double start() throws ExecutionException, InterruptedException {
        Double pi = 0.;

        List<Future<Double>> tasks = new ArrayList<>();

        for (int i = 0; i < parties; i++) {
            var task = new PiCalc(i+1, parties);
            var future = executor.submit(task);
            tasks.add(future);
        }

        for (var fut : tasks) {
            pi += fut.get();
        }
        executor.shutdown();
        System.out.println("Calculated!");
        return pi*4;
    }

    public void stop() {
        System.out.println("Waiting for finishing calculating...");
        this.sigint = true;
    }

    private class PiCalc implements Callable<Double> {
        private final long start;
        private final long step;

        public PiCalc(long start, long step) {
            this.start = start;
            this.step = step;
        }

        @Override
        public Double call() throws BrokenBarrierException, InterruptedException {
            var d = 0.;

            var k = start;
            for (long i = 0; working; i++) {
                var delta = 1. / (2*k-1);
                delta = (k & 1) == 0 ? -delta : delta;
                d += delta;
                k += step;
                if (i % BARRIER_WAIT_COND == 0) {
                    barrier.await();
                }
            }
            return d;
        }
    }

    private class BarrierExit implements Runnable {

        @Override
        public void run() {
            if (sigint) {
                working = false;
            }
        }
    }

}
