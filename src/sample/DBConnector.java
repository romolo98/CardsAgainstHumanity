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
        url = "jdbc:sqlserver://unicah.database.windows.net:1433;database=CardsAgainstHumanity";
        user = "Matteo";
        password = "Darkn3ssinside";
    }

    public static DBConnector getInstance() {
        if (instance == null)
            instance = new DBConnector();
        return instance;
    }

    public void connect () throws SQLException {
        connection = DriverManager.getConnection(url,user,password);
        //System.out.println("connesso!");
    }

    ///////////////////////////////////////////////////////////////////777
    //DB CARTA

    public void addCarta (String contenuto,String tipologia,int ID_mazzo) throws SQLException {
        statement = connection.createStatement();
        String sql = "INSERT INTO Carta (contenuto,tipologia,ID_mazzo)" +
                "VALUES ('"+contenuto+"','"+tipologia+"','"+ID_mazzo+"')";
        statement.executeUpdate(sql);
    }

    public int getNoCarteMazzo(int ID_Mazzo) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT COUNT(*) AS countCards "+
                "FROM Carta " +
                "WHERE ID_Mazzo = "+ID_Mazzo;
        ResultSet result = statement.executeQuery(sql);
        result.next();
        System.out.println(result.getInt("countCards"));
        return result.getInt("countCards");
    }

    public String getContenuto(int index,int ID_Mazzo) throws SQLException {
        statement = connection.createStatement();
        String sql1 = "ALTER VIEW cardData AS " +
                "SELECT Contenuto, ROW_NUMBER() OVER(ORDER BY (ID_Carta)) AS rowNumber " +
                "FROM Carta " +
                "WHERE ID_Mazzo ="+ID_Mazzo;
        statement.executeUpdate(sql1);
        String sql = "SELECT Contenuto " +
                "FROM cardData " +
                "WHERE rowNumber = " + index;
        ResultSet result = statement.executeQuery(sql);
        result.next();
        //System.out.println(result.getString("Contenuto"));
        return result.getString("Contenuto");
    }

    public String getTipologia(int index,int ID_Mazzo) throws SQLException {
        statement = connection.createStatement();
        String sql1 = "ALTER VIEW cardData AS " +
                "SELECT Tipologia, ROW_NUMBER() OVER(ORDER BY (ID_Carta)) AS rowNumber " +
                "FROM Carta " +
                "WHERE ID_Mazzo ="+ID_Mazzo;
        statement.executeUpdate(sql1);

        String sql = "SELECT Tipologia " +
                "FROM cardData " +
                "WHERE rowNumber = " + index;
        ResultSet result = statement.executeQuery(sql);
        result.next();
        return result.getString("Tipologia");
    }

    public int getID_Mazzo_Carta(int index) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT ID_Mazzo "+
                "FROM cardData "+
                "WHERE rowNumber = "+index;
        ResultSet result = statement.executeQuery(sql);
        result.next();
        return result.getInt("ID_Mazzo");
    }

    public int getID_Carta(int index,int ID_Mazzo) throws SQLException {
        statement = connection.createStatement();
        String sql1 = "ALTER VIEW cardData AS " +
                "SELECT ID_Carta, ROW_NUMBER() OVER(ORDER BY (ID_Carta)) AS rowNumber " +
                "FROM Carta " +
                "WHERE ID_Mazzo ="+ID_Mazzo;
        statement.executeUpdate(sql1);

        String sql = "SELECT ID_Carta "+
                "FROM cardData "+
                "WHERE rowNumber = "+index;
        ResultSet result = statement.executeQuery(sql);
        result.next();
        return result.getInt("ID_Carta");
    }

    /////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////
    //DB MAZZO

    public String getNome(int index) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT Nome " +
                "FROM deckData " +
                "WHERE rowNumber = " + index;
        ResultSet result = statement.executeQuery(sql);
        result.next();
        return result.getString("Nome");
    }

    public boolean getGiocabile(int index) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT Giocabile " +
                "FROM deckData " +
                "WHERE rowNumber = " + index;
        ResultSet result = statement.executeQuery(sql);
        result.next();
        return result.getBoolean("Giocabile");
    }

    public int getNoCarte(int index) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT NoCarte " +
                "FROM deckData " +
                "WHERE rowNumber = " + index;
        ResultSet result = statement.executeQuery(sql);
        result.next();
        return result.getInt("NoCarte");
    }

    public int getID_Mazzo(int index) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT ID_Mazzo "+
                "FROM deckData "+
                "WHERE rowNumber = "+index;
        ResultSet result = statement.executeQuery(sql);
        result.next();
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

    public int getNoMazzi() throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT COUNT(*) AS countRow "+
                "FROM Mazzo";
        ResultSet result = statement.executeQuery(sql);
        result.next();
        return result.getInt("countRow");
    }

    ///////////////////////////////////////////////////////////////////////

}
