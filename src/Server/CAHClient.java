package Server;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.Controller;
;

import java.awt.*;

import static Server.CAHNetwork.porta;
import static Server.CAHNetwork.registraOggetti;

public class CAHClient extends Application {

    @FXML
    Label connectedusers;

    @FXML
    TextArea chatScreen;


    String nome;
    String host;
    Client client;

    public CAHClient () {
        client = new Client();
        client.start();

        //registro il client
        registraOggetti(client);

        client.addListener(new Listener() {
            public void connesso(Connection connessione){
                //Creo un oggetto RegistraUtente(Stringa) per inviare al server il nome del nuovo utente connesso
                CAHNetwork.RegistraUtente utente = new CAHNetwork.RegistraUtente();
                utente.nome = nome;
                client.sendTCP(utente);
            }

            public void ricevuto(Connection connessione, Object oggetto){
                if (oggetto instanceof Mossa){
                    //TO BE DECIDED
                }

                if (oggetto instanceof CAHNetwork.AggiornaUtenti){
                    CAHNetwork.AggiornaUtenti aggiornaUtenti = (CAHNetwork.AggiornaUtenti) oggetto;

                    for(String s : aggiornaUtenti.nomiUtenti){
                        connectedusers.setText(s+"\n");
                    }
                    return;
                }

                if (oggetto instanceof Messaggio) {
                    Messaggio m = (Messaggio) oggetto;
                    chatScreen.appendText(m.testo);
                    return;
                }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../sample/UserSet.fxml"));
        primaryStage.setTitle("User");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        //BISOGNA SETTARE IL CLIENT

        client.connect(5000, "localhost", porta);

        primaryStage.setOnCloseRequest(event -> {
            client.stop();
        });

    }

    public void sendMessage(String m){
        Messaggio mes = new Messaggio();
        mes.testo = m;
        client.sendTCP(mes);
    }


    public static void main(String[] args) {
        new CAHClient();
        launch(args);
        Log.set(Log.LEVEL_DEBUG);
    }

    public String getHost(){
        return host;
    }

    public void setNomeUtente(String n){
        nome = n;
    }

    public void setHost(String h){
        host = h;
    }

    public void sendTCPMessage(Object o){
        Messaggio mes = (Messaggio) o;
        client.sendTCP(mes);
    }

    public void impostaNomi(final String[] nomi){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                for(String n : nomi){

                }
            }
        });

    }

}