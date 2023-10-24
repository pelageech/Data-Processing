package ru.nsu.ablaginin.producer;

import ru.nsu.ablaginin.product.C;

import java.util.concurrent.TimeUnit;

public class ProducerC extends Producer<C>{
    public static final int TIME = 3;
    private int id = 0;
    public ProducerC(Storage<C> storage) {
        super(storage);
    }

    @Override
    public void produce() throws InterruptedException {
        TimeUnit.SECONDS.sleep(TIME);
        put(new C(id++));
        System.out.printf("Produced C (id=%d)\n", id-1);
    }
}
