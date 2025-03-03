package lat.jack.socketiochatfrontend.Events.Chat;

import io.socket.client.Socket;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import lat.jack.socketiochatfrontend.Controllers.ChatController;
import lat.jack.socketiochatfrontend.Managers.SocketManager;

public class onSendMessageButtonClick implements EventHandler<MouseEvent> {

  private final ChatController chatController;

  public onSendMessageButtonClick(ChatController chatController) {
    this.chatController = chatController;
  }

  @Override
  public void handle(MouseEvent mouseEvent) {
    System.out.println("Send message button clicked!");

    String message = chatController.getInputMessage().getText().trim();
    if (!message.isEmpty()) {
      // Send Message
      chatController.getInputMessage().clear();

      SocketManager socketManager = SocketManager.getInstance();
      Socket socket = socketManager.getSocket();

      socket.emit("sendMessage", message);
    }
  }
}
