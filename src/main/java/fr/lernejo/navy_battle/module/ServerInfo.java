package fr.lernejo.navy_battle.module;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public class ServerInfo {
    @NotNull
    private final String id;
    @NotNull
    private final String url;
    @NotNull
    private final String message;

    @JsonCreator
    public ServerInfo(@JsonProperty("id") String id,
                      @JsonProperty("url") String url,
                      @JsonProperty("message") String message) {
        this.id = id;
        this.url = url;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ServerInfo {" +
            "id='" + id + '\'' +
            ", url='" + url + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
