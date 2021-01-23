package logic;

public class Room {
    private static String nome;
    private static String indirizzoIP;
    private static int deckList[];


    public static void createRoom(String name, String addrIP, String idMazzo) {
        nome = name;
        indirizzoIP = addrIP;
        deckList = CAHParser.parseDeck(idMazzo);
    }
}
