package lat.jack.socketiochatfrontend.Events.Login;

import io.socket.client.Socket;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import lat.jack.socketiochatfrontend.Controllers.LoginController;
import lat.jack.socketiochatfrontend.Managers.ApplicationManager;
import lat.jack.socketiochatfrontend.Managers.SocketManager;

public class onConnectButtonClick implements EventHandler<MouseEvent> {

    private final LoginController loginController;

    public onConnectButtonClick(LoginController loginController) {
        this.loginController = loginController;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        System.out.println("Login button clicked!");

        String userName = loginController.getInputUsername().getText();

        System.out.println(userName);

        loginController.getTaskQueue().addToQueue("Username: " + userName);

        SocketManager socketManager = SocketManager.getInstance();

        socketManager.setOnAuthenticatedCallback(user -> {
            Platform.runLater(() -> {
                ApplicationManager.switchScene("chat-view.fxml", (Node) mouseEvent.getSource());
            });
        });

        Socket socket = socketManager.getSocket();

        socket.emit("authenticate", userName);
    }
}
