package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class StartServer {
    public void startServer(int port) throws IOException {
        InetSocketAddress addr = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(addr, 0);
        server.createContext("/ping", new Ping());
        server.createContext("/api/game/start", new StartGame(port));
        server.createContext("/api/game/fire", new HandleFire());
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.start();
    }
}
