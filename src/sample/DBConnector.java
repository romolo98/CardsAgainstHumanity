package sample;

import java.sql.*;

public class DBConnector {
    private static DBConnector instance;
    private String url;
    private String user;
    private String password;
    private Connection connection;
    private Statement statement;


    private DBConnector() {
        url = "jdbc:sqlserver://server-cah.database.windows.net;databaseName=CardsAgainstHumanity DB";
        user = "romolo";
        password = "vugc445_";
    }

    public static DBConnector getInstance() {
        if (instance == null)
            instance = new DBConnector();
        return instance;
    }

    public void connect () throws SQLException {
        connection = DriverManager.getConnection(url,user,password);
        System.out.println("connesso!");
    }

    public void addCarta (String contenuto,String tipologia,int ID_mazzo) throws SQLException {
        statement = connection.createStatement();
        String sql = "INSERT INTO Carta (contenuto,tipologia,ID_mazzo)" +
                    "VALUES ('"+contenuto+"','"+tipologia+"','"+ID_mazzo+"')";
        statement.executeUpdate(sql);
    }

    public int getNoMazzi() throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT COUNT(*) AS countRow "+
                "FROM Mazzo";
        ResultSet result = statement.executeQuery(sql);
        result.next();
        return result.getInt("countRow");
    }

    public String getNome(int index) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT Nome " +
                "FROM deckData " +
                "WHERE rowNumber = " + index;
        ResultSet result = statement.executeQuery(sql);
        result.next();
        System.out.println(result.getString("Nome"));
        return result.getString("Nome");
    }

    public boolean getGiocabile(int index) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT Giocabile " +
                "FROM deckData " +
                "WHERE rowNumber = " + index;
        ResultSet result = statement.executeQuery(sql);
        result.next();
        System.out.println(result.getBoolean("Giocabile"));
        return result.getBoolean("Giocabile");
    }


    public int getNoCarte(int index) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT NoCarte " +
                "FROM deckData " +
                "WHERE rowNumber = " + index;
        ResultSet result = statement.executeQuery(sql);
        result.next();
        System.out.println(result.getInt("NoCarte"));
        return result.getInt("NoCarte");
    }

    public int getID_Mazzo(int index) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT ID_Mazzo "+
                    "FROM ID_Mazzo_Row_number "+
                    "WHERE rowNumber = "+index;
        ResultSet result = statement.executeQuery(sql);
        result.next();
        System.out.println(result.getInt("ID_Mazzo"));
        return result.getInt("ID_Mazzo");
    }

    public int addMazzo(String nome) throws SQLException {
        statement = connection.createStatement();
        String sql = "INSERT INTO Mazzo (Nome) " +
                "VALUES ('"+nome+"')";
        statement.executeUpdate(sql);

        statement = connection.createStatement();
        String sql1 = "SELECT *"+
                "FROM Mazzo";
        ResultSet result = statement.executeQuery(sql1);
        int returnInt = 0;
        while(result.next()){
            returnInt = result.getInt("ID_Mazzo");
        }
        return returnInt;
    }

    public String getContenutoCarta (String ID_Carta) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT Contenuto " +
                     "FROM Carta"+
                     "WHERE ID_Carta = "+ID_Carta;
        ResultSet result = statement.executeQuery(sql);
        String returnString = new String();
        while(result.next()){
            returnString = result.getString("Contenuto");
        }
        return returnString;
    }

}
