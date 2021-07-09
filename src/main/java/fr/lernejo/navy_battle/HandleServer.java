package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.model.Fire;
import fr.lernejo.navy_battle.model.ServerInfo;

import static java.lang.System.exit;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;

public class HandleServer {
    public final BuildMap map = new BuildMap();
    private final HashMap<String, String> enemy = new HashMap<>();
    private final int port;
    private final HandleRequest hr = new HandleRequest();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public HandleServer(int port) throws IOException {
        InetSocketAddress addr = new InetSocketAddress("localhost", port);
        HttpServer server = HttpServer.create(addr, 0);
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.createContext("/ping", new Ping());
        server.createContext("/api/game/start", new StartGame(this));
        server.createContext("/api/game/fire", new HandleFire(this));
        server.start();
        this.port = port;
    }

    public void start(int port, String url) throws IOException, URISyntaxException, InterruptedException {
        ServerInfo myServ = new ServerInfo(UUID.randomUUID().toString(), "http://localhost:" + port, "Good Luck dude");
        HttpResponse<String> response = new HandleRequest().makePost(url, objectMapper.writeValueAsString(myServ));
        if (response.statusCode() == 202) {
            ServerInfo remoteServer = objectMapper.readValue(response.body(), ServerInfo.class);
            setEnemy("url", remoteServer.getUrl());
            setEnemy("id", remoteServer.getId());
        }
    }

    public HashMap<String, String> getEnemy() { return this.enemy; }
    public void setEnemy(String key, String value){ this.enemy.put(key, value); }
    public int getPort() { return this.port; }

    public void fire() throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> resp = hr.makeGet(getEnemy().get("url") + "/api/game/fire?cell=" + map.getRandomBoxes());
        if (resp.statusCode() == 202) {
            Fire enemyFire = objectMapper.readValue(resp.body(), Fire.class);
            if (!enemyFire.getShipLeft()) { exit(0); }
        }
    }
}
