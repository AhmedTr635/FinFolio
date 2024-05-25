package com.example.finfolio.Investissement.Service;

import Models.Model;
import com.example.finfolio.Investissement.Entite.Investissement;
import com.example.finfolio.Investissement.Entite.RealEstate;
import com.example.finfolio.Investissement.Entite.User;
import com.example.finfolio.util.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InvestissementService implements IService<Investissement> {
    private Connection cnx;
    private PreparedStatement pst;
    /*private int id;
    private double montant;
    private double prixAchat;
    private LocalDate dateAchat;
    private double ROI;
    private RealEstate Re;
    private User user;
    private double tax;*/

    public InvestissementService() {
        cnx= DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Investissement inv) {
        try {
            String query = "INSERT INTO investissement (id,montant,prix_achat,date_achat,roi,re_id,user_id,tax) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1,inv.getId() );
            pst.setDouble(2,inv.getMontant());
            pst.setDouble(3,inv.getPrixAchat());
            pst.setDate(4, Date.valueOf(inv.getDateAchat()));
            pst.setDouble(5,inv.getROI());
            pst.setDouble(6,inv.getRe().getId());
            pst.setFloat(7, Model.getInstance().getUser().getId());
            pst.setDouble(8,inv.getTax());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("investissement added successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Investissement inv) {
        try {
            String query = "DELETE FROM investissement WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, inv.getId());
            pst.executeUpdate();
            System.out.println("inv deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Set<Investissement> fetchInvestissementByUserId(int userId) {
        Set<Investissement> investissements = new HashSet<>();
        try {
            String query = "SELECT * FROM investissement WHERE user_id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                double montant = rs.getDouble("montant");
                double prixAchat = rs.getDouble("prix_achat");
                LocalDate dateAchat = rs.getDate("date_achat").toLocalDate();
                double ROI = rs.getDouble("roi");
                int reId = rs.getInt("re_id");
                double tax = rs.getDouble("tax");
                RealEstate re = new RealEstateService().readById(reId);
                User user = new UserService().readById(Model.getInstance().getUser().getId());
                // You may need to fetch RealEstate and User objects here
                Investissement inv = new Investissement(id, montant, prixAchat, dateAchat, ROI, re, user, tax);
                investissements.add(inv);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return investissements;
    }

    @Override
    public void update(Investissement inv, int id) {
        try {
            String query = "UPDATE investissement SET montant=?,prix_achat=?,date_achat=?,roi=?,re_id=?,user_id=?,tax=? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);

            pst.setDouble(1,inv.getMontant());
            pst.setDouble(2,inv.getPrixAchat());
            pst.setDate(3,Date.valueOf(inv.getDateAchat()));
            pst.setDouble(4,inv.getROI());
            pst.setInt(5,inv.getRe().getId());
            pst.setInt(6,Model.getInstance().getUser().getId());
            pst.setDouble(7,inv.getTax());
            pst.setInt(8,id);

            pst.executeUpdate();
            System.out.println("Investissement updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Investissement readById(int id) {
        Investissement inv = null;
        try {
            String query = "SELECT * FROM investissement WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

                double montant = rs.getDouble("montant");
                double prixAchat = rs.getDouble("prix_achat");
                LocalDate dateAchat = rs.getDate("date_achat").toLocalDate();
                double ROI = rs.getDouble("roi");
                int reId = rs.getInt("re_id");
                int userId = rs.getInt("user_id");
                double tax = rs.getDouble("tax");
                UserService us=new UserService();
                User user=us.readById(userId);
                RealEstateService reservice=new RealEstateService();
                RealEstate re=reservice.readById(reId);

                /*private int id;
    private double montant;
    private double prixAchat;
    private LocalDate dateAchat;
    private double ROI;
    private RealEstate Re;
    private User user;
    private double tax;
                */


                inv = new Investissement(id,montant,prixAchat,dateAchat,ROI,re,user,tax);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return inv;
    }

    @Override
    public Set<Investissement> readAll() {
        Set<Investissement> InvestissementSet = new HashSet<>();
        try {
            String query = "SELECT * FROM investissement";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id=rs.getInt("id");
                double montant = rs.getDouble("montant");
                double prixAchat = rs.getDouble("prix_achat");
                LocalDate dateAchat = rs.getDate("date_achat").toLocalDate();
                double ROI = rs.getDouble("roi");
                int reId = rs.getInt("re_id");
                int userId = rs.getInt("user_id");
                double tax = rs.getDouble("tax");
                UserService us=new UserService();
                User user=us.readById(userId);
                RealEstateService reservice=new RealEstateService();
                RealEstate re=reservice.readById(reId);
                Investissement inv = new Investissement(id,montant,prixAchat,dateAchat,ROI,re,user,tax);
                InvestissementSet.add(inv);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return InvestissementSet;
    }
    public List<Investissement> selectInvestissementByRealEstateId(int realEstateId) {
        List<Investissement> investissements = new ArrayList<>();
        try {
            String query = "SELECT * FROM investissement WHERE re_id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, realEstateId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                double montant = rs.getDouble("montant");
                double prixAchat = rs.getDouble("prix_achat");
                LocalDate dateAchat = rs.getDate("date_achat").toLocalDate();
                double ROI = rs.getDouble("roi");
                int userId = rs.getInt("user_id");
                double tax = rs.getDouble("tax");
                UserService us = new UserService();
                User user = us.readById(userId);
                RealEstateService reservice = new RealEstateService();
                RealEstate re = reservice.readById(realEstateId);
                Investissement inv = new Investissement(id, montant, prixAchat, dateAchat, ROI, re, user, tax);
                investissements.add(inv);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return investissements;
    }

}
