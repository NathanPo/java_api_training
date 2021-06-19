package fr.lernejo.navy_battle;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Launcher {
    public static void main(String[] args) throws IOException {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            port = 9999;
        }
        InetSocketAddress addr = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(addr, 0);
        server.createContext("/ping", new Ping());
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.start();
        System.out.println("Le serveur en ecoute sur le port: " + addr.getPort());
    }
}

class Ping implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        String response = "OK";
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(response.getBytes());
        }
    }
}
