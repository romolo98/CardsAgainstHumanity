package logic;

public class CAHParser {

    public static int[] parseDeck(String idMazzo) {
        String token[] = idMazzo.split(",");

        int id[] = new int[token.length];
        for(int i = 0; i < token.length; i++)
            id[i] = Integer.parseInt(token[i]);

        return id;
    }
}
