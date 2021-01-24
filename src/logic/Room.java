package logic;

import java.util.ArrayList;

public class Room {
    private static ArrayList<String> userList = new ArrayList<String>();
    private static int deckList[];
    private static int porta;
    private static int highscore = 0;

    public static void setHighscore(int score) {highscore = score;}

    public static void createRoom(String name, int port, String idMazzo) {
        userList.add(name);
        porta = port;
        deckList = CAHParser.parseDeck(idMazzo);
    }

    /*public static Boolean connectRoom(String name, String addrIP, int port) {
        if( indirizzoIP.equals(addrIP) ) {
            userList.add(name);
            porta = port;
        }

        return false;
    }*/

    public static Boolean playerNumber() {
        if(userList.size() >= 4)
            return true;
        return false;
    }

    public static Boolean isScoreSet() {
        if(highscore > 0)
            return true;
        return false;
    }


}
