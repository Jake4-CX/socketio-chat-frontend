package lat.jack.socketiochatfrontend.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lat.jack.socketiochatfrontend.Events.Login.onConnectButtonClick;
import lat.jack.socketiochatfrontend.Utils.TaskQueue;


public class LoginController extends ToasterController {

    @FXML
    private Button buttonConnect;

    @FXML
    private TextField inputUsername;

    // Toast
    @FXML
    StackPane toasterPane;
    @FXML
    ImageView toasterImage;
    @FXML
    Text toasterText;

    @FXML
    protected void initialize() {
        System.out.println("LoginView initialized!");

        this.taskQueue = new TaskQueue(this);

        buttonConnect.addEventFilter(MouseEvent.MOUSE_PRESSED, new onConnectButtonClick(this));

    }

    public TextField getInputUsername() {
        return inputUsername;
    }
}