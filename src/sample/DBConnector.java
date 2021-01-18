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
        url = "jdbc:sqlserver://server-cah.database.windows.net:1433,databaseName=CardsAgainstHumanity_DB";
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

    public void prova () throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT sp.name\n" +
                "    , sp.default_database_name\n" +
                "FROM sys.server_principals sp\n" +
                "WHERE sp.name = SUSER_SNAME()";
        ResultSet result = statement.executeQuery(sql);
        System.out.println(result);

    }

    public void addCarta (String contenuto,String tipologia,int ID_mazzo) throws SQLException {
        statement = connection.createStatement();
        String sql = "INSERT INTO Carta (contenuto,tipologia,ID_mazzo)" +
                    "VALUES ("+contenuto+","+tipologia+""+ID_mazzo+")";
        statement.executeUpdate(sql);
    }

    public int addMazzo(String nome) throws SQLException {
        statement = connection.createStatement();
        String sql = "INSERT INTO Mazzo (Nome) " +
                "VALUES ('"+nome+"')";
        statement.executeUpdate(sql);

        statement = connection.createStatement();
        String sql1 = "SELECT MAX(ID_Mazzo)"+
                "FROM Mazzo";
        int result = statement.executeUpdate(sql1);
        return result;
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
