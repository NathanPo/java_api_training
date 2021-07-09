package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.model.ServerInfo;
import java.io.IOException;
import java.util.UUID;

class StartGame implements HttpHandler {
    private final HandleRequest hr = new HandleRequest();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HandleServer serv;

    public StartGame(HandleServer handleServer) {
        this.serv = handleServer;
    }

    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST"))
            hr.notFound(exchange);
        try {
            ServerInfo remoteServer = objectMapper.readValue(hr.getJsonString(exchange), ServerInfo.class);
            this.serv.setEnemy("url", remoteServer.getUrl());
            this.serv.setEnemy("id", remoteServer.getId());
            ServerInfo myServ = new ServerInfo(UUID.randomUUID().toString(), "http://localhost:" + serv.getPort(), "Good Luck");
            String response = objectMapper.writeValueAsString(myServ);
            hr.sendResponse(exchange, response, true);
            this.serv.fire();
        } catch (Exception e) {
            hr.badRequest(exchange);
        }
    }
}

