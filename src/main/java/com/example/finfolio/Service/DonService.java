package com.example.finfolio.Service;

import com.example.finfolio.Entite.Don;
import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Entite.User;
import com.example.finfolio.util.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;


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



        public void deleteByEventId(int eventId) {
            String request = "DELETE FROM don WHERE evenement_id = ?";
            try {
                pst = connexion.prepareStatement(request);
                pst.setInt(1, eventId);
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

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

    public List<Don> getDonationsWithDetails() {
        List<Don> donations = new ArrayList<>();
        String query = "SELECT d.user_id, d.montant_user, d.evenement_id, u.nom, u.prenom, u.email, e.*  " +
                "FROM don d " +
                "JOIN user u ON d.user_id = u.id " +
                "JOIN evenement e ON d.evenement_id = e.id";
        try (
                PreparedStatement pst = connexion.prepareStatement(query);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int eventId =rs.getInt("evenement_id");
                float montant = rs.getFloat("montant_user");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String nomEvenemment = rs.getString("nom_event");
                LocalDate dateEvenemment = rs.getDate("date").toLocalDate();

                // Créer un objet User avec les détails de l'utilisateur
                UserService us = new UserService();
                EvennementService ev = new EvennementService();
                User u =  us.readById(userId);
                Evennement event= ev.readById(eventId);
/*
                System.out.println(event);
*/
               event.setNom(nomEvenemment);
                System.out.println(nomEvenemment);
                event.setDate(dateEvenemment);
                u.setEmail(email);
                u.setNom(nom);



                // Créer un objet Don avec les détails récupérés et l'ajouter à la liste
                Don donation = new Don(montant, u, event);
                donations.add(donation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
        return donations;
    }







/*
    public List<Don> getDonationsWithDetails(int eventId) {
        List<Don> donations = new ArrayList<>();
        String query = "SELECT d.user_id, d.montant_user, d.evenement_id, u.nom, u.prenom, u.email, e.nom, e.date " +
                "FROM don d " +
                "JOIN user u ON d.user_id = u.id " +
                "JOIN evenement e ON d.evenement_id = e.id " +
                "WHERE d.evenement_id = ?";
        try (
             PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setInt(1, eventId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    float montant = rs.getFloat("montant_user");
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String email = rs.getString("email");
                    String nomEvenemment = rs.getString("nom");
                    LocalDate dateEvenemment = rs.getDate("date").toLocalDate();


                    // Créer un objet User avec les détails de l'utilisateur
                   UserService us = new UserService();
                   EvennementService ev = new EvennementService();
                  User u =  us.readById(userId);
                  Evennement event= ev.readById(eventId);
                  event.setNom(nomEvenemment);
                  event.setDate(dateEvenemment);
                  u.setEmail(email);

                  u.setNom(nom);
                    // Créer un objet Don avec les détails récupérés et l'ajouter à la liste
                    Don donation = new Don(montant,u ,event);
                    donations.add(donation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
        return donations;
    }
*/



    public static DonService getInstance() {
        if (instance == null) {
            instance = new DonService();
        }
        return instance;
    }



}
