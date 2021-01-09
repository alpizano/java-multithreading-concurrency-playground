package org.example.sections.four.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ThroughputHttpServer {
    private static final String INPUT_FILE = "resources/war_and_peace.txt";
    private static final int NUMBER_OF_THREADS = 1;

    public static void main(String[] args) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
        startServer(text);
    }

    public static void startServer(String text) throws IOException {
        // create http server
        HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
        server.createContext("/search", new WordCountHandler(text));
        server.setExecutor(executor);
        server.start();
    }

    private static class WordCountHandler implements HttpHandler {
        private String text;

        public WordCountHandler(String text) {
            this.text=text;
        }


        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

        }
    }
}
