package sample;

import Server.CAHClient;
import Server.Messaggio;
import com.esotericsoftware.kryonet.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class Controller {

    @FXML
    private Button playButton, createButton, optionsButton, turnBack, sendMessage;

    @FXML
    private TextField chatField;

    @FXML
    private static TextArea chatWall;

    public void ActionCreatebutton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateRoomScreen.fxml"));
        createButton.getScene().setRoot(root);
    }

    public void ActionPlayButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PlayScreen.fxml"));
        playButton.getScene().setRoot(root);
    }

    public void ActionOptionsButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("OptionScreen.fxml"));
        optionsButton.getScene().setRoot(root);
    }

    public void ActionTurnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        turnBack.getScene().setRoot(root);
    }

    public void ActionCreateRoomButton(ActionEvent actionEvent) {
    }

    public void ActionSendButton(ActionEvent actionEvent) {
        chatWall.appendText(chatField.getText()+"\n");
        Messaggio m = new Messaggio();
        m.testo = chatField.getText()+"\n";
        CAHClient.getInstance().getClient().sendTCP(m);
    }

    public void isKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ESCAPE){
            Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        }
    }

    public static TextArea getTextArea(){
        return chatWall;
    }

    public static void getMessage(){

    }

}