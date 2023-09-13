package ru.nsu.ablaginin;

import java.util.concurrent.Callable;

public class PiCalc implements Callable<Double> {
    private long start;
    private long step;
    private long iter;

    public PiCalc(long start, long step, long iter) {
        this.start = start;
        this.step = step;
        this.iter = iter;
    }

    @Override
    public Double call() {
        double d = 0;

        var k = start;
        for (int i = 0; i < iter; i++) {
            var delta = 1. / (2*k-1);
            delta = (k & 1) == 0 ? -delta : delta;
            d += delta;
            k += step;
        }
        return d;
    }
}
