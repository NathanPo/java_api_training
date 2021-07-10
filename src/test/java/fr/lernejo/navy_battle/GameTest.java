package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

public class GameTest {
    @Test
    public void GameTest() throws Exception {
        String port = "4321";
        Launcher.main(new String[]{"9976"});
        Launcher.main(new String[]{port, "http://localhost:9976"});
    }
}
