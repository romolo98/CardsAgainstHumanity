package controller;

import Server.CAHClient;
import Server.CAHServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
        if (CAHClient.id_connessioni.size() > 0) {
            PlayScreenController playScreenController = GraphicHandler.displayScreen(GraphicHandler.PLAY_SCREEN, GraphicHandler.OPEN_STREAM).getController();
            CAHClient.getClient().start();
            CAHClient.getClient().connect(5000, ipField.getText(), Integer.parseInt(portField.getText()));
            CAHClient.abilitato = false;
            playScreenController.setHighscoreVisible(false);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nessuno ha Creato la Partita Richiesta", ButtonType.OK);
            alert.showAndWait();
        }

        //playScreenController.setCursor();

    }

    @FXML
    void previousScreen(ActionEvent event) {
        GraphicHandler.displayScreen(GraphicHandler.MAIN_SCREEN, GraphicHandler.NO_STREAM);
    }

}