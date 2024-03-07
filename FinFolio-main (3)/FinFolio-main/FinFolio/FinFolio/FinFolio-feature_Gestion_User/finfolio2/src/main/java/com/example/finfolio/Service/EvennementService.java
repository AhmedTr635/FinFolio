package com.example.finfolio.Service;


import com.example.finfolio.Entite.Evennement;
import com.example.finfolio.util.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class EvennementService {


    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;

    private static EvennementService instance;
    public EvennementService() {connexion= DataSource.getInstance().getCnx();}


    public void add(Evennement ev) {
        String request="insert into evenement (nom_event,montant,date,adresse) values(?,?,?,?)";

        try {
            pst=connexion.prepareStatement(request);
            pst.setString(1,ev.getNom());
            pst.setFloat(2,ev.getMontant());
            pst.setDate(3, Date.valueOf(ev.getDate()));
            pst.setString(4,ev.getAdresse());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }





    public void delete(int id) {

        String request="delete from evenement where id = ?";

        try{
            DonService.getInstance().deleteByEventId(id);
            pst=connexion.prepareStatement(request);
            pst.setInt(1,id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




    public void update(Evennement ev, int id) {
        String request="update evenement set nom_event = ?, montant = ?, date= ?, adresse= ? where id = ? ";

        try{
            pst=connexion.prepareStatement(request);
            pst.setString(1, ev.getNom());
            pst.setFloat(2, ev.getMontant());
            pst.setDate(3, Date.valueOf(ev.getDate()));
            pst.setString(4, ev.getAdresse());
            pst.setInt(5, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public List<Evennement> readAll() {
        String request="select * from evenement";
        List<Evennement> list=new ArrayList<>();
        try {
            ste= connexion.createStatement();
            ResultSet rs = ste.executeQuery(request);
            while(rs.next()){
                list.add(new Evennement(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getString(5)));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }



/*    public List<Evennement> searchByName(String name) {
        List<Evennement> events = new ArrayList<>();
        String request="select * from evenement where nom like ?";
        try (PreparedStatement pst = connexion.prepareStatement(request)) {
            pst.setString(1, "%" + name + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                float montant = rs.getFloat("montant");
                LocalDate date = rs.getDate("date").toLocalDate();
                String adresse = rs.getString("adresse");
                Evennement event = new Evennement(id, nom, montant, date, adresse);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to search events by name");
        }

        return events;
    }*/




    public List<Evennement> rechercherEvent(String evnt) throws SQLException {
        String requeteSQL = "SELECT * FROM evenement WHERE nom_event LIKE ? OR montant LIKE ? OR date LIKE ? OR adresse LIKE ?";



        // Ajoutez le joker % pour correspondre à n'importe quelle partie de la chaîne
        evnt= "%" + evnt + "%";


        List<Evennement> resultatsRecherche = new ArrayList<>();
        try (PreparedStatement statement = connexion.prepareStatement(requeteSQL)) {

            statement.setString(1, evnt);
            statement.setString(2, evnt);
            statement.setString(3, evnt);
            statement.setString(4, evnt);


            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Evennement evennement = new Evennement(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getFloat(3),
                            resultSet.getDate(4).toLocalDate(),
                            resultSet.getString(5));



                    resultatsRecherche.add(evennement);
                }
            }
        }

        return resultatsRecherche;
    }


    public Evennement readById(int id) {
        String request = "select * from evenement where id =?";
        try {
            pst=connexion.prepareStatement(request);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int evid = rs.getInt(1);
                String nom= rs.getString(2);
                float montantev = rs.getFloat(3);
                LocalDate date = rs.getObject(4, LocalDate.class);
                String adresse = rs.getString(5);


                return new Evennement(evid,nom,montantev,date,adresse);


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }





    public Evennement showUpComingEvent() {
        String requete = "SELECT * FROM evenement WHERE date > NOW() ORDER BY date ASC LIMIT 1";
        Evennement upcomingEvent = null;
        try {
            ste = connexion.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            // Check if there are any rows returned
            if (rs.next()) {
                // Retrieve the date from the result set
                Date sqlDate = rs.getDate("date");

                // Convert the java.sql.Date to LocalDate
                LocalDate date = sqlDate.toLocalDate();
                // Create the Depense object
                upcomingEvent = new Evennement(
                        rs.getString(2),

                        rs.getFloat(3),
                        date,
                        rs.getString(5));


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return upcomingEvent;

    }




    public static EvennementService getInstance() {
        if (instance == null) {
            instance = new EvennementService();
        }
        return instance;
    }


}