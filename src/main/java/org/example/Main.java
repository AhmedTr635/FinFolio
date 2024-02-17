package org.example;

import entite.User;
import service.UserService;
import util.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException {

        User u1= new User(1,"Trabelsi","Ahmed","trabelsi.ahmed@esprit.tn",53604045,"hmed","Ben Arous",2,2.5f,"admin");

        Connection cnx =DataSource.getInstance().getCnx();
        UserService us =new UserService();
        //us.add(u1);
        //us.update(u1,u2);
//us.delete(u1);
        us.readAll().forEach(e-> System.out.println(e));
    }
}