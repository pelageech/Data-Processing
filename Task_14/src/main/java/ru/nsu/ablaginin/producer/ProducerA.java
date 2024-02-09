package ru.nsu.ablaginin.producer;

import ru.nsu.ablaginin.product.A;

import java.util.concurrent.TimeUnit;

public class ProducerA extends Producer<A>{
    public static final int TIME = 1;
    private int id = 0;
    public ProducerA(Storage<A> storage) {
        super(storage);
    }

    @Override
    public void produce() throws InterruptedException {
        TimeUnit.SECONDS.sleep(TIME);
        put(new A(id++));
        System.out.printf("Produced A (id=%d)\n", id-1);
    }
}
