package com.example.finfolio.Service;

import com.example.finfolio.Entite.User;
import com.example.finfolio.Portfolio.Entite.ActifsCourants;
import com.example.finfolio.util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActifsCservices  {
    private Connection connection;
    private Statement ste;
    private PreparedStatement pst;

    public ActifsCservices() {
        connection= DataSource.getInstance().getCnx();
    }






    public void delete(ActifsCourants a) {
        String requete="DELETE  FROM actif_courant WHERE user_id = ?";

        try { pst= connection.prepareStatement(requete);
            pst.setInt(1, a.getUser().getId());
            pst.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public void update(ActifsCourants a,String n, float m, String t) {

            String requete="UPDATE actif_courant SET Name=?, Montant = ?, Type = ? WHERE id = ?";

            try { pst= connection.prepareStatement(requete);
                pst.setString(1, n);
                pst.setFloat(2, m);
                pst.setString(3, t);
                pst.setInt(4,a.getId());
                pst.executeUpdate();




            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    // Retrieve the data from the database and store it in a list of ActifsCourants objects
    public double totalamortissement() {
        List<ActifsCourants> actifsCourantsList = new ArrayList<>();
        String requete="SELECT * FROM actif_courant";

        try {
            Statement ste= connection.createStatement();
            ResultSet rs=ste.executeQuery(requete);


            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int montant = rs.getInt("montant");
                String type = rs.getString("type");
                User user = fetchUserById(rs.getInt("user_id"));

                ActifsCourants actifCourant = new ActifsCourants(id, name, montant, type, user);
                actifsCourantsList.add(actifCourant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Calculate the total depreciation for all elements in the list
        double totalDepreciation = 0;
        for (ActifsCourants actifCourant : actifsCourantsList) {

            totalDepreciation += actifCourant.calculateDepreciation(); // Assuming you have added a getDepreciation() method in ActifsCourants class
        }
        return totalDepreciation;
    }



    public List<ActifsCourants> readAll() {
        String requete="select * from actif_courant";
        //String requete="select actif_courant.*, user.nom FROM actif_courant JOIN user ON actif_courant.user_id = user.id"
        List<ActifsCourants> list=new ArrayList<>();

        try {
            ste= connection.createStatement();
            ResultSet rs=ste.executeQuery(requete);
            while (rs.next()) {
                int id =rs.getInt(1);
                        String name=rs.getString(2);
                        float montant=rs.getFloat(3);
                        String type=rs.getString(4);
                        User user = fetchUserById(rs.getInt("user_id"));
                ActifsCourants actifCourant = new ActifsCourants(id, name, montant, type, user);
                list.add(actifCourant);
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


    public ActifsCourants readById(int id) {
        return null;
    }


    public void add(ActifsCourants a){
        String requete="insert into actif_courant (name,montant,type,user_id) values(?,?,?,?)";

        try { pst= connection.prepareStatement(requete);
            pst.setString(1, a.getName());
            pst.setFloat(2, a.getMontant());
            pst.setString(3, a.getType());
            pst.setInt(4, a.getUser().getId());
            pst.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}

