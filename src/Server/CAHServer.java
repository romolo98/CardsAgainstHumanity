package Server;

import Server.CAHNetwork.AggiornaUtenti;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class CAHServer extends Application {

        Server server;

        public CAHServer () throws IOException {
                server = new Server(){
                        protected Connection newConnection () {
                                return new ChatConnection();
                        }
                };

                CAHNetwork.registraOggetti(server);

                server.addListener(new Listener() {
                        public void received(Connection c, Object o) {

                                ChatConnection connessioneChat = (ChatConnection) c;

                                if (o instanceof Mossa){
                                        //TO BE DECIDED
                                        return;
                                }

                                if (o instanceof CAHNetwork.RegistraUtente) {
                                        if (connessioneChat.nome != null)
                                                return;

                                String nome = ((CAHNetwork.RegistraUtente) o).nome;

                                if (nome == null || nome.length() == 0){
                                        return;
                                }

                                connessioneChat.nome = nome;


                                Messaggio mes = new Messaggio();
                                mes.testo = nome + " si è unito alla partita";
                                        System.out.println(mes.testo);
                                //Manda il messaggio a tutti, escludendo l'utente appena connesso.
                                server.sendToAllExceptTCP(connessioneChat.getID(), mes);

                                aggiornaUtenti();

                                return;
                                }

                                if (o instanceof Messaggio){
                                        System.out.println("HO RICEVUTO UN MESSAGGIO");
                                        if (connessioneChat.nome == null)
                                                return;
                                        Messaggio mes = (Messaggio)o;
                                        if (mes.testo == null || mes.testo.length() == 0)
                                                return;
                                        String messaggio = mes.testo;

                                        mes.testo = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + " " + connessioneChat.nome + ": " + messaggio;
                                        server.sendToAllTCP(mes);
                                        return;
                                }

                        }

                        public void disconnected (Connection c){
                                ChatConnection connection = (ChatConnection) c;

                                if (connection.nome != null) {
                                        Messaggio mes = new Messaggio();
                                        mes.testo = connection.nome + "ha lasciato la partita";
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
                        ChatConnection con = (ChatConnection) connessioni[i];
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
