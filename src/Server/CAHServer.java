package Server;

import Server.CAHNetwork.AggiornaUtenti;
import com.dosse.upnp.UPnP;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Pair;
import logic.Room;
import sample.Carta;
import sample.DBConnector;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CAHServer extends Application {

        Server server;
        private ArrayList<Integer> players_ID = new ArrayList<Integer>();
        private static ArrayList<String> WhiteCardList = new ArrayList<String>();
        public static ArrayList<String> BlackCardList = new ArrayList<String>();
        private boolean gameOn = false;

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
                                        //SEGNALA LA FINE DEL ROUND.
                                        //SBLOCCA IL TASTO GIOCA NUOVA CARTA AD OGNI GIOCATORE
                                        //SE IL PUNTEGGIO MAX E' STATO RAGGIUNTO, FINE PARTITA.
                                        //SE IL PUNTEGGIO NON E' STATO RAGGIUNTO, DA' UNA NUOVA CARTA AI GIOCATORI

                                }

                                if (o instanceof Punto){
                                        System.out.println("Un giocatore ha guadagnato un punto");
                                        //INCREMENTA IL PUNTEGGIO DI UN GIOCATORE, IN BASE AL SUO CONNECTIONID.
                                        //DOBBIAMO TROVARE IL MODO DI RICONDURRE LA CARTA AL GIOCATORE CHE L'HA GIOCATA.
                                        //SE FISSIAMO LO SLOT IN CUI OGNI GIOCATORE "POSA" LA SUA CARTA, PROBLEMA RISOLTO
                                        //ES: GIOCATORE 1 -> TEXTAREA1 | GIOCATORE 2 -> TEXTAREA2, ETC.
                                        //DOPO CHE IL PUNTO E' STATO ASSEGNATO, IL SERVER SI MANDA UN PACCHETTO ROUNDEND.
                                        //POSSIAMO ANCHE EVITARE MA RENDE IL CODICE UN PO' PIU' PULITO.
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
                                }

                                if (o instanceof MaxScore){
                                        //SETTIAMO IL PUNTEGGIO ALLO SCORE
                                        //IL CLIENT NOTIFICA LA SUA VITTORIA AL SERVER
                                }

                                if (o instanceof CAHNetwork.RegistraUtente) {
                                        if (connessioneCAH.nome != null)
                                                return;

                                        String nome = ((CAHNetwork.RegistraUtente) o).nome;

                                        if (nome == null || nome.length() == 0){
                                                return;
                                        }
                                        connessioneCAH.nome = nome;

                                        if (players_ID.size() == 4){
                                                Alert alert = new Alert(Alert.AlertType.ERROR, "La stanza è piena, stronzo", ButtonType.OK);
                                                alert.showAndWait();
                                                return;
                                        }
                                        players_ID.add(connessioneCAH.getID());

                                        if (players_ID.size() == 2){
                                                PlayerIds playerIds = new PlayerIds();
                                                playerIds.Ids = players_ID;
                                                server.sendToAllTCP(playerIds);
                                        }

                                        Messaggio mes = new Messaggio();
                                        mes.testo = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + " " + nome + " si è unito alla partita\n";
                                        //Manda il messaggio a tutti, escludendo l'utente appena connesso.
                                        server.sendToAllExceptTCP(connessioneCAH.getID(), mes);

                                        Master master = new Master();

                                        server.sendToTCP(players_ID.get(0),master);

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
                                        System.out.println(connection.getID());
                                        System.out.println(players_ID.size());
                                        players_ID.remove(players_ID.indexOf(connection.getID()));
                                        System.out.println(players_ID.size());
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
