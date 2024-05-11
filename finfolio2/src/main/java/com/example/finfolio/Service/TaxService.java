package com.example.finfolio.Service;

import com.example.finfolio.Entite.Tax;
import com.example.finfolio.util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaxService {


    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;

    public TaxService() {
        connexion= DataSource.getInstance().getCnx();
    }
    public void add(Tax t) {
        String requete="insert into tax (montant, type,optimisation) values ('"+t.getmontantTax()+"','"+t.getType()+"','"+t.getOptimisation()+"')";

        try {
            ste=connexion.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(int id) {
        String requete = "DELETE FROM tax WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(requete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public  void update(Tax t, int id) {
        String requete = "UPDATE tax SET montant= ?, type= ?,optimisation= ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(requete);
            preparedStatement.setDouble(1, t.getmontantTax());
            preparedStatement.setString(2,t.getType());
            preparedStatement.setString(3, t.getOptimisation());
            preparedStatement.setInt(4, id); // Assuming getId() returns the person's unique identifier
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Tax> readAll() {
        String requete="SELECT * FROM tax";
        List<Tax> list=new ArrayList<>();
        try {
            ste= connexion.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                list.add(new Tax(
                        rs.getInt("id"),
                        rs.getFloat("montant"),
                        rs.getString("type"),
                        rs.getString("optimisation")


                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;    }


    public Tax readById(int id) {
        String requete = "SELECT * FROM tax WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(requete);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                // Créer une nouvelle instance de Depense et la retourner
                Tax tax = new Tax(
                        rs.getInt("id"),
                        rs.getFloat("montant"),
                        rs.getString("type"),
                        rs.getString("optimisation")
                );
                return tax;
            } else {
                System.out.println("hello.");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int addAndGetId(Tax tax) throws SQLException {
        // SQL query to insert Tax object into database
        String sql = "INSERT INTO tax (montant, type, optimisation) VALUES (?, ?, ?)";

        try (
                // Establish database connection
                PreparedStatement pst = connexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        ) {
            // Set parameters in prepared statement
            pst.setDouble(1, tax.getmontantTax());
            pst.setString(2, tax.getType());
            pst.setString(3, tax.getOptimisation());

            // Execute query to insert Tax object into database
            pst.executeUpdate();

            // Retrieve auto-generated keys (ID) from database
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return the auto-generated ID
            }
        }

        // Return -1 if auto-generated ID couldn't be retrieved
        return -1;
    }
    public double sommeTaxByDepense(){
        TaxService ts= new TaxService();
        List<Tax> taxes = ts.readAll();
        double somme = taxes.stream()
                .filter(tax -> tax.getType().equals("depense"))
                .mapToDouble(Tax::getmontantTax)
                .sum();
        return somme;


    }
}
