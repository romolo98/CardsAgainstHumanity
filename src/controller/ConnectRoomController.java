package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.GraphicHandler;
import logic.Room;

public class ConnectRoomController {

    @FXML
    private Button backButton;

    @FXML
    private TextField ipField;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField portField;

    @FXML
    void connectToRoom(ActionEvent event) {
        GraphicHandler.displayScreen( GraphicHandler.PLAY_SCREEN, GraphicHandler.NO_STREAM );
    }

    @FXML
    void previousScreen(ActionEvent event) {
        GraphicHandler.displayScreen(GraphicHandler.MAIN_SCREEN, GraphicHandler.NO_STREAM);
    }

}