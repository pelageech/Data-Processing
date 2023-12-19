package ru.nsu.ablaginin;

import javax.xml.stream.XMLStreamException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static final String fileName = "people.xml";
    private static final String outFileName = "result.txt";

    public static void main(String[] args) throws IOException, XMLStreamException {
        var input = Main.class.getClassLoader().getResourceAsStream(fileName);

        BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
        writer.append(new PeopleParserProcessor(input)
                .parse().toString());
        writer.close();
//
//        new PeopleParserProcessor(new FileInputStream(file))
//                .parse()
        //.forEach(System.out::println)
        ;
    }
}