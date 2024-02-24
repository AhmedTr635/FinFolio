package com.example.finfolio.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {  private static  DataSource instance;
 /*   private   String  url ="jdbc:mysql://localhost:3306/finfolio";
    private  String  login ="root";
    private   String  pwd = "";*/

    private static String url = "jdbc:mysql://mysql-dbpidvtest123.alwaysdata.net/dbpidvtest123_java";
     private static String login = "347956";
     private static String pwd = "Finfolio1";
    private Connection cnx;

    private DataSource() {
        try {


            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("success");
        }
        catch (SQLException e) {
            System.out.println("echec");

        }
    }

    public static DataSource getInstance() {
        if (instance==null)
            instance= new DataSource();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

}
