package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PingTest {
    private final HttpClient client = HttpClient.newHttpClient();
    @Test
    public void testPing() throws Exception {
        int port = 1234;
        String[] args = new String[] { String.valueOf(port) };
        Launcher.main(args);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:" + port + "/ping")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("OK", response.body());
    }
}
