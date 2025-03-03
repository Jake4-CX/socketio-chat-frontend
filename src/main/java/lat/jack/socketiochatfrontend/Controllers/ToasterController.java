package lat.jack.socketiochatfrontend.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lat.jack.socketiochatfrontend.Main;
import lat.jack.socketiochatfrontend.Utils.TaskQueue;

import java.util.Timer;
import java.util.TimerTask;

public class ToasterController implements Toaster {

  public TaskQueue taskQueue = null;

  // Toast
  @FXML
  StackPane toasterPane;
  @FXML
  ImageView toasterImage;
  @FXML
  Text toasterText;

  @Override
  public void toaster(String message) {
    toasterText.setText(message);
    toasterPane.setVisible(true);
    toasterPane.setOpacity(1);
    toasterPane.setTranslateY(0);
    toasterPane.setDisable(false);

    toasterImage.setImage(new Image(String.valueOf(Main.class.getResource("Assets/Toasts/Black.png"))));

    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        toasterText.setText("");
        toasterPane.setOpacity(0);
        toasterPane.setTranslateY(-50);
        toasterPane.setDisable(true);
      }
    }, 2000);
  }

  public TaskQueue getTaskQueue() {
    return taskQueue;
  }
}
