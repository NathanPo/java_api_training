package fr.lernejo.navy_battle;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.module.ServerInfo;

public class Launcher {
    public static void main(String[] args) throws IOException {
        try {
            if (args.length == 0) {
                System.err.println("You forgot the PORT");
                System.exit(-1);
            }
            int port = Integer.parseInt(args[0]);
            InetSocketAddress addr = new InetSocketAddress(port);
            HttpServer server = HttpServer.create(addr, 0);
            server.createContext("/ping", new Ping());
            server.createContext("/api/game/start", new StartGame(port));
            // server.createContext("/api/game/fire", new HandleFire());
            server.setExecutor(Executors.newSingleThreadExecutor());
            server.start();
            System.out.println("Le serveur en ecoute sur le port: " + addr.getPort());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}

class StartGame implements HttpHandler {
    private final int port;
    public StartGame(int port) {
        this.port = port;
    }

    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            new HandleError().notFound(exchange);
        }
        String jsonString = "{\n" +
            "\"id\": \"2aca7611-0ae4-49f3-bf63-75bef4769028\",\n" +
            "\"url\": \"http://localhost:9876\",\n" +
            //"\"message\": \"May the best code win\"\n" +
            "}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ServerInfo hisServ = objectMapper.readValue(jsonString, ServerInfo.class);
        } catch (Exception e) {
            new HandleError().badRequest(exchange); // A VERIFIER !!!
        }

        ServerInfo myServ = new ServerInfo(UUID.randomUUID().toString(), "http://localhost:" + port, "Good Luck");
        String myServJson = objectMapper.writeValueAsString(myServ);

        String response = myServJson;
        exchange.getResponseHeaders().set("Content-type", "application/json");
        exchange.sendResponseHeaders(202, response.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(response.getBytes());
        }
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
