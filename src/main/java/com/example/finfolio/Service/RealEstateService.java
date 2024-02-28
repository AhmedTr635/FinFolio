package com.example.finfolio.Service;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.Set;

import com.example.finfolio.Entite.*;
import com.example.finfolio.Entite.DigitalCoins ;
import com.example.finfolio.util.DataSource ;

public class RealEstateService implements IService<RealEstate>{
    private Connection cnx;
    private PreparedStatement pst;

    public RealEstateService() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(RealEstate re) {
        try {
            String query = "INSERT INTO real_estate (id,emplacement, ROI, valeur, nbChambres, superficie) VALUES (?,?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1,re.getId());
            pst.setString(2, re.getEmplacement());
            pst.setFloat(3, re.getROI());
            pst.setDouble(4, re.getValeur());
            pst.setInt(5, re.getNbChambres());
            pst.setFloat(6, re.getSuperficie());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            /*for (Map.Entry<User, Double> entry : re.getUserParticipation().entrySet()) {
                String userParticipationQuery = "INSERT INTO user_participation (real_estate_id, user_id, participation) VALUES (?, ?, ?)";
                PreparedStatement userPst = cnx.prepareStatement(userParticipationQuery);
                userPst.setInt(1, id);
                userPst.setInt(2, entry.getKey().getId());
                userPst.setDouble(3, entry.getValue());
                userPst.executeUpdate();
            }*/
            System.out.println("Real estate added successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void delete(RealEstate re) {
        try {
            String query = "DELETE FROM real_estate WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, re.getId());
            pst.executeUpdate();
            System.out.println("Real estate deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Map<User, Double> fetchUserParticipation(int realEstateId) {
        Map<User, Double> userParticipation = new HashMap<>();
        try {
            String query = "SELECT idUser, montant FROM investissement WHERE idRE = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, realEstateId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("idUser");
                double participation = rs.getDouble("montant");
                UserService usS=new UserService();
                User user = usS.readById(userId);

                // Add the user and participation to the map
                userParticipation.put(user, participation);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userParticipation;
    }

    @Override
    public void update(RealEstate re, int id) {
        try {
            String query = "UPDATE real_estate SET emplacement=?, ROI=?, valeur=?, nbChambres=?, superficie=? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);

            pst.setString(1, re.getEmplacement());
            pst.setFloat(2, re.getROI());
            pst.setDouble(3, re.getValeur());
            pst.setInt(4, re.getNbChambres());
            pst.setFloat(5, re.getSuperficie());
            pst.setDouble(6,id);

            pst.executeUpdate();
            System.out.println("Real estate updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public RealEstate readById(int id) {
        RealEstate re = null;
        try {
            String query = "SELECT * FROM real_estate WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String emplacement = rs.getString("emplacement");
                float ROI = rs.getFloat("ROI");
                double valeur = rs.getDouble("valeur");
                int nbChambres = rs.getInt("nbchambres");
                float superficie = rs.getFloat("superficie");

                Map<User, Double> userParticipation = fetchUserParticipation(id);
                re = new RealEstate(id, emplacement, ROI, valeur, nbChambres, superficie, userParticipation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return re;
    }


    @Override
    public Set<RealEstate> readAll() {
        Set<RealEstate> realEstateSet = new HashSet<>();
        try {
            String query = "SELECT * FROM real_estate";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String emplacement = rs.getString("emplacement");
                float ROI = rs.getFloat("ROI");
                double valeur = rs.getDouble("valeur");
                int nbChambres = rs.getInt("nbChambres");
                float superficie = rs.getFloat("superficie");
                //Fetch user participation information from another table if needed
                Map<User, Double> userParticipation = fetchUserParticipation(id);
                RealEstate re = new RealEstate(id, emplacement, ROI, valeur, nbChambres, superficie, userParticipation);
                realEstateSet.add(re);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return realEstateSet;
    }
}



