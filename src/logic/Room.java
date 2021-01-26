package logic;

import sample.Carta;
import sample.DBConnector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Room {
    private static ArrayList<String> userList = new ArrayList<String>();
    private static ArrayList<String> WhiteCardList = new ArrayList<String>();
    public static ArrayList<String> BlackCardList = new ArrayList<String>();
    private static int porta;
    private static int highscore = 0;

    public static void setHighscore(int score) {highscore = score;}

    public static void createRoom(String name, int port, int arrayID[]) throws SQLException {
        userList.add(name);
        porta = port;

        for(int j = 0; j < arrayID.length; j++) {
            System.out.println(arrayID[j]);
            for (int i = 1; i < DBConnector.getInstance().getNoCarteMazzo(arrayID[j]); i++) {
                Carta c = new Carta(DBConnector.getInstance().getID_Carta(i,arrayID[j]),DBConnector.getInstance().getContenuto(i,arrayID[j]),DBConnector.getInstance().getTipologia(i,arrayID[j]),arrayID[j]);
                if (c.getTipologia().equals("Bianca")) {
                    WhiteCardList.add(c.getContenuto());
                }
                else {
                    BlackCardList.add(c.getContenuto());
                }
            }
        }
    }

    public static void checkUtente(String name) {
        int contPrensenti = Collections.frequency(userList, name); // Contatore di utenti già presenti con un determinato nickname.
        if(contPrensenti == 0)
            userList.add(name);
        else
            userList.add(name + "_" + contPrensenti);
    }

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

    public static int getNoCarteNere() {
        return BlackCardList.size();
    }

    public static String getContenutoCarta (int index){
        System.out.println(BlackCardList.get(index));
        return BlackCardList.get(index);
    }

    public static ArrayList<String> getWhiteCards(){
        return WhiteCardList;
    }
}
