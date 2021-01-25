package controller;

import Server.CAHClient;
import Server.Messaggio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import logic.CAHParser;
import logic.Room;
import org.supercsv.cellprocessor.ParseInt;

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
    private ListView<String> playersList;

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
        /*if ( !Room.isGameStarted() ) {
            Room.setHighscore( Integer.parseInt( highscoreField.getText() ) );
        } else chatWall.appendText( "Non puoi settare il punteggio durante la partita."+'\n');*/
        this.updatePlayersList();
    }

    @FXML
    void exitGame(ActionEvent event) {

    }

    @FXML
    void startGame(ActionEvent event) {
        if ( !Room.playerNumber() ) {
            chatWall.appendText( "Giocatori insufficienti!");
        }else if ( !Room.isScoreSet() ) {
            chatWall.appendText( "Non hai impostato un punteggio massimo!");
        } //else Room.startGame();
    }

    public void sendMessageToChatWall(String messaggio){
        chatWall.appendText(messaggio);
        System.out.println("sono entrato");
    }

    public void updatePlayersList () {
        if ( playersList == null )        playersList = new ListView<String>();
        playersList.setItems(FXCollections.observableArrayList("Row 1", "Row 2", "Long Row 3", "Row 4", "Row 5", "Row 6", "Row 7",
                "Row 8", "Row 9", "Row 10", "Row 11", "Row 12", "Row 13", "Row 14", "Row 15", "Row 16", "Row 17", "Row 18",
                "Row 19", "Row 20", "Row 21", "Row 22", "Row 23", "Row 24", "Row 25"));
        playersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        playersList.setOrientation(Orientation.VERTICAL);
        System.out.println("update");

    }

}