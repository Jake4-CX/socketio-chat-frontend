package lat.jack.socketiochatfrontend.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class User {
    @JsonProperty("socket_id")
    private String socketId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("connected_at")
    private Instant connectedAt;

    // Default constructor (required for Jackson)
    public User() {}

    public String getSocketId() {
        return socketId;
    }

    public String getUsername() {
        return username;
    }

    public Instant getConnectedAt() {
        return connectedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "socketId='" + socketId + '\'' +
                ", username='" + username + '\'' +
                ", connectedAt=" + connectedAt +
                '}';
    }
}
