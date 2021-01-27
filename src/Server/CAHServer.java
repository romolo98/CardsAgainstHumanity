package Server;

import Server.CAHNetwork.AggiornaUtenti;
import com.dosse.upnp.UPnP;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import controller.PlayScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Pair;
import logic.GraphicHandler;
import logic.Room;
import sample.Carta;
import sample.DBConnector;
import sample.Record;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class CAHServer extends Application {

        Server server;
        private ArrayList<Record> players_ID = new ArrayList<>();
        private static ArrayList<String> WhiteCardList = new ArrayList<String>();
        public static ArrayList<String> BlackCardList = new ArrayList<String>();
        private boolean gameOn = false;
        private static ArrayList<Integer> EveryKing = new ArrayList<>();
        public static int numberOfPlayers = 2;
        public static int winner;

        public CAHServer () throws IOException {
                server = new Server(){
                        protected Connection newConnection () {
                                return new CAHConnection();
                        }
                };
                CAHNetwork.registraOggetti(server);

                server.addListener(new Listener() {
                        public void received(Connection c, Object o) {

                                CAHConnection connessioneCAH = (CAHConnection) c;

                                if (o instanceof Mossa){
                                        System.out.println("Ho ricevuto una mossa");

                                        Mossa m = (Mossa) o;
                                        m.mossa = ((Mossa) o).mossa;
                                        server.sendToAllTCP(m);
                                }

                                if (o instanceof RoundEnd){
                                        System.out.println("Il round è finito");

                                        Random r = new Random();
                                        int casuale = r.nextInt(BlackCardList.size());
                                        BlackCard b = new BlackCard();
                                        b.cartaNera = BlackCardList.get(casuale);
                                        server.sendToAllTCP(b);

                                        for (int i = 0; i < players_ID.size(); i++){
                                                Collections.shuffle(WhiteCardList);
                                                WhiteCard wc = new WhiteCard();
                                                wc.cartaBianca = WhiteCardList.get(0);
                                                server.sendToTCP(players_ID.get(i).getUserID(), wc);
                                        }

                                }

                                if (o instanceof Punto){
                                        System.out.println("Qualcuno ha guadagnato un Punto");

                                        Punto playerPoint = (Punto) o;
                                        server.sendToTCP(playerPoint.ID, playerPoint);

                                        UpdateScore us = new UpdateScore();
                                        us.ranking = players_ID;
                                        server.sendToAllTCP(us);

                                        RoundEnd re = new RoundEnd();
                                        server.sendToAllTCP(re);

                                        if (EveryKing.size() == players_ID.size()){
                                                EveryKing.clear();
                                        }

                                        for (int i = 0; i < players_ID.size(); i++){
                                                if (!EveryKing.contains(players_ID.get(i))){
                                                        EveryKing.add(players_ID.get(i).getUserID());
                                                        break;
                                                }
                                        }
                                        Czar czar = new Czar();

                                        Random r = new Random();
                                        int casuale = r.nextInt(players_ID.size());

                                        czar.king = players_ID.get(casuale).getUserID();

                                        System.out.println(czar.king);
                                        server.sendToTCP(czar.king, czar);

                                }

                                if (o instanceof Match){
                                        System.out.println("Un nuovo match sta per iniziare!");
                                        gameOn = true;


                                        //Carico il mazzo
                                        try {
                                                loadDeck();
                                        } catch (SQLException throwables) {
                                                throwables.printStackTrace();
                                        }

                                        //Carica carta nera
                                        Random r = new Random();
                                        int casuale = r.nextInt(BlackCardList.size());
                                        BlackCard b = new BlackCard();
                                        b.cartaNera = BlackCardList.get(casuale);
                                        server.sendToAllTCP(b);

                                        //Mischiamo le carte

                                        //Distribuiamo le carte iniziali

                                        for (int p = 1; p <= 4; ++p){
                                                Collections.shuffle(WhiteCardList);
                                                for (int i = 0 ; i < 5; ++i){
                                                        WhiteCard w = new WhiteCard();
                                                        w.cartaBianca = WhiteCardList.get(i);
                                                        server.sendToTCP(p, w);
                                                }
                                        }

                                        Czar czar = new Czar();
                                        casuale = r.nextInt(players_ID.size());
                                        czar.king = players_ID.get(casuale).getUserID();

                                        server.sendToTCP(czar.king, czar);
                                        EveryKing.add(czar.king);

                                        UpdateScore currentPoints = new UpdateScore();
                                        currentPoints.ranking = players_ID;
                                        server.sendToAllTCP(currentPoints);

                                }

                                if (o instanceof MaxScore){
                                        MaxScore m = new MaxScore();
                                        m = (MaxScore) o;
                                        server.sendToAllTCP(m);

                                }

                                if (o instanceof GameWin){
                                        GameWin g = (GameWin) o;
                                        server.sendToAllTCP(g);
                                }

                                if (o instanceof CAHNetwork.RegistraUtente) {
                                        if (connessioneCAH.nome != null)
                                                return;

                                        String nome = ((CAHNetwork.RegistraUtente) o).nome;

                                        if (nome == null || nome.length() == 0){
                                                return;
                                        }
                                        connessioneCAH.nome = nome;

                                        if (players_ID.size() == numberOfPlayers){
                                                Alert alert = new Alert(Alert.AlertType.ERROR, "La stanza è piena, animale", ButtonType.OK);
                                                alert.showAndWait();
                                                return;
                                        }
                                        players_ID.add(new Record(connessioneCAH.getID(),connessioneCAH.nome,0));

                                        if (players_ID.size() == numberOfPlayers){
                                                PlayerIds playerIds = new PlayerIds();
                                                for (int i=0;i<players_ID.size();i++){
                                                        playerIds.Ids.add(players_ID.get(i).getUserID());
                                                }
                                                server.sendToAllTCP(playerIds);
                                        }

                                        Messaggio mes = new Messaggio();
                                        mes.testo = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + " " + nome + " si è unito alla partita\n";
                                        //Manda il messaggio a tutti, escludendo l'utente appena connesso.
                                        server.sendToAllExceptTCP(connessioneCAH.getID(), mes);

                                        Master master = new Master();

                                        server.sendToTCP(players_ID.get(0).getUserID(),master);

                                        aggiornaUtenti();

                                        return;
                                        }

                                if (o instanceof Messaggio){
                                        System.out.println("Messaggio ricevuto");
                                        System.out.println(connessioneCAH.getID());
                                        if (connessioneCAH.nome == null)
                                                return;

                                        Messaggio mes = (Messaggio)o;
                                        if (mes.testo == null || mes.testo.length() == 0)
                                                return;

                                        String messaggio = mes.testo;

                                        mes.testo = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + " " + connessioneCAH.nome + ": " + messaggio+ "\n";
                                        server.sendToAllExceptTCP(connessioneCAH.getID(), mes);
                                        return;
                                }

                        }

                        public void disconnected (Connection c){

                                CAHConnection connection = (CAHConnection) c;

                                if (connection.nome != null) {
                                        Messaggio mes = new Messaggio();
                                        mes.testo = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + " " + connection.nome + "ha lasciato la partita\n";
                                        server.sendToAllTCP(mes);
                                        //CODICE PULITO
                                        //////


                                        for (int i=0;i<players_ID.size();i++){
                                                System.out.println("Prima della Cancellazione");
                                                System.out.println(players_ID.get(i));
                                        }

                                        if (players_ID.contains((connection.getID()))){
                                                players_ID.remove(players_ID.indexOf(connection.getID()));
                                        }

                                        for (int i=0;i<players_ID.size();i++){
                                                System.out.println("Dopo la Cancellazione");
                                                System.out.println(players_ID.get(i));
                                        }

                                        if (gameOn){
                                                GameInterrupt g = new GameInterrupt();
                                                g.gameInterrupt = true;
                                                players_ID.clear();
                                                gameOn = false;
                                                server.sendToAllTCP(g);
                                        }

                                        aggiornaUtenti();
                                }
                        }

                });
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
                Parent root = FXMLLoader.load(getClass().getResource("ServerWindow.fxml"));
                primaryStage.setTitle("Server");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();

                UPnP.openPortTCP(CAHNetwork.porta);

                server.bind(CAHNetwork.porta);
                server.start();
                DBConnector.getInstance().connect();
                primaryStage.setOnCloseRequest(event -> {
                        server.stop();
                });
        }

        public void aggiornaUtenti(){
                //Prendo tutte le connessioni al server
                Connection[] connessioni = server.getConnections();
                ArrayList nomi = new ArrayList(connessioni.length);
                for (int i = 0; i < connessioni.length; i++){
                        CAHConnection con = (CAHConnection) connessioni[i];
                        nomi.add(con.nome);
                }

                CAHNetwork.AggiornaUtenti a = new AggiornaUtenti();
                a.nomiUtenti = (String[]) nomi.toArray(new String[nomi.size()]);
                server.sendToAllTCP(a);
        }

        public static void main(String[] args) throws IOException {
        new CAHServer();
        launch(args);
        }

        public void loadDeck() throws SQLException {
              for (int i = 1; i < DBConnector.getInstance().getNoCarteMazzo(2); i++) {
                        Carta c = new Carta(DBConnector.getInstance().getID_Carta(i,2),DBConnector.getInstance().getContenuto(i,2),DBConnector.getInstance().getTipologia(i,2),2);
                        if (c.getTipologia().equals("Bianca")) {
                                WhiteCardList.add(c.getContenuto());
                        }
                        else {
                                BlackCardList.add(c.getContenuto());
                        }
                }
        }

        public int getNoCarteNere(){
                return BlackCardList.size();
        }

        public String getContenutoCarta(int index){
                return BlackCardList.get(index);
        }

        public ArrayList<String> getWhiteCardList(){
                return WhiteCardList;
        }
}
