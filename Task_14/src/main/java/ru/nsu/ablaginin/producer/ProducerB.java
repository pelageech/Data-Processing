package ru.nsu.ablaginin.producer;

import ru.nsu.ablaginin.product.B;

import java.util.concurrent.TimeUnit;

public class ProducerB extends Producer<B>{
    public static final int TIME = 2;
    private int id = 0;
    public ProducerB(Storage<B> storage) {
        super(storage);
    }

    @Override
    public void produce() throws InterruptedException {
        TimeUnit.SECONDS.sleep(TIME);
        put(new B(id++));
        System.out.printf("Produced B (id=%d)\n", id-1);
    }
}
