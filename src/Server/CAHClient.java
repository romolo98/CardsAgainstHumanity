package Server;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import controller.Controller;
import controller.PlayScreenController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    public static ArrayList<Integer> id_connessioni = new ArrayList<>();
    public static boolean Czar;
    public static int pointsToVictory;
    public static int myPoints = 0;

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
                    int id = ((Mossa) oggetto).ID_player;
                for (int i = 0; i < id_connessioni.size(); i++) {
                    if (id_connessioni.get(i) == id && i == 0)
                        ((PlayScreenController) GraphicHandler.getLoader().getController()).getPlayerSlot1().setText(((Mossa) oggetto).mossa);
                    else if (id_connessioni.get(i) == id && i == 1)
                        ((PlayScreenController) GraphicHandler.getLoader().getController()).getPlayerSlot2().setText(((Mossa) oggetto).mossa);
                    else if (id_connessioni.get(i) == id && i == 2)
                        ((PlayScreenController) GraphicHandler.getLoader().getController()).getPlayerSlot3().setText(((Mossa) oggetto).mossa);
                    else if (id_connessioni.get(i) == id && i == 3)
                        ((PlayScreenController) GraphicHandler.getLoader().getController()).getPlayerSlot4().setText(((Mossa) oggetto).mossa);
                    }
                    return;
                }

                if (oggetto instanceof GameInterrupt){
                    GraphicHandler.displayScreen(GraphicHandler.MAIN_SCREEN, GraphicHandler.NO_STREAM).getController();
                    return;
                }

                if (oggetto instanceof Czar){
                    Czar = true;
                    /*Alert alert = new Alert(Alert.AlertType.INFORMATION, "SEI LO CZAAAAR", ButtonType.OK);
                    alert.showAndWait();
                    */PlayScreenController psc = GraphicHandler.getLoader().getController();
                    for (int i = 0; i < id_connessioni.size(); i++){
                        if (id_connessioni.get(i) == client.getID()){
                            if (i == 0)
                                psc.getPlayerSlot1().setDisable(true);
                            else if (i == 1)
                                psc.getPlayerSlot2().setDisable(true);
                            else if (i == 2)
                                psc.getPlayerSlot3().setDisable(true);
                            else if (i == 3)
                                psc.getPlayerSlot4().setDisable(true);
                        }
                    }
                }

                if (oggetto instanceof PlayerIds){
                    id_connessioni = ((PlayerIds) oggetto).Ids;
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

                if (oggetto instanceof MaxScore){
                    MaxScore m = (MaxScore) oggetto;
                    pointsToVictory = m.punteggioVittoria;

                }

                if (oggetto instanceof GameWin){
                    GameWin gw = (GameWin) oggetto;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (gw.winner == client.getID()) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Complimenti, sei la persona più cattiva in questa partita", ButtonType.OK);
                                alert.showAndWait();

                            }
                            else {
                                Alert alert2 = new Alert(Alert.AlertType.ERROR, "Non sei riuscito nemmeno a far ridere con le parole brutte, fai schifo", ButtonType.OK);
                                alert2.showAndWait();
                            }
                        }
                        });
                    GameInterrupt gi = new GameInterrupt();
                    client.sendTCP(gi);
                    return;
                }

                if (oggetto instanceof Punto){
                    myPoints++;

                    if (myPoints == pointsToVictory) {
                    GameWin w = new GameWin();
                    w.winner = CAHClient.getClient().getID();
                    CAHClient.getClient().sendTCP(w);
                    }
                }

                if (oggetto instanceof UpdateScore){
                    UpdateScore current = (UpdateScore) oggetto;
                    PlayScreenController psc = GraphicHandler.getLoader().getController();
                    ArrayList<String> players = new ArrayList<>();
                    current.sortHighscore();
                    for (int i=0;i<current.ranking.size();i++){
                        players.add(current.ranking.get(i).getScore() + " " + current.ranking.get(i).getName());
                    }
                    psc.updatePlayersList(players);
                }

                if (oggetto instanceof RoundEnd){
                    Czar = false;
                    PlayScreenController psc = GraphicHandler.getLoader().getController();
                    psc.getConfirmCard().setDisable(false);
                    for (int i = 0; i < id_connessioni.size(); i++) {
                        if (id_connessioni.get(i) == client.getID()) {
                            if (i == 0)
                                psc.getPlayerSlot1().setDisable(false);
                            else if (i == 1)
                                psc.getPlayerSlot2().setDisable(false);
                            else if (i == 2)
                                psc.getPlayerSlot3().setDisable(false);
                            else if (i == 3)
                                psc.getPlayerSlot4().setDisable(false);
                        }
                    }
                    psc.getPlayerSlot1().setText("");
                    psc.getPlayerSlot2().setText("");
                    psc.getPlayerSlot3().setText("");
                    psc.getPlayerSlot4().setText("");

                    RoundEnd re = new RoundEnd();
                    CAHClient.getClient().sendTCP(re);
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
