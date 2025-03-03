package lat.jack.socketiochatfrontend.Managers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lat.jack.socketiochatfrontend.Main;

import java.io.IOException;
import java.util.Objects;

public class ApplicationManager {

    private static Main main;

    public ApplicationManager(Main main) {

        this.main = main;

        System.out.println("ApplicationManager initialized!");
    }

    public static Main getMain() { return main; }

    public static void switchScene(String fxmlPath, Node node) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxmlPath)));
            Stage stage = (Stage) node.getScene().getWindow(); // Get stage from node
            stage.setScene(new Scene(root, 530, 355));

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public static void switchScene(String fxmlPath, Node node, int width, int height) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxmlPath)));
            Stage stage = (Stage) node.getScene().getWindow(); // Get stage from node
            stage.setScene(new Scene(root, width, height));

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public static void switchScene(String fxmlPath, Stage stage) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxmlPath)));
            stage.setScene(new Scene(root, 520, 820));

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}
