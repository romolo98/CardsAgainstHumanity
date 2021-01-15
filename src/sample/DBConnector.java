package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private String url;
    private String user;
    private String password;
    private Connection connection;

    public DBConnector() {
        url = "jdbc:sqlserver://server-cah.database.windows.net:1433";
        user = "romolo";
        password = "vugc445_";
    }

    public void connect () throws SQLException {
        connection = DriverManager.getConnection(url,user,password);
        System.out.println("connesso!");
    }

}
