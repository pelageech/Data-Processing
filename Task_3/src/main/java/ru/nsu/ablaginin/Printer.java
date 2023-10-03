package ru.nsu.ablaginin;

import java.util.ArrayList;
import java.util.List;

public class Printer extends Thread{

    private final List<String> strings = new ArrayList<>();
    public Printer(List<String> strs) {
        strs.stream().map(String::valueOf).forEach(strings::add);
    }
    @Override
    public void run() {
        System.out.println(strings);
    }
}
