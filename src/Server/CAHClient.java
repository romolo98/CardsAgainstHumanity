package Server;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller;

import static Server.CAHNetwork.registraOggetti;

public class CAHClient extends Application {

    String nome =  "Roger";
    String host;
    static public Client client;

    public CAHClient () {
        client = new Client();
        client.start();

        //registro il client
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
                    Messaggio m = (Messaggio) oggetto;
                    //COMPARE IL MESSAGGIO SULLA SCHERMATA DELLA CHAT
                    return;
                }
            }
        });
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/sample/StartScreen.fxml"));
        primaryStage.setTitle("Client1");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();

        //BISOGNA SETTARE IL CLIENT
        client.connect(5000, "localhost", 54321);

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

    static public Client getClient(){
        return client;
    }

}
