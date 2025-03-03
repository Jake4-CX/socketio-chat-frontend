package lat.jack.socketiochatfrontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lat.jack.socketiochatfrontend.Controllers.LoginController;
import lat.jack.socketiochatfrontend.Managers.ApplicationManager;

public class Main extends Application {

    private Scene scene;
    private Stage stage;

    private LoginController loginController;

    static ApplicationManager applicationManager;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        applicationManager = new ApplicationManager(this);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        this.scene = new Scene(loader.load(), 530, 355);
        this.loginController = loader.getController();

        this.stage = primaryStage;


        primaryStage.setResizable(false);
        primaryStage.setTitle("Socket.IO Chat Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
