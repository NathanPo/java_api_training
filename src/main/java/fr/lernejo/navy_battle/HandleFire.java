package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.model.Fire;

import java.io.IOException;
import java.net.URISyntaxException;


import static java.lang.System.exit;

public class HandleFire implements HttpHandler {
    private final HandleRequest hr = new HandleRequest();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HandleServer serv;

    public HandleFire(HandleServer handleServer) { this.serv = handleServer; }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) { hr.notFound(exchange); }
        String cell = exchange.getRequestURI().getQuery().split("cell=")[1];
        String consequence = serv.map.isTouched(cell);
        Fire fire = new Fire(serv.map.isAlive(), consequence);
        String response = objectMapper.writeValueAsString(fire);
        hr.sendResponse(exchange, response, true);
        if (serv.map.isAlive()) {
            try { serv.fire(); } catch (URISyntaxException e) { e.printStackTrace(); } catch (InterruptedException e) { e.printStackTrace(); }
        } else { exit(0); }
    }
}
