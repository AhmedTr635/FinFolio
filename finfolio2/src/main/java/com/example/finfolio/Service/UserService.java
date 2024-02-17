package com.example.finfolio.Service;

import Views.AlerteFinFolio;
import com.example.finfolio.util.DataSource;
import com.example.finfolio.Entite.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService   {
    private Statement ste;

    private PreparedStatement ps;
    private Connection cnx;

    public UserService() {

        cnx= DataSource.getInstance().getCnx();
    }

    public void add(User u)throws SQLException {
        if(readAll().stream().anyMatch(us->us.getEmail().equals(u.getEmail())))
            AlerteFinFolio.alerte("exist");
            else {
            String req = "INSERT INTO user (nom,prenom,email,numtel,password,adresse,nbcredit,rate,role) values (?,?,?,?,?,?,?,?,?)";
            ps = cnx.prepareStatement(req);
            try {


                ps.setString(1, u.getNom());
                ps.setString(2, u.getPrenom());
                ps.setString(3, u.getEmail());
                ps.setString(4, u.getNumTel());
                ps.setString(5, u.getPassword());
                ps.setString(6, u.getAdresse());
                ps.setInt(7, u.getNbrCredit());
                ps.setFloat(8, u.getRate());
                ps.setString(9, u.getRole());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
    public List<User> readAll()throws SQLException
    {
        String req="Select * from user ";
        List<User>list=new ArrayList<>();
        ste= cnx.createStatement();
        try {


            ResultSet resultSet= ste.executeQuery(req);

            while (resultSet.next())
            {
                list.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("Email"),
                        resultSet.getString("numtel"),
                        resultSet.getString("password"),
                        resultSet.getString("adresse"),
                        resultSet.getInt("nbcredit"),
                        resultSet.getFloat("rate"),
                        resultSet.getString("role") ,
                        resultSet.getString("solde")
                ));
            }}catch (SQLException e)
        {e.printStackTrace();}


        return list;
    }
    public int update(User user,User user2)  throws  SQLException {
        String req = "UPDATE user SET nom = ?, prenom = ?, email = ?, numtel = ?, password = ?, adresse = ?, nbcredit = ? , rate = ? , role = ? , solde = ? WHERE id = ?";
        ps = cnx.prepareStatement(req);


        try {
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getNumTel());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getAdresse());
            ps.setInt(7, user.getNbrCredit());
            ps.setFloat(8, user.getRate());
            ps.setString(9, user.getRole());
            ps.setString(9, user.getSolde());
            ps.setInt(10, user.getId());


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps.executeUpdate();

    }
    public void delete(User user) throws SQLException {
        String req = "DELETE FROM user WHERE id = ?";
        ps = cnx.prepareStatement(req);
        try {
            ps.setInt(1, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            throw e;
        }
    }
    public User getUserByEmail(String email) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM user WHERE Email = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                           resultSet.getInt("id"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("Email"),
                            resultSet.getString("numtel"),
                            resultSet.getString("password"),
                            resultSet.getString("adresse"),
                            resultSet.getInt("nbcredit"),
                            resultSet.getFloat("rate"),
                            resultSet.getString("role") ,
                            resultSet.getString("solde")


                    );
                }
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            throw ex;
        }

        return user;
    }

}