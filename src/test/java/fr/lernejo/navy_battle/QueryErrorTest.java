package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryErrorTest {
    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    public void NotFound() throws Exception {
        int port = 12345;
        String[] args = new String[]{String.valueOf(port)};
        Launcher.main(args);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:" + port + "/api/game/start")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("Not Found", response.body());
    }

    @Test
    public void badRequest() throws Exception {
        int port = 12341;
        String[] args = new String[]{String.valueOf(port)};
        Launcher.main(args);
        HttpResponse<String> response = new HandleRequest().makePost("http://localhost:" + port + "/api/game/start", "");
        assertEquals("Bad Request", response.body());
    }
}
