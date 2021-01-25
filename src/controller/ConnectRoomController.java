package controller;

import Server.CAHClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import logic.GraphicHandler;

import java.io.IOException;

public class ConnectRoomController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    void connectToRoom(ActionEvent event) throws IOException {
        CAHClient.nome = nameField.getText();
        GraphicHandler.displayScreen( GraphicHandler.PLAY_SCREEN, GraphicHandler.OPEN_STREAM );
        CAHClient.getClient().connect(5000, ipField.getText(), Integer.parseInt(portField.getText()));
    }

    @FXML
    void previousScreen(ActionEvent event) {
        GraphicHandler.displayScreen(GraphicHandler.MAIN_SCREEN, GraphicHandler.NO_STREAM);
    }

}