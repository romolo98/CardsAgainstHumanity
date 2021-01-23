package logic;

import java.util.ArrayList;

public class Room {
    private static ArrayList<String> userList = new ArrayList<String>();
    private static String indirizzoIP;
    private static int deckList[];
    private static int porta;

    public static void createRoom(String name, String addrIP, String idMazzo) {
        userList.add(name);
        indirizzoIP = addrIP;
        deckList = CAHParser.parseDeck(idMazzo);
    }

    public static Boolean connectRoom(String name, String addrIP, int port) {
        if( indirizzoIP.equals(addrIP) ) {
            userList.add(name);
            porta = port;
        }

        return false;
    }

    public static void startGame() {

    }
}
