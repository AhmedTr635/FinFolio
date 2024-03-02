package com.example.finfolio.Service;

import Models.Model;
import com.example.finfolio.Entite.Depense;
import com.example.finfolio.util.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DepenseService {
    private final Connection connexion;
    private Statement ste;
    private PreparedStatement pst;
    private static DepenseService instance;

    public DepenseService() {
        connexion= DataSource.getInstance().getCnx();
    }

    public static DepenseService getInstance() {
        if (instance == null) {
            instance = new DepenseService();
        }
        return instance;
    }

    public void add(Depense d) {

        String requete="insert into depense (taux_tax,date,type,montant,user_id) values ('"+d.getTax().getId()+"','"+d.getDate()+"','"+d.getType()+"','"+d.getMontant()+"','"+d.getUser().getId()+"')";

        try {
            ste=connexion.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(int id ) {
        String requete = "DELETE FROM depense WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(requete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void update(Depense d,int id ) {
        String requete = "UPDATE depense SET taux_tax = ?, date = ?,type=? ,montant =?, user_id=? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(requete);
            preparedStatement.setInt(1, d.getTax().getId());
            preparedStatement.setDate(2, d.getSqlDate()); // Convert LocalDate to java.sql.Date
            preparedStatement.setString(3, d.getType());
            preparedStatement.setFloat(4, d.getMontant());
            preparedStatement.setInt(5, d.getUser().getId()); // Assuming getId() returns the person's unique identifier
            preparedStatement.setInt(6, id); // Assuming getId() returns the person's unique identifier
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Depense> readAll() {
        String request = "SELECT * FROM depense ORDER BY date ASC";
        List<Depense> list = new ArrayList<>();
        try {

            ste= connexion.createStatement();

            ResultSet rs= ste.executeQuery(request);

            while (rs.next()) {
                int id = rs.getInt("id");
                int tax = rs.getInt("taux_tax");
                LocalDate date = rs.getDate("date").toLocalDate();
                String type = rs.getString("type");
                int montant = rs.getInt("montant");
                // Assuming the type of the user column is int
                int user_id = rs.getInt("user_id");
                TaxService ts=new TaxService();
                UserService us=new UserService();

                list.add(new Depense(id, ts.readById(tax), date, type, montant, Model.getInstance().getUser()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public Depense readById(int id) {
        String requete = "SELECT * FROM depense WHERE id = ?";
        try {

            pst=connexion.prepareStatement(requete);
            pst.setInt(1,id);
            ResultSet rs= pst.executeQuery();

            while (rs.next())
            {
                int tax = rs.getInt("taux_tax");
                LocalDate sqlDate = rs.getDate("date").toLocalDate();
                String type = rs.getString("type");
                int montant = rs.getInt("montant");
                // Assuming the type of the user column is int
                int user_id = rs.getInt("user_id");
                UserService us=new UserService();
                TaxService ts = new TaxService();

                // Create and return the User object
                return new Depense(id, ts.readById(tax) , sqlDate, type, montant,Model.getInstance().getUser());
            }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
//recherche dynamique des depense en fonction du type et du montant
    public List<Depense> rechercherEvent(String dep) throws SQLException {
        String requeteSQL = "SELECT * FROM depense WHERE type LIKE ? OR montant LIKE ?";



        // Ajoutez le joker % pour correspondre à n'importe quelle partie de la chaîne
        dep= "%" + dep + "%";


        List<Depense> resultatsRecherche = new ArrayList<>();
        try (PreparedStatement statement = connexion.prepareStatement(requeteSQL)) {

            statement.setString(1, dep);
            statement.setString(2, dep);



            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int tax =resultSet.getInt(1);
                    int userid=resultSet.getInt(5);
                    UserService us =new UserService();
                    TaxService tx=new TaxService();
                    String type = resultSet.getString("type");
                    int montant = resultSet.getInt("montant");
                    //
                    LocalDate date = resultSet.getDate("date").toLocalDate();

                    Depense dep1 = new Depense(tx.readById(tax),
                            date,
                            type,
                            montant,
                            Model.getInstance().getUser()                    );



                    resultatsRecherche.add(dep1);
                }
            }
        }

        return resultatsRecherche;
    }
}
