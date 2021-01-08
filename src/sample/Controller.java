package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    Button playButton, createButton, optionsButton, turnBack;

    public void ActionPlayButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PlayScreen.fxml"));
        Stage window = (Stage)playButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void ActionCreatebutton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateRoomScreen.fxml"));
        Stage window = (Stage)createButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void ActionOptionsButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("OptionScreen.fxml"));
        Stage window = (Stage)optionsButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void ActionTurnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Stage window = (Stage)turnBack.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void ActionCreateRoomButton(ActionEvent actionEvent) {
    }


    public void ActionSendButton(ActionEvent actionEvent) {
        //qui va implementato l'invio del messaggio.
    }
}
