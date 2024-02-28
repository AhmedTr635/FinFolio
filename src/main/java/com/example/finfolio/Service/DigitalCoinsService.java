package com.example.finfolio.Service;

import com.example.finfolio.Entite.User;
import com.example.finfolio.util.DataSource;
import com.example.finfolio.Entite.DigitalCoins;


import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DigitalCoinsService implements IService <DigitalCoins>{
    private Connection cnx;
    private PreparedStatement pst;

    public DigitalCoinsService() {
        cnx = DataSource.getInstance().getCnx();
    }
    /*int id,String code, double recentValue, Date dateAchat, Date dateVente, double montant, float leverage, double stopLoss, User user, double ROI, double prixAchat, double tax*/

    @Override
    public void add(DigitalCoins dc) {
        try {
            String query = "INSERT INTO digital_coins (id,recent_value,dateAchat,dateVente,montant,leverage,stoploss,userid,ROI,prixachat,tax,code) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1,dc.getId() );
            pst.setString(12,dc.getCode() );
            pst.setDouble(2,dc.getRecentValue());
            pst.setDate(3,Date.valueOf(dc.getDateAchat()));
            pst.setDate(4,Date.valueOf(dc.getDateVente()));
            pst.setDouble(5,dc.getMontant());
            pst.setFloat(6,dc.getLeverage());
            pst.setDouble(7,dc.getStopLoss());
            pst.setInt(8,dc.getUser().getId());
            pst.setDouble(9,dc.getROI());
            pst.setDouble(10,dc.getPrixAchat() );
            pst.setDouble(11,dc.getTax());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("DigitalCoin added successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void delete(DigitalCoins dc) {
        try {
            String query = "DELETE FROM digital_coins WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, dc.getId());
            pst.executeUpdate();
            System.out.println("digital coin deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(DigitalCoins dc, int id) {
        try {
            String query = "UPDATE digital_coins SET  recent_value=?,dateAchat=?,dateVente=?,montant=?,leverage=?,stoploss=?,userid=?,ROI=?,prixachat=?,tax=?,code=? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(11,dc.getCode());
            pst.setDouble(1,dc.getRecentValue());
            pst.setDate(2,Date.valueOf(dc.getDateAchat()));
            pst.setDate(3,Date.valueOf(dc.getDateVente()));
            pst.setDouble(4,dc.getMontant());
            pst.setFloat(5,dc.getLeverage());
            pst.setDouble(6,dc.getStopLoss());
            pst.setInt(7,dc.getUser().getId());
            pst.setDouble(8,dc.getROI());
            pst.setDouble(9,dc.getPrixAchat() );
            pst.setDouble(10,dc.getTax());
            pst.setDouble(12,id);
            pst.executeUpdate();
            System.out.println("Digital coin updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public DigitalCoins readById(int id) {

        DigitalCoins dc = null;
        try {
            String query = "SELECT * FROM digital_coins WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

                double recentValue = rs.getDouble("recent_value");
                String code = rs.getString("code");
                LocalDate dateAchat = rs.getDate("dateAchat").toLocalDate();
                LocalDate dateVente = rs.getDate("dateVente").toLocalDate();
                double montant = rs.getDouble("montant");
                float leverage = rs.getFloat("leverage");
                double stopLoss = rs.getDouble("stoploss");
                int userID = rs.getInt("userid");
                UserService us=new UserService();
                User user=us.readById(userID);
                double ROI = rs.getDouble("ROI");
                double prixAchat = rs.getDouble("prixachat");
                double tax = rs.getDouble("tax");
                /*private int id;
    private String code;
    private double recentValue;
    private java.sql.Date dateAchat;
    private  java.sql.Date dateVente;
    private double montant;
    private float leverage;
    private double stopLoss;
    private User user;
    private double ROI;
    private double prixAchat;
    private double tax;
                */


                dc = new DigitalCoins(id,code,recentValue,dateAchat,dateVente,montant,leverage,stopLoss,user,ROI,prixAchat,tax);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dc;
    }

    @Override
    public Set<DigitalCoins> readAll() {
        Set<DigitalCoins> digitalCoinsSet = new HashSet<>();
        try {
            String query = "SELECT * FROM digital_coins";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                double recentValue = rs.getDouble("recent_value");
                String code = rs.getString("code");
                LocalDate dateAchat = rs.getDate("dateAchat").toLocalDate();
                LocalDate dateVente = rs.getDate("dateVente").toLocalDate();
                double montant = rs.getDouble("montant");
                float leverage = rs.getFloat("leverage");
                double stopLoss = rs.getDouble("stoploss");
                int userID = rs.getInt("userid");
                double ROI = rs.getDouble("ROI");
                double prixAchat = rs.getDouble("prixachat");
                double tax = rs.getDouble("tax");
                UserService us=new UserService();
                User user=us.readById(userID);
                DigitalCoins dc = new DigitalCoins( id,code,recentValue,dateAchat,dateVente,montant,leverage,stopLoss,user,ROI,prixAchat,tax);
                digitalCoinsSet.add(dc);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return digitalCoinsSet;
    }
}

