package ru.nsu.ablaginin;

import java.util.Arrays;

public class Printer {
    public synchronized void print(String[] message) {
        String[] m;
        m = message.clone();
        for (int i = 0; i < m.length; i++) {
            m[i] = String.valueOf(m[i]);
        }
        System.out.println(Arrays.stream(message).toList());
    }
}
