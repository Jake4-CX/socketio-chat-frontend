package lat.jack.socketiochatfrontend.Events.Chat;

import io.socket.client.Socket;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import lat.jack.socketiochatfrontend.Controllers.ChatController;
import lat.jack.socketiochatfrontend.Managers.SocketManager;

public class onJoinRoomButtonClick implements EventHandler<MouseEvent> {

  private final ChatController chatController;

  public onJoinRoomButtonClick(ChatController chatController) {
    this.chatController = chatController;
  }


  @Override
  public void handle(MouseEvent mouseEvent) {

    SocketManager socketManager = SocketManager.getInstance();
    Socket socket = socketManager.getSocket();

    if (chatController.getSelectedRoom() != null) {
      socket.emit("joinRoom", chatController.getSelectedRoom());
      System.out.println("Joining room: " + chatController.getSelectedRoom());
    }

  }
}
