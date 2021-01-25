package controller;

import Server.CAHClient;
import Server.Messaggio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import logic.CAHParser;
import logic.GraphicHandler;
import logic.Room;
import org.supercsv.cellprocessor.ParseInt;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class PlayScreenController {

    @FXML
    private HBox handCardsBox;

    @FXML
    private TextArea whiteCard1;

    @FXML
    private TextField highscoreField;

    @FXML
    private TextArea blackCardSlot;

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

    public void setCursor(){

    }

    public void setHighscoreVisible(Boolean check) {
        highscoreField.setVisible(check);
    }

    @FXML
    void sendMessage(ActionEvent event) {
        chatWall.appendText(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + " io: "+chatField.getText()+'\n');
        Messaggio m = new Messaggio();
        m.testo = chatField.getText();
        chatField.setText("");
        CAHClient.getClient().sendTCP(m);
    }

    @FXML
    void confirmCard(ActionEvent event) {

    }

    @FXML
    void setHighscore(ActionEvent event) {
        /*if ( !Room.isGameStarted() ) { //SPOSTARE LA CHIAMATA SUL SERVER
            Room.setHighscore( Integer.parseInt( highscoreField.getText() ) );
        } else chatWall.appendText( "Non puoi settare il punteggio durante la partita."+'\n');*/
    }

    @FXML
    void exitGame(ActionEvent event) {
        GraphicHandler.displayScreen( GraphicHandler.MAIN_SCREEN, GraphicHandler.NO_STREAM );
        CAHClient.getClient().stop();
    }

    @FXML
    void startGame(ActionEvent event) {
        if ( CAHClient.abilitato == false  ) { // SPOSTARE LA CHIAMATA SUL SERVER
            chatWall.appendText("Non hai i privilegi per iniziare una partita!\n");
        }else if ( !Room.isScoreSet() ) {
            chatWall.appendText( "Non hai impostato un punteggio massimo!\n");
        }else if (!Room.playerNumber()){
            chatWall.appendText("Giocatori insufficienti!\n");
        }else{
            Random r = new Random();
            int casuale = r.nextInt(Room.getNoCarteNere());
            blackCardSlot.setText(Room.getContenutoCarta(casuale));
        }

    }

    public void updatePlayersList ( ArrayList<String> players ) {
        if ( playersList == null )        playersList = new ListView<String>();
        playersList.setItems(FXCollections.observableArrayList( players ));
        playersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        playersList.setOrientation(Orientation.VERTICAL);
    }

    public void sendMessageToChatWall ( String message ) {
        chatWall.appendText( message + "\n");
    }
}