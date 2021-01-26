package Server;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import controller.Controller;
import controller.PlayScreenController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;
import logic.GraphicHandler;
import sample.DBConnector;


import java.util.ArrayList;

import static Server.CAHNetwork.registraOggetti;

public class CAHClient extends Application {

    public static String nome;
    public static Boolean abilitato;
    String host;
    static public Client client;
    private ArrayList<Pair<String, Integer>> utentiConnessi;


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
                    String whereToWrite = ((CAHConnection) connessione).nome;

                    for (int i = 0; i < utentiConnessi.size(); i++) {
                        if (whereToWrite.equals(utentiConnessi.get(i).getKey())) {
                            switch (i) {
                                case 1:
                                    ((PlayScreenController) GraphicHandler.getLoader().getController()).getPlayerSlot1().setText(((Mossa) oggetto).mossa);
                                    break;
                                case 2:
                                    ((PlayScreenController) GraphicHandler.getLoader().getController()).getPlayerSlot2().setText(((Mossa) oggetto).mossa);
                                    break;
                                case 3:
                                    ((PlayScreenController) GraphicHandler.getLoader().getController()).getPlayerSlot3().setText(((Mossa) oggetto).mossa);
                                    break;
                                case 4:
                                    ((PlayScreenController) GraphicHandler.getLoader().getController()).getPlayerSlot4().setText(((Mossa) oggetto).mossa);
                                    break;
                            }
                        }
                    }
                    return;
                }

                if (oggetto instanceof PlayerList){
                    utentiConnessi = ((PlayerList) oggetto).playerList;
                    return;
                }

                if (oggetto instanceof GameInterrupt){
                    //INTERROMPO LA PARTITA
                    //VOID RESET
                    GraphicHandler.displayScreen(GraphicHandler.MAIN_SCREEN, GraphicHandler.NO_STREAM).getController();

                    return;
                }

                if (oggetto instanceof Master){
                    System.out.println("Sei l'amministratore della partita");
                    abilitato = true;
                    //((PlayScreenController) GraphicHandler.getLoader().getController()).setHighscoreVisible(abilitato);
                    return;
                }

                if (oggetto instanceof BlackCard){
                    System.out.println("Mi è arrivata una carta nera");

                    if ( GraphicHandler.getLoader().getController() instanceof PlayScreenController ) {
                        System.out.println("ENTRO");
                        String b = ((BlackCard) oggetto).cartaNera;
                        ((PlayScreenController) GraphicHandler.getLoader().getController()).setBlackCardSlot(b);
                    }
                    return;
                }

                if (oggetto instanceof Messaggio) {
                    if ( GraphicHandler.getLoader().getController() instanceof PlayScreenController ) {
                        Messaggio m = (Messaggio) oggetto;
                        ((PlayScreenController) GraphicHandler.getLoader().getController()).sendMessageToChatWall(m.testo);
                    }
                    return;
                }


                if (oggetto instanceof WhiteCard) {
                    if (GraphicHandler.getLoader().getController() instanceof PlayScreenController) {
                        String b = ((WhiteCard) oggetto).cartaBianca;
                        ((PlayScreenController) GraphicHandler.getLoader().getController()).setPlayable(b);
                    }
                    return;
                }

                if (oggetto instanceof RoundEnd){
                    //LOGICA DI FINE ROUND
                }
            }
        });
    }

    public static Client getClient() {
        return client;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Cards Against Humanity");
        primaryStage.setScene( GraphicHandler.getScene() );
        primaryStage.setMaximized(false);
        primaryStage.show();
        DBConnector.getInstance().connect();

        //BISOGNA SETTARE IL CLIENT


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
