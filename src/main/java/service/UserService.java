package service;

import entite.User;
import interfaces.IService;
import util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {
    private Statement ste;

    private PreparedStatement ps;
    private Connection cnx;

    public UserService() {
        cnx= DataSource.getInstance().getCnx();
    }

    public void add(User u)throws SQLException {
        String req="INSERT INTO user (nom,prenom,email,numtel,password,adresse,nbcredit,rate,role) values (?,?,?,?,?,?,?,?,?)";
        ps= cnx.prepareStatement(req);
        try {



            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setInt(4, u.getNumTel());
            ps.setString(5, u.getPassword());
            ps.setString(6, u.getAdresse());
            ps.setInt(7, u.getNbrCredit());
            ps.setFloat(8, u.getRate());
            ps.setString(9, u.getRole());
            ps.executeUpdate();
        } catch (SQLException e)
        {e.printStackTrace();}



    }
    public List<User> readAll()throws SQLException
    {
        String req="Select * from user ";
        List<User>list=new ArrayList<>();
        ste= cnx.createStatement();
        try {


        ResultSet rs= ste.executeQuery(req);

        while (rs.next())
        {
            list.add(new User(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getString(6) ,
                    rs.getString(7),
                    rs.getInt(8),
                    rs.getFloat(9),
                    rs.getString(10))
            );
        }}catch (SQLException e)
        {e.printStackTrace();}


        return list;
    }
    public int update(User user,User user2)  throws  SQLException {
        String req = "UPDATE user SET nom = ?, prenom = ?, email = ?, numtel = ?, password = ?, adresse = ?, nbcredit = ? , rate = ? , role = ? WHERE id = ?";
        ps = cnx.prepareStatement(req);


        try {
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getNumTel());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getAdresse());
            ps.setInt(7, user.getNbrCredit());
            ps.setFloat(8, user.getRate());
            ps.setString(9, user.getRole());
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
}
