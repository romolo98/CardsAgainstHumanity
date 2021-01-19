package sample;

import Server.CAHClient;
import Server.Messaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.sql.SQLException;


public class Controller {

    private FXMLLoader loader = new FXMLLoader();

    @FXML
    private Button playButton, createButton, optionsButton, turnBack, sendMessage;

    @FXML
    private TextField chatField;

    @FXML
    private TextArea chatWall;

    public void setChatWall(String messaggio){
        chatWall.appendText(messaggio);
        System.out.println("sono entrato");
    }

    public TextArea getChatWall () {
        return chatWall;
    }

    public void ActionCreatebutton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateRoomScreen.fxml"));
        createButton.getScene().setRoot(root);
    }

    public void ActionPlayButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PlayScreen.fxml"));
        playButton.getScene().setRoot(root);
    }

    public void ActionOptionsButton(ActionEvent actionEvent) throws IOException, SQLException {
        Parent root = loader.load(getClass().getResource("DeckManager.fxml").openStream());
        optionsButton.getScene().setRoot(root);
        ManagerController managerController = loader.getController();

        for (int i=1;i<=DBConnector.getInstance().getNoMazzi();i++) {
            managerController.getDatiMazzo().add(new Mazzo(DBConnector.getInstance().getID_Mazzo(i), DBConnector.getInstance().getNome(i), DBConnector.getInstance().getNoCarte(i), DBConnector.getInstance().getGiocabile(i)));
        }
        managerController.getColName().setCellValueFactory(new PropertyValueFactory<Mazzo,String>("nome"));
        managerController.getColTotal().setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("noCarte"));
        managerController.getColID().setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("ID_Mazzo"));
        managerController.getTable().setItems(managerController.getDatiMazzo());
    }

    public void ActionTurnBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        turnBack.getScene().setRoot(root);
    }

    public void ActionCreateRoomButton(ActionEvent actionEvent) {
    }

    public void ActionSendButton(ActionEvent actionEvent) {
        chatWall.appendText(chatField.getText()+'\n');
        Messaggio m = new Messaggio();
        m.testo = chatField.getText()+"\n";
        chatField.setText("");
        CAHClient.getClient().sendTCP(m);
    }

    public void isKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ESCAPE){
            Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        }
    }
}