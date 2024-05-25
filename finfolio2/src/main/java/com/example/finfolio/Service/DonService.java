package com.example.finfolio.Service;

import Models.Model;
import com.example.finfolio.Entite.Don;
import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.Entite.User;
import com.example.finfolio.util.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class DonService  {


    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;

    private static  DonService instance;

    public DonService() {connexion= DataSource.getInstance().getCnx();}

    public void add(Don d) {
        String request="insert into don (montant_user,user_id_id,evenement_id_id) values(?,?,?)";

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
        String request = "DELETE FROM don WHERE evenement_id_id = ?";
        try {
            pst = connexion.prepareStatement(request);
            pst.setInt(1, eventId);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Don d, int id) {
        String request="update evenement set montant_user = ?, user_id_id = ?, evenement_id_id= ? where id = ? ";

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
                EvennementService evs = new EvennementService();
                UserService us = new UserService();

                Don don = new Don(donId, amount, Model.getInstance().getUser(), evs.readById(eventId));
                list.add(don);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
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

                return new Don(donid,montant,Model.getInstance().getUser(), evs.readById(evid));


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }




    public List<Don> getDonationsWithDetails() {
        List<Don> donations = new ArrayList<>();
        String query = "SELECT d.user_id_id, d.montant_user, d.evenement_id_id, u.nom, u.prenom, u.email, e.*  " +
                "FROM don d " +
                "JOIN user u ON d.user_id_id = u.id " +
                "JOIN evenement e ON d.evenement_id_id = e.id";
        try (
                PreparedStatement pst = connexion.prepareStatement(query);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int userId = rs.getInt("user_id_id");
                int eventId = rs.getInt("evenement_id_id");
                float montant = rs.getFloat("montant_user");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String nomEvenemment = rs.getString("nom_event");
                LocalDate dateEvenemment = rs.getDate("date").toLocalDate();

                // Create a new User object with the retrieved details
                User u = new User();
                u.setId(userId);
                u.setNom(nom);
                u.setPrenom(prenom);
                u.setEmail(email);

                // Create a new Evennement object with the retrieved details
                Evennement event = new Evennement();
                event.setId(eventId);
                event.setNom(nomEvenemment);
                event.setDate(dateEvenemment);

                // Create a new Don object with the retrieved details and add it to the list
                Don donation = new Don(montant, u, event);
                donations.add(donation);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return donations;
    }



    public List<Don> readAll1(int userId) {
        String request = "SELECT * FROM don WHERE user_id_id = ?";
        List<Don> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(request);
            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                // Assuming the type of the evennement_id column is int
                int evennementId = rs.getInt("evenement_id_id");
                Evennement evennement = EvennementService.getInstance().readById(evennementId);
                float montant = rs.getFloat("montant_user");
                // Assuming the type of the user_id column is int
                int user_id = rs.getInt("user_id_id");
                User user = Model.getInstance().getUser();

                list.add(new Don(id, montant, user, evennement));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }



    public List<Don> getDonationsForMonth(LocalDate monthDate, int userId) {
        List<Don> allDonations = readAll1(Model.getInstance().getUser().getId());
        List<Don> donationsForMonth = new ArrayList<>();

        int year = monthDate.getYear();
        int month = monthDate.getMonthValue();

        for (Don donation : allDonations) {
            LocalDate donationDate = donation.getEvennement().getDate();

            if (donationDate.getYear() == year && donationDate.getMonthValue() == month) {
                donationsForMonth.add(donation);
            }
        }

        return donationsForMonth;
    }



/*
    public List<Don> getDonationsForMonth(LocalDate monthDate) {
        List<Don> allDonations = readAll();
        List<Don> donationsForMonth = new ArrayList<>();

        int year = monthDate.getYear();
        int month = monthDate.getMonthValue();

        for (Don donation : allDonations) {
            LocalDate donationDate = donation.getEvennement().getDate();

            if (donationDate.getYear() == year && donationDate.getMonthValue() == month) {
                donationsForMonth.add(donation);
            }
        }

        return donationsForMonth;
    }
*/



    public float getTotalDonationsForEvent(int eventId) {
        float totalDonations = 0;
        List<Don> allDonations = readAll();
        // Loop through all donations and sum up the amounts for the given event
        for (Don donation : allDonations) {
            if (donation.getEvennement().getId() == eventId) {
                totalDonations += donation.getMontant_user();
            }
        }
        return totalDonations;
    }


    public static DonService getInstance() {
        if (instance == null) {
            instance = new DonService();
        }
        return instance;
    }



}
