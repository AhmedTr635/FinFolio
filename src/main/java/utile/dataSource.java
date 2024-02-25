package utile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dataSource {

    private static String url = "jdbc:mysql://mysql-dbpidvtest123.alwaysdata.net/dbpidvtest123_java";
    private static String login = "347956";
    private static String pwd = "Finfolio1"; // Replace ********** with the actual password

    private static Connection cnx;
    private static dataSource instance;

    private dataSource() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database: " + e.getMessage());
        }
    }

    public static dataSource getInstance() {
        if (instance == null) {
            instance = new dataSource();
        }
        return instance;
    }

    public static Connection getCnx() {
        return cnx;
    }
}
