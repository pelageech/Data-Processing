package ru.nsu.ablaginin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static void waitUser() {
        System.out.println("\nEnter space to scroll down.");

        try {
            int key;
            do {
                key = System.in.read();
            } while (key != ' ' && key != -1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final String requestURL = "http://www.ccfit.nsu.ru/~kireev/";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
            .newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .uri(URI.create(requestURL))
            .build();

        AtomicInteger linesCount = new AtomicInteger();
        client
            .send(request, HttpResponse.BodyHandlers.ofLines())
            .body()
            .forEach(line -> {
                System.out.println(line);
                linesCount.getAndIncrement();

                if (linesCount.get() % 25 == 0) {
                    waitUser();
                }
            });
    }
}
