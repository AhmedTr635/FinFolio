package com.example.finfolio.Service;

import com.example.finfolio.Entite.User;
import com.example.finfolio.util.DataSource;
import com.example.finfolio.Entite.Error;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ErrorService {

    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;

    private static ErrorService instance;

    public ErrorService() {connexion= DataSource.getInstance().getCnx();}







    public void add(Error er) {
        String request="insert into Problem (user_id,user_nom,error,timestamp) values(?,?,?,?)";

        try {
            pst=connexion.prepareStatement(request);
            pst.setInt(1,er.getUser().getId());
            pst.setString(2,er.getUser().getNom());
            pst.setString(3,er.getError());
            pst.setString(4,er.getTimestamp());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    public List<Error> readAll() throws SQLException {
        String request="select * from problem";
        UserService us=new UserService();
        List<User>users=us.readAll();
        User user=new User();
        List<Error> list=new ArrayList<>();
        try {
            ste= connexion.createStatement();
            ResultSet rs = ste.executeQuery(request);
            while(rs.next()){
                System.out.println("err");
                        int id=rs.getInt(1);
                        int user_id= rs.getInt(2);
                        String name=rs.getString(3);
                        String error=rs.getString(4);
                        String timestamp=rs.getString(5);
                System.out.println(user_id+" "+error);
                        user=users.stream().filter(u->u.getId()==user_id).findFirst().get();
                System.out.println("izibi");
                        list.add(new Error(id,user, error));


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            System.out.println(users);
        }
        return list;
    }


//    public  User fetchUserByUserId(int userId) {
//
//            String sql = "SELECT user_id, username, email FROM user_table WHERE user_id = ?";
//
//            try (PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {
//                preparedStatement.setInt(1, userId);
//
//                try (ResultSet ps = preparedStatement.executeQuery()) {
//                    if (resultSet.next()) {
//                        User user = new User();
//                        user.setUserId(resultSet.getInt("user_id"));
//                        user.setUsername(resultSet.getString("username"));
//                        user.setEmail(resultSet.getString("email"));
//                        user.setId(ps.getInt("user_id"));
//                        user.setNom(ps.getString("nom"));
//                        user.setPrenom(ps.getString("prenom"));
//                        user.setEmail(ps.getString("email"));
//                        user.setNumtel(ps.getString("numtel"));
//                        user.setPassword(ps.getString("password"));
//                        user.setAdresse(ps.getString("adresse"));
//                        user.setNbcredit(Integer.parseInt(ps.getString("nbcredit")));
//                        user.setRate(Float.parseFloat(ps.getString("rate")));
//                        user.setRole(ps.getString("nom"));
//                        user.set(ps.getString("nom");
//
//
//                        return user;
//                    }
//                }
//            }
//         catch (SQLException e) {
//            e.printStackTrace(); // Handle the exception according to your application's needs
//        }
//
//        return null;
//    }
//}

    public Error fetchErrorByUserId(int userId) {


        Error error = null;

        try  {
            String query = "SELECT * FROM problem WHERE user_id = ?";
            pst = connexion.prepareStatement(query);
            pst.setInt(1, userId);
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                String problem = resultSet.getString("error");
                String date=resultSet.getString("timestamp");
                // Add more attributes if needed
                UserService userService =new UserService();
                User user = userService.readAll().stream().filter(u->u.getId()==userId).findFirst().get();
                error = new Error(user, problem);
                ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return error;
    }



    public static ErrorService getInstance() {
        if (instance == null) {
            instance = new ErrorService();
        }
        return instance;
    }



}
