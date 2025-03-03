package lat.jack.socketiochatfrontend.Managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lat.jack.socketiochatfrontend.Models.AuthenticatedResponse;
import lat.jack.socketiochatfrontend.Models.User;

import java.net.URI;
import java.util.function.Consumer;

public class SocketManager {

    private static SocketManager instance;
    private Socket socket;

    private static User currentUser;
    private static String currentRoom;

    private String[] availableRooms;

    private Consumer<User> onAuthenticatedCallback;

    private SocketManager() {
        try {
            System.out.println("SocketManager created!");
            URI uri = URI.create("http://localhost:8080/");
            IO.Options options = IO.Options.builder()
                    .setUpgrade(true)
                    .setTransports(new String[]{"websocket"})
                    .build();

            socket = IO.socket(uri, options);

            socket.connect();

            socket.on(Socket.EVENT_CONNECT, (socketEvent) -> {
                System.out.println("Socket connected!");
            });

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            socket.on("authenticated", new Emitter.Listener() {
                @Override
                public void call(Object... objects) { // objects: [{"socket_id":"9c99f128-497b-45c2-896f-57c9d90ac080","connected_at":"2025-03-03T14:30:06.8881469Z","username":"ds"}]
                    try {
                        if (objects.length > 0) {
                            AuthenticatedResponse authenticatedResponse = objectMapper.readValue(objects[0].toString(), AuthenticatedResponse.class);
                            currentUser = authenticatedResponse.getUser();
                            availableRooms = authenticatedResponse.getRooms();

                            System.out.println("Authenticated User: " + currentUser);

                            if (onAuthenticatedCallback != null) {
                                onAuthenticatedCallback.accept(currentUser);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

//            socket.on("error", new Emitter.Listener() {
//                        @Override
//                        public void call(Object... objects) {
//                            System.out.println("Error: " + objects[0]);
//                        }
//            });

            socket.on(Socket.EVENT_DISCONNECT, (socket) -> {
                System.out.println("Socket disconnected!");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized SocketManager getInstance() {
        if (instance == null) {
            instance = new SocketManager();
        }
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String[] getAvailableRooms() {
        return availableRooms;
    }

    public void setOnAuthenticatedCallback(Consumer<User> callback) {
        this.onAuthenticatedCallback = callback;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        SocketManager.currentRoom = currentRoom;
    }
}
