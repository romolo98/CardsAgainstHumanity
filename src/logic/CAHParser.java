package logic;

public class CAHParser {

    public static final String IPV4_PATTERN = "(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}" +
            "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$";

    public static int[] parseDeck(String idMazzo) {
        String token[] = idMazzo.split(",");

        int id[] = new int[token.length];
        for(int i = 0; i < token.length; i++)
            id[i] = Integer.parseInt(token[i]);

        return id;
    }
}
