package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lernejo.navy_battle.module.ServerInfo;

public class Launcher {
    public static void main(String[] args) throws IOException {
        try {
            if (args.length == 0)
                System.exit(-1);
            final int port = Integer.parseInt(args[0]);
            new StartServer().startServer(port);

            if (args.length == 2) {
                ObjectMapper objectMapper = new ObjectMapper();
                ServerInfo myServ = new ServerInfo(UUID.randomUUID().toString(), "http://localhost:" + port, "Good Luck dude");
                String myServJson = objectMapper.writeValueAsString(myServ);
                HttpResponse<String> response = new HandleRequest().makePost(args[1], myServJson);
                ServerInfo remoteServer = objectMapper.readValue(response.body(), ServerInfo.class);
                System.out.println(remoteServer.toString());
            }
        } catch (NumberFormatException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

