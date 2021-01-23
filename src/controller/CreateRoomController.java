package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import logic.GraphicHandler;
import logic.Room;

public class CreateRoomController {

    @FXML
    private TextField nameField;

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
        if ( !( ipField.getText().matches("[0-9].[0-9].[0-9].[0-9]") )) {
            Alert alert = new Alert( Alert.AlertType.ERROR, "IP non valido!", ButtonType.OK);
            alert.showAndWait();
        } else {
            Room.createRoom(nameField.getText(), ipField.getText(), decksIDField.getText());
            GraphicHandler.displayScreen( GraphicHandler.PLAY_SCREEN, GraphicHandler.NO_STREAM );
        }
    }

    @FXML
    void previousScreen(ActionEvent event) {
        GraphicHandler.displayScreen( GraphicHandler.MAIN_SCREEN, GraphicHandler.NO_STREAM );
    }



}