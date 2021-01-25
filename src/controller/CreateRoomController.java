package controller;

import Server.CAHClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import logic.CAHParser;
import logic.GraphicHandler;
import logic.Room;
import sample.DBConnector;

import java.sql.SQLException;
import java.util.ArrayList;

public class CreateRoomController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField decksIDField;

    @FXML
    private TextField portField;

    @FXML
    void createRoom(ActionEvent event) throws SQLException {
        /*if ( !portField.getText().matches(CAHParser.IPV4_PATTERN) ) {
            Alert alert = new Alert( Alert.AlertType.ERROR, "IP non valido!", ButtonType.OK);
            alert.showAndWait();
        } else {*/
        CAHClient.abilitato = true;
        int arrayIdMazzi[] = CAHParser.parseDeck(decksIDField.getText());


        boolean isIdGood = false;
        for(int i = 0; i < arrayIdMazzi.length; i++)
            if(DBConnector.getInstance().getGiocabileDiMazzo(arrayIdMazzi[i])) {
                isIdGood = true;
                System.out.println("Controlla se il mazzo esiste.");
            }

        if(isIdGood) {
            Room.createRoom(nameField.getText(), Integer.parseInt(portField.getText()), arrayIdMazzi);
            GraphicHandler.displayScreen(GraphicHandler.PLAY_SCREEN, GraphicHandler.NO_STREAM);
        }
    }

    @FXML
    void previousScreen(ActionEvent event) {
        GraphicHandler.displayScreen( GraphicHandler.MAIN_SCREEN, GraphicHandler.NO_STREAM );
    }

}