package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String bd = "sdlab09chaisa";
    private static final String login = "root"; 
    private static final String password = "root"; 
    private static final String url = "jdbc:mysql://localhost:3306/" + bd + "?useSSL=false&serverTimezone=UTC";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, login, password);
            System.out.println("Conectado a la base de datos [" + bd + "]");
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
}
