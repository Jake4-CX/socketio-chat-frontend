module lat.jack.socketiochatfrontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires socket.io.client;
    requires engine.io.client;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.sql;

    opens lat.jack.socketiochatfrontend to javafx.fxml;
    exports lat.jack.socketiochatfrontend;
    exports lat.jack.socketiochatfrontend.Controllers;
    opens lat.jack.socketiochatfrontend.Controllers to javafx.fxml;
    opens lat.jack.socketiochatfrontend.Models to com.fasterxml.jackson.databind;
}