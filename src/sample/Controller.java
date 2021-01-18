package sample;

import Server.CAHNetwork;
import Server.Messaggio;
import Server.Mossa;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;

import static Server.CAHNetwork.registraOggetti;

public class Controller extends Application{

    private String nome;
    private String host;
    private Client client;
    private FXMLLoader loader = new FXMLLoader();

    @FXML
    private Button playButton, createButton, optionsButton, turnBack, sendMessage;

    @FXML
    private TextField chatField;

    @FXML
    private TextArea chatWall;

    @FXML
    public void initialize() throws IOException {
        client = new Client();
        client.start();
        this.client.connect(5000, "localhost", 54321);
        nome = "Matteo";
        registraOggetti(client);

        client.addListener(new Listener() {
            public void connected(Connection connessione){
                //Creo un oggetto RegistraUtente(Stringa) per inviare al server il nome del nuovo utente connesso
                CAHNetwork.RegistraUtente utente = new CAHNetwork.RegistraUtente();
                utente.nome = nome;
                System.out.println(utente.nome);
                client.sendTCP(utente);
            }

            public void received(Connection connessione, Object oggetto){
                if (oggetto instanceof Mossa){
                    //TO BE DECIDED
                }

                if (oggetto instanceof Messaggio) {
                    System.out.println("Messaggio ricevuto");
                    Messaggio m = (Messaggio) oggetto;
                    chatWall.appendText(m.testo+"\n");
                    return;
                }
            }
        });

    }

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
        Messaggio m = new Messaggio();
        m.testo = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + " " + ": " + chatField.getText()+"\n";
        chatField.setText("");
        client.sendTCP(m);
    }

    public void isKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ESCAPE){
            Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/PlayScreen.fxml"));
        primaryStage.setTitle("Client1");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();

        //IL CLIENT ANDREBBE FERMATO QUI MA NON E' PIU' POSSIBILE
    }

    public static void main(String[] args) {
        launch(args);
        Log.set(Log.LEVEL_DEBUG);
    }
}