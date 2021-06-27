package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.module.ServerInfo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

class StartGame implements HttpHandler {
    private final int port;
    private final HandleRequest hr = new HandleRequest();
    private final ObjectMapper objectMapper = new ObjectMapper();
    public StartGame(int port) {
        this.port = port;
    }

    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST"))
            hr.notFound(exchange);
        try {
            ServerInfo remoteServer = objectMapper.readValue(hr.getJsonString(exchange), ServerInfo.class);
            System.out.println(remoteServer.toString());
        } catch (Exception e) {
            hr.badRequest(exchange);
        }

        ServerInfo myServ = new ServerInfo(UUID.randomUUID().toString(), "http://localhost:" + port, "Good Luck");
        String response = objectMapper.writeValueAsString(myServ);
        exchange.getResponseHeaders().set("Content-type", "application/json");
        exchange.sendResponseHeaders(202, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
