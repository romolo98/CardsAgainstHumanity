package Server;

import Server.CAHNetwork;
import Server.Messaggio;
import Server.Mossa;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Controller;
import sample.DBConnector;


import static Server.CAHNetwork.porta;
import static Server.CAHNetwork.registraOggetti;

public class CAHClient extends Application {

    String nome =  "Roger";
    String host;
    static public Client client;
    private Controller controller;
    private FXMLLoader loader = new FXMLLoader();

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
                    controller.setChatWall(m.testo);
                    return;
                }
            }
        });
    }

    public static Connection getClient() {
        return client;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = loader.load(getClass().getResource("../sample/StartScreen.fxml").openStream());
        primaryStage.setTitle("Cards Against Humanity");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
        DBConnector.getInstance().connect();

        controller = loader.getController();

        //BISOGNA SETTARE IL CLIENT
        //client.connect(5000, "localhost", porta);

        primaryStage.setOnCloseRequest(event -> {
            client.stop();
        });
    }

    public static void main(String[] args) {
        launch(args);
        Log.set(Log.LEVEL_DEBUG);
    }

    public String getNome(){
        return nome;
    }

    public void setNomeUtente(String n){
        nome = n;
    }

    public String getHost(){
        return host;
    }

    public void setHost(String h){
        host = h;
    }
}
