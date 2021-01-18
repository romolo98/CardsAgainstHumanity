package sample;

import java.sql.*;

public class DBConnector {
    private String url;
    private String user;
    private String password;
    private Connection connection;
    private Statement statement;

    public DBConnector() {
        url = "jdbc:sqlserver://server-cah.database.windows.net:1433";
        user = "romolo";
        password = "vugc445_";
    }

    public void connect () throws SQLException {
        connection = DriverManager.getConnection(url,user,password);
        System.out.println("connesso!");
    }

    public void addCarta (String contenuto,String tipologia,int ID_mazzo) throws SQLException {
        statement = connection.createStatement();
        String sql = "INSERT INTO Carta (contenuto,tipologia,ID_mazzo)" +
                    "VALUES ("+contenuto+","+tipologia+""+ID_mazzo+")";
        statement.executeUpdate(sql);
    }

    public void addMazzo(String nome) throws SQLException {
        statement = connection.createStatement();
        String sql = "INSERT INTO Mazzo (Nome)" +
                "VALUES ("+nome+")";
        statement.executeUpdate(sql);
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
