package Server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class CAHNetwork {

    static public final int porta = 54555;

    //Registro gli oggetti che dovranno essere inviati tramite la rete
    static public void registraOggetti(EndPoint endPoint){
        Kryo kryo = endPoint.getKryo();
        kryo.register(Mossa.class);
        kryo.register(Messaggio.class);
        kryo.register(String.class);
        kryo.register(RegistraUtente.class);
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

    //Messaggio e Mossa potrebbe andare tranquillamente qui. Vedrò dopo come implementarlo.

}
