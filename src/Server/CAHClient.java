package Server;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import controller.PlayScreenController;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.GraphicHandler;
import sample.DBConnector;


import static Server.CAHNetwork.registraOggetti;

public class CAHClient extends Application {

    public static String nome;
    public static Boolean abilitato;
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
                    //SCRIVO IL CONTENUTO DELLA CARTA DI UN AVVERSARIO NELLO SPAZIO DEDICATO A LUI
                }

                if (oggetto instanceof Master){
                    System.out.println("Sei l'amministratore della partita");
                    abilitato = true;
                    ((PlayScreenController) GraphicHandler.getLoader().getController()).setHighscoreVisible(abilitato);
                    return;
                }

                if (oggetto instanceof Messaggio) {
                    if ( GraphicHandler.getLoader().getController() instanceof PlayScreenController ) {
                        Messaggio m = (Messaggio) oggetto;
                        ((PlayScreenController) GraphicHandler.getLoader().getController()).sendMessageToChatWall(m.testo);
                    }
                    return;
                }

                if (oggetto instanceof BlackCard){
                    if ( GraphicHandler.getLoader().getController() instanceof PlayScreenController ) {
                        String b = ((BlackCard) oggetto).cartaNera;
                        ((PlayScreenController) GraphicHandler.getLoader().getController()).setBlackCardSlot(b);
                    }
                return;
                }

                if (oggetto instanceof WhiteCard) {
                    if (GraphicHandler.getLoader().getController() instanceof PlayScreenController) {
                        String b = ((WhiteCard) oggetto).cartaBianca;
                        //QUI CI VA UNO SWITCH PER DECIDERE IN QUALE SLOT PIAZZARE LA CARTA RICEVUTA.
                        // QUESTA ANDRA' DOVE setText() E' "". PER ORA HO LASCIATO WhiteCard1.
                        ((PlayScreenController) GraphicHandler.getLoader().getController()).setWhiteOne(b);
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
