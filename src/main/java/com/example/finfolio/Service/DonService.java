package com.example.finfolio.Service;

import com.example.finfolio.Entite.Don;
import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DonService  {


    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;

    private static  DonService instance;

    public DonService() {connexion= DataSource.getInstance().getCnx();}

    public void add(Don d) {
        String request="insert into don (montant_user,user_id,evenement_id) values(?,?,?)";

        try {
            pst=connexion.prepareStatement(request);
            pst.setFloat(1,d.getMontant_user());
            pst.setInt(2,d.getUser().getId());
            pst.setInt(3,d.getEvennement().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(int id) {
        String request="delete from don where id = ?";
        try {
            pst=connexion.prepareStatement(request);
            pst.setInt(1,id);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    /*    public void deleteByEventId(int eventId) {
            String request = "DELETE FROM don WHERE evenement_id = ?";
            try {
                pst = connexion.prepareStatement(request);
                pst.setInt(1, eventId);
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }*/

    public void update(Don d, int id) {
        String request="update evenement set montant_user = ?, user_id = ?, evenement_id= ? where id = ? ";

        try{
            pst=connexion.prepareStatement(request);
            pst.setFloat(1, d.getMontant_user());
            pst.setInt(2, d.getUser().getId());
            pst.setInt(3, d.getEvennement().getId());
            pst.setInt(4, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public List<Don> readAll() {
        String request = "SELECT * FROM don";
        List<Don> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            ResultSet rs = ste.executeQuery(request);
            while (rs.next()) {
                int donId = rs.getInt(1);
                float amount = rs.getFloat(2);
                int userId = rs.getInt(3);
                int eventId = rs.getInt(4);
                // Assuming you have a method to retrieve an Evenement by its ID
                EvennementService evs = new EvennementService();
                UserService us = new UserService();

                Don don = new Don(donId, amount, us.readById(userId), evs.readById(eventId));
                list.add(don);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public List<Evennement> searchByName(String name) {
        return null;
    }


    public Don readById(int id) {
        String request = "select * from don where id =?";
        try {
            pst=connexion.prepareStatement(request);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int donid = rs.getInt(1);
                float montant = rs.getFloat(2);
                int userid = rs.getInt(3);
                int evid = rs.getInt(4);
                EvennementService evs = new EvennementService();
                UserService us = new UserService();

                return new Don(donid,montant,us.readById(userid), evs.readById(evid));


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    public static DonService getInstance() {
        if (instance == null) {
            instance = new DonService();
        }
        return instance;
    }



}
