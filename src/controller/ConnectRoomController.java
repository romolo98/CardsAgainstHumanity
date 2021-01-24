package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import logic.GraphicHandler;

public class ConnectRoomController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField ipField;

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