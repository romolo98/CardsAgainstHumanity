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
import javafx.stage.Stage;
import logic.Room;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CAHServer extends Application {

        Server server;

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
                                        if (connessioneCAH.nome != null)
                                                return;

                                        Mossa m = (Mossa) o;
                                        m.mossa = ((Mossa) o).mossa;
                                        server.sendToAllExceptTCP(connessioneCAH.getID(), m);
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

                                        //Carica carta nera
                                        Random r = new Random();
                                        int casuale = r.nextInt(Room.getNoCarteNere());
                                        BlackCard b = new BlackCard();
                                        b.cartaNera = Room.getContenutoCarta(casuale);
                                        server.sendToAllTCP(b);

                                        //Carica carte bianche
                                        ArrayList<String> carteMazzo = Room.getWhiteCards();

                                        //Contiamo i player, ipoteticamente 4

                                        int players_Number = 4;

                                        //Mischiamo le carte
                                        Collections.shuffle(carteMazzo);

                                        //Distribuiamo le carte

                                        int maxCarte = carteMazzo.size()/players_Number;

                                        for (int p = 1; p <= 4; ++p){
                                                for (int i =0 ; i < maxCarte; ++i){
                                                        WhiteCard w = new WhiteCard();
                                                        w.cartaBianca = carteMazzo.get(i);
                                                        server.sendToTCP(p, w);
                                                }
                                        }

                                        //LA PARTITA E' TECNICAMENTE PRONTA PER INIZIARE
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

                                        Messaggio mes = new Messaggio();
                                        mes.testo = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + " " + nome + " si è unito alla partita\n";
                                        //Manda il messaggio a tutti, escludendo l'utente appena connesso.
                                        server.sendToAllExceptTCP(connessioneCAH.getID(), mes);

                                        if (connessioneCAH.getID() == 1) {
                                                Master master = new Master();
                                                server.sendToTCP(1,master);
                                        }
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

}
