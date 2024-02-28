package com.example.finfolio.Service;


import com.example.finfolio.Entite.ActifsCourants;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActifsCservices implements iActifsservice<ActifsCourants> {
    private Connection connection;
    private Statement ste;
    private PreparedStatement pst;

    public ActifsCservices() {
        connection= DataSource.getInstance().getCnx();
    }





    @Override
    public void delete(ActifsCourants a) {
        String requete="DELETE  FROM actif_courant WHERE user_id = ?";

        try { pst= connection.prepareStatement(requete);
            pst.setInt(1, a.getUser_id());
            pst.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void update(ActifsCourants a,String n, int m, String t) {

        String requete="UPDATE actif_courant SET Name=?, Montant = ?, Type = ? WHERE user_id = ?";

        try { pst= connection.prepareStatement(requete);
            pst.setString(1, n);
            pst.setInt(2, m);
            pst.setString(3, t);
            pst.setInt(4, a.getUser_id());
            pst.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    @Override
    public List<ActifsCourants> readAll() {
        String requete="select * from actif_courant";
        //String requete="select actif_courant.*, user.nom FROM actif_courant JOIN user ON actif_courant.user_id = user.id"
        List<ActifsCourants> list=new ArrayList<>();

        try {
            ste= connection.createStatement();
            ResultSet rs=ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new ActifsCourants(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getInt(5)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public ActifsCourants readById(int id) {
        return null;
    }


    public void add(ActifsCourants a){
        String requete="insert into actif_courant (name,montant,type,user_id) values(?,?,?,?)";

        try { pst= connection.prepareStatement(requete);
            pst.setString(1, a.getName());
            pst.setInt(2, a.getMontant());
            pst.setString(3, a.getType());
            pst.setInt(4, a.getUser_id());
            pst.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}


