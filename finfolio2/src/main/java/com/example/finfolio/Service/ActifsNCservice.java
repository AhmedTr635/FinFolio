package com.example.finfolio.Service;

import com.example.finfolio.Entite.User;
import com.example.finfolio.Portfolio.Entite.ActifsNonCourants;
import com.example.finfolio.util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActifsNCservice  {
    private Connection connection;
    private Statement ste;
    private PreparedStatement pst;

    public ActifsNCservice() {
        connection= DataSource.getInstance().getCnx();
    }






    public void delete(ActifsNonCourants a) {
        String requete="DELETE  FROM actif_non_courant WHERE id = ?";

        try { pst= connection.prepareStatement(requete);
            pst.setInt(1,
                    a.getId());
            pst.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public void update(ActifsNonCourants a,String n, String t, float v, float p) {

        String requete="UPDATE actif_non_courant SET Name = ?, Type = ?, Valeur = ?,Prix_achat = ?WHERE id = ?";

        try { pst= connection.prepareStatement(requete);
            pst.setString(1, n);
            pst.setString(2, t);

            pst.setFloat(3, v);
            pst.setFloat(4,p);
            pst.setInt(5,a.getId());
            pst.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public double totalamortissement() {
        List<ActifsNonCourants> actifsNonCourantsList = new ArrayList<>();
        String requete="SELECT * FROM actif_non_courant";

        try {
            Statement ste = connection.createStatement();
            ResultSet rs=ste.executeQuery(requete);


            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                int valeur = rs.getInt("valeur");
                int prix = rs.getInt("prix_achat");
                User user = fetchUserById(rs.getInt("user_id"));

                ActifsNonCourants actifNonCourant = new ActifsNonCourants(id, name,type, valeur, prix, user);
                actifsNonCourantsList.add(actifNonCourant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Calculate the total depreciation for all elements in the list
        double totalDepreciation = 0;
        for (ActifsNonCourants actifNonCourant : actifsNonCourantsList) {

            totalDepreciation += actifNonCourant.calculateDepreciation(); // Assuming you have added a getDepreciation() method in ActifsCourants class
        }
        return totalDepreciation;
    }




    public List<ActifsNonCourants> readAll() {
        String requete="select * from actif_non_courant";
        List<ActifsNonCourants> list=new ArrayList<>();

        try {
            ste= connection.createStatement();
            ResultSet rs=ste.executeQuery(requete);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                float valeur = rs.getFloat("valeur");
                float prix=rs.getFloat("prix_achat");


                User user = fetchUserById(rs.getInt("user_id"));

                ActifsNonCourants actifNonCourant = new ActifsNonCourants(id, name, type,valeur,prix, user);
                list.add(actifNonCourant);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public User fetchUserById(int userId) throws SQLException {

        UserService us= new UserService();
        return us.readAll().stream().filter(u->u.getId()==userId).findFirst().get();
    }


    public ActifsNonCourants readById(int id) {
        return null;
    }


    public void add(ActifsNonCourants p){
        String requete="insert into actif_non_courant (name,type,valeur,prix_achat,user_id) values(?,?,?,?,?)";

        try { pst= connection.prepareStatement(requete);
            pst.setString(1, p.getName());
            pst.setString(2, p.getType());
            pst.setFloat(3, p.getValeur());
            pst.setFloat(4, p.getPrix_achat());
            pst.setInt(5,p.getUser().getId());
            pst.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}


