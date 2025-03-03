package lat.jack.socketiochatfrontend.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class MessageResponse {

  @JsonProperty("user")
  private User user;

  @JsonProperty("message")
  private String message;

  @JsonProperty("processed_at")
  private Instant processedAt;

  public User getUser() {
    return user;
  }

  public String getMessage() {
    return message;
  }

  public Instant getProcessedAt() {
    return processedAt;
  }

  @Override
  public String toString() {
    return "MessageResponse{" +
            "user=" + user +
            ", message='" + message + '\'' +
            ", processedAt=" + processedAt +
            '}';
  }
}
