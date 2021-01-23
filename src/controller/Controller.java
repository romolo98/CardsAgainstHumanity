package controller;

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
import logic.GraphicHandler;
import sample.DBConnector;
import sample.Mazzo;

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
        GraphicHandler.displayScreen( GraphicHandler.CREATE_ROOM_SCREEN, GraphicHandler.NO_STREAM );
    }

    public void ActionPlayButton(ActionEvent actionEvent) throws IOException {
        GraphicHandler.displayScreen( GraphicHandler.PLAY_SCREEN, GraphicHandler.NO_STREAM );
    }

    public void ActionOptionsButton(ActionEvent actionEvent) throws IOException, SQLException {
        loader = GraphicHandler.displayScreen( GraphicHandler.DECK_LIST_SCREEN, GraphicHandler.OPEN_STREAM );

        ManagerController managerController = loader.getController();

        for (int i = 1; i<= DBConnector.getInstance().getNoMazzi(); i++) {
            managerController.getDatiMazzo().add(new Mazzo(DBConnector.getInstance().getID_Mazzo(i), DBConnector.getInstance().getNome(i), DBConnector.getInstance().getNoCarte(i), DBConnector.getInstance().getGiocabile(i), DBConnector.getInstance().getNoCarteNereMazzo(DBConnector.getInstance().getID_Mazzo(i)),DBConnector.getInstance().getNoCarteBiancheMazzo(DBConnector.getInstance().getID_Mazzo(i))));
        }
        managerController.getColName().setCellValueFactory(new PropertyValueFactory<Mazzo,String>("nome"));
        managerController.getColTotal().setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("noCarte"));
        managerController.getColID().setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("ID_Mazzo"));
        managerController.getColBlack().setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("black"));
        managerController.getColWhite().setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("white"));
        managerController.getColReady().setCellValueFactory(new PropertyValueFactory<Mazzo,Boolean>("giocabile"));
        managerController.getTable().setItems(managerController.getDatiMazzo());
    }

    public void ActionTurnBack(ActionEvent actionEvent) throws IOException {
        GraphicHandler.displayScreen( GraphicHandler.MAIN_SCREEN, GraphicHandler.NO_STREAM );
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
            GraphicHandler.displayScreen( GraphicHandler.MAIN_SCREEN, GraphicHandler.NO_STREAM );
        }
    }
}