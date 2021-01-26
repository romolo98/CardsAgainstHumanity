package controller;

import Server.*;
import com.esotericsoftware.kryonet.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    private TextArea whiteCard1, whiteCard2, whiteCard3, whiteCard4, whiteCard5;

    @FXML
    private TextArea playerSlot1, playerSlot2, playerSlot3, playerSlot4;

    @FXML
    private Button confirmCard;

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

    private String myMove = null;

    private int moveSlot = 0;

    public void setCursor(){

    }

    public void setHighscoreVisible(Boolean check) {
        highscoreField.setVisible(check);
    }

    @FXML
    void sendMessage(ActionEvent event) {
        chatWall.appendText(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + " Io: "+chatField.getText()+'\n');
        Messaggio m = new Messaggio();
        m.testo = chatField.getText();
        chatField.setText("");
        CAHClient.getClient().sendTCP(m);
    }

    @FXML
    void confirmCard(ActionEvent event) {
        confirmCard.setDisable(true);
        Mossa m = new Mossa();
        m.mossa = myMove;
        CAHClient.getClient().sendTCP(m);

        switch (moveSlot){
            case 1:
                whiteCard1.setText("");
                break;
            case 2:
                whiteCard2.setText("");
                break;
            case 3:
                whiteCard3.setText("");
                break;
            case 4:
                whiteCard4.setText("");
                break;
            case 5:
                whiteCard5.setText("");
                break;
        }

    }

    @FXML
    void setHighscore(ActionEvent event) {
        /*if ( !Room.isGameStarted() ) { //SPOSTARE LA CHIAMATA SUL SERVER*/
            Room.setHighscore( Integer.parseInt( highscoreField.getText() ) );
            setHighscoreVisible(false);
            MaxScore score = new MaxScore();
            score.punteggioVittoria = Integer.parseInt(highscoreField.getText());
            CAHClient.getClient().sendTCP(score);
        //} else chatWall.appendText( "Non puoi settare il punteggio durante la partita."+'\n');*/
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
        }
        /*else if ( !Room.isScoreSet() ) {
            chatWall.appendText("Non hai impostato un punteggio massimo!\n");
        }else if (!Room.playerNumber()){
            chatWall.appendText("Giocatori insufficienti!\n");
        }*/
        else{
            Match m = new Match();
            m.m = "Literally whatever";
            CAHClient.getClient().sendTCP(m);

            /*Random r = new Random();
            int casuale = r.nextInt(Room.getNoCarteNere());
            blackCardSlot.setText(Room.getContenutoCarta(casuale));*/
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

    public void setBlackCardSlot(String blackCard){
        blackCardSlot.setText(blackCard);
    }

    public void setPlayable(String s){
        if (whiteCard1.getText().equals("")) {
            whiteCard1.setText(s);
            return;
        }
        else if (whiteCard2.getText().equals("")) {
            whiteCard2.setText(s);
            return;
        }
        else if (whiteCard3.getText().equals("")) {
            whiteCard3.setText(s);
            return;
        }
        else if (whiteCard4.getText().equals("")) {
            whiteCard4.setText(s);
            return;
        }
        else if (whiteCard5.getText().equals("")) {
            whiteCard5.setText(s);
            return;
        }
    }

    public void choice1(MouseEvent mouseEvent) {
        myMove = whiteCard1.getText();
        moveSlot = 1;
    }

    public void choice2(MouseEvent mouseEvent) {
        myMove = whiteCard2.getText();
        moveSlot = 2;
    }

    public void choice3(MouseEvent mouseEvent) {
        myMove = whiteCard3.getText();
        moveSlot = 3;
    }

    public void choice4(MouseEvent mouseEvent) {
        myMove = whiteCard4.getText();
        moveSlot = 4;
    }

    public void choice5(MouseEvent mouseEvent) {
        myMove = whiteCard5.getText();
        moveSlot = 5;
    }

    public TextArea getPlayerSlot1() {
        return playerSlot1;
    }

    public TextArea getPlayerSlot2() {
        return playerSlot2;
    }

    public TextArea getPlayerSlot3() {
        return playerSlot3;
    }

    public TextArea getPlayerSlot4() {
        return playerSlot4;
    }
}