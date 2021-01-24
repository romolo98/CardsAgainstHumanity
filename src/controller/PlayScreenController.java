package controller;

import Server.CAHClient;
import Server.Messaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class PlayScreenController {

    @FXML
    private HBox handCardsBox;

    @FXML
    private TextField highscoreField;

    @FXML
    private TextField chatField;

    @FXML
    private TextArea chatWall;

    @FXML
    private ListView<?> playersList;

    @FXML
    private HBox playedCardsBox;

    @FXML
    private AnchorPane blackCardBox;

    @FXML
    void sendMessage(ActionEvent event) {
        chatWall.appendText(chatField.getText()+'\n');
        Messaggio m = new Messaggio();
        m.testo = chatField.getText()+"\n";
        chatField.setText("");
        CAHClient.getClient().sendTCP(m);
    }

    @FXML
    void confirmCard(ActionEvent event) {

    }

    @FXML
    void setHighscore(ActionEvent event) {

    }

    @FXML
    void exitGame(ActionEvent event) {

    }

    @FXML
    void startGame(ActionEvent event) {

    }

    public void sendMessageToChatWall(String messaggio){
        chatWall.appendText(messaggio);
        System.out.println("sono entrato");
    }

}