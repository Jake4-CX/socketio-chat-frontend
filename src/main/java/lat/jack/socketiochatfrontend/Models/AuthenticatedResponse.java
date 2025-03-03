package lat.jack.socketiochatfrontend.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class AuthenticatedResponse {

  @JsonProperty("user")
  private User user;

  @JsonProperty("rooms")
  private String[] rooms;

  public User getUser() {
    return user;
  }

  public String[] getRooms() {
    return rooms;
  }

  @Override
  public String toString() {
    return "AuthenticatedResponse{" +
            "user=" + user +
            ", rooms=" + Arrays.toString(rooms) +
            '}';
  }
}
