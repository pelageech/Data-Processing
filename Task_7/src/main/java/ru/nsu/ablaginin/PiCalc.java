package ru.nsu.ablaginin;

import java.util.concurrent.Callable;

public class PiCalc implements Callable<Double> {
    private final long start;
    private final long step;
    private final long iter;

    public PiCalc(long start, long step, long iter) {
        this.start = start;
        this.step = step;
        this.iter = iter;
    }

    @Override
    public Double call() {
        var d = 0.;

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
