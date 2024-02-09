package ru.nsu.ablaginin.producer;

import ru.nsu.ablaginin.product.A;
import ru.nsu.ablaginin.product.B;
import ru.nsu.ablaginin.product.C;
import ru.nsu.ablaginin.product.Widget;

public class ProducerWidget extends Producer<Widget>{
    private final Storage<A> storageA;
    private final Storage<B> storageB;
    private final Storage<C> storageC;
    private int id = 0;
    public ProducerWidget(
            Storage<Widget> storage, 
            Storage<A> storageA, 
            Storage<B> storageB,
            Storage<C> storageC) {
        super(storage);
        this.storageA = storageA;
        this.storageB = storageB;
        this.storageC = storageC;
    }

    @Override
    void produce() throws InterruptedException {
        put(new Widget(id++, storageA.take(), storageB.take(), storageC.take()));
    }
}
