package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.GraphicHandler;
import logic.Room;

public class CreateRoomController {

    @FXML
    private TextField roomNameField;

    @FXML
    private TextField decksIDField;

    @FXML
    private Button backButton;

    @FXML
    private TextField ipField;

    @FXML
    private Button confirmButton;

    @FXML
    void createRoom(ActionEvent event) {
        Room.createRoom(roomNameField.getText(), ipField.getText(), decksIDField.getText());
    }

    @FXML
    void previousScreen(ActionEvent event) {
        GraphicHandler.displayScreen( GraphicHandler.MAIN_SCREEN, GraphicHandler.NO_STREAM );
    }



}