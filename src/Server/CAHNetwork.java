package Server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import sample.Record;

import java.util.ArrayList;

public class CAHNetwork {

    static public final int porta = 56555;

    //Registro gli oggetti che dovranno essere inviati tramite la rete
    static public void registraOggetti(EndPoint endPoint){
        Kryo kryo = endPoint.getKryo();
        kryo.register(Mossa.class);
        kryo.register(Messaggio.class);
        kryo.register(String.class);
        kryo.register(RegistraUtente.class);
        kryo.register(Server.CAHNetwork.AggiornaUtenti.class);
        kryo.register(String[].class);
        kryo.register(CAHNetwork.RegistraUtente.class);
        kryo.register(Master.class);
        kryo.register(BlackCard.class);
        kryo.register(Match.class);
        kryo.register(Punto.class);
        kryo.register(RoundEnd.class);
        kryo.register(WhiteCard.class);
        kryo.register(MaxScore.class);
        kryo.register(GameInterrupt.class);
        kryo.register(PlayerIds.class);
        kryo.register(ArrayList.class);
        kryo.register(Czar.class);
        kryo.register(UpdateScore.class);
        kryo.register(Record.class);
        kryo.register(GameWin.class);
    }


    //ALCUNI OGGETTI CHE POSSONO ESSERE MANDATI NELLA RETE
    //Viene usato al login per registrare l'utente
    static public class RegistraUtente {
        public String nome;
    }

    //Viene usato per aggiornare la lista degli utenti connessi
    static public class AggiornaUtenti {
        public String[] nomiUtenti;
    }
}
