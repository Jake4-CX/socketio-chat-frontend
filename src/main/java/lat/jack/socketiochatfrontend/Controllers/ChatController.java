package lat.jack.socketiochatfrontend.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.socket.client.Socket;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import lat.jack.socketiochatfrontend.Events.Chat.onJoinRoomButtonClick;
import lat.jack.socketiochatfrontend.Events.Chat.onSendMessageButtonClick;

import lat.jack.socketiochatfrontend.Managers.SocketManager;
import lat.jack.socketiochatfrontend.Models.MessageResponse;
import lat.jack.socketiochatfrontend.Utils.TaskQueue;

import java.util.Arrays;
import java.util.Objects;

public class ChatController extends ToasterController {

    // Rooms
    @FXML
    private ListView listViewAvailableRooms;
    @FXML
    private ScrollPane scrollPaneAvailableRooms;
    @FXML
    private Button buttonJoinRoom;
    @FXML
    private Button buttonLeaveRoom;

    private String selectedRoom = null;

    // Chat

    @FXML
    private VBox vboxChatMessages;

    @FXML
    private ScrollPane scrollPaneChatMessages;

    @FXML
    private TextField inputMessage;

    @FXML
    private Button buttonSendMessage;

    @FXML
    protected void initialize() {
        System.out.println("ChatView initialized!");

        this.taskQueue = new TaskQueue(this);

        SocketManager socketManager = SocketManager.getInstance();
        Socket socket = socketManager.getSocket();

        if (socketManager.getSocket() != null) {
            populateRoomList(socketManager.getAvailableRooms());
        }

        // disable "buttonSendMessage" button until the user has joined a room.
        buttonSendMessage.setDisable(true);

        buttonSendMessage.addEventFilter(MouseEvent.MOUSE_PRESSED, new onSendMessageButtonClick(this));
        buttonJoinRoom.addEventFilter(MouseEvent.MOUSE_PRESSED, new onJoinRoomButtonClick(this));
        buttonLeaveRoom.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            socket.emit("leaveRoom");
        });

        socket.on("leftRoom", _ -> {
            Platform.runLater(() -> { // Run on JavaFX thread when available
                socketManager.setCurrentRoom(null);
                buttonSendMessage.setDisable(true);
                vboxChatMessages.getChildren().clear();

                buttonJoinRoom.setDisable(false);
                buttonLeaveRoom.setDisable(true);
            });
        });

        socket.on("joinedRoom", args -> {
            String room = (String) args[0];

            Platform.runLater(() -> {
                socketManager.setCurrentRoom(room);
                buttonSendMessage.setDisable(false);

                buttonJoinRoom.setDisable(true);
                buttonLeaveRoom.setDisable(false);
            });
        });

        socket.on("error", args -> {
            String error = (String) args[0];
            System.out.println("Error: " + error);
            getTaskQueue().addToQueue(error);
        });

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        socket.on("message", args -> {
            try {
                if (args.length > 0) {
                    MessageResponse messageResponse = objectMapper.readValue(args[0].toString(), MessageResponse.class);
                    System.out.println(messageResponse);

                    Platform.runLater(() -> addMessage(messageResponse));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void addMessage(MessageResponse messageResponse) {

        SocketManager socketManager = SocketManager.getInstance();
        boolean isUserMessage = Objects.equals(socketManager.getCurrentUser().getSocketId(), messageResponse.getUser().getSocketId());

        Text text = new Text(messageResponse.getUser().getUsername() + ": " + messageResponse.getMessage());
        text.setWrappingWidth(260); // Ensures text wraps properly

        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(isUserMessage ?
                "-fx-background-color: #0078D7; -fx-text-fill: white; -fx-padding: 5px; -fx-background-radius: 5px;"
                : "-fx-background-color: #E5E5E5; -fx-text-fill: black; -fx-padding: 5px; -fx-background-radius: 5px;");

        vboxChatMessages.getChildren().add(textFlow);

        // Allow VBox to grow
        vboxChatMessages.setPrefHeight(Region.USE_COMPUTED_SIZE);
        vboxChatMessages.setMinHeight(Region.USE_PREF_SIZE);

        // Force ScrollPane to update
        scrollPaneChatMessages.layout();
        scrollPaneChatMessages.setVvalue(1.0);
    }

    private void populateRoomList(String[] rooms) {
        listViewAvailableRooms.getItems().clear();
        for (String room : rooms) {
            listViewAvailableRooms.getItems().add(room);
        }

        listViewAvailableRooms.setOnMouseClicked(e -> {
            selectedRoom = (String) listViewAvailableRooms.getSelectionModel().getSelectedItem();
        });
    }

    public TextField getInputMessage() {
        return inputMessage;
    }

    public String getSelectedRoom() {
        return selectedRoom;
    }
}
