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
        String request="insert into evennement (nom_event,montant,date,adresse,description) values(?,?,?,?,?)";


        try {
            pst=connexion.prepareStatement(request);
            pst.setString(1,ev.getNom());
            pst.setFloat(2,ev.getMontant());
            pst.setDate(3, Date.valueOf(ev.getDate()));
            pst.setString(4,ev.getAdresse());
            pst.setString(5,ev.getDescription());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }





    public void delete(int id) {

        String request="delete from evennement where id = ?";

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
        String request="update evennement set nom_event = ?, montant = ?, date= ?, adresse= ?, description=? where id = ? ";

        try{
            pst=connexion.prepareStatement(request);
            pst.setString(1, ev.getNom());
            pst.setFloat(2, ev.getMontant());
            pst.setDate(3, Date.valueOf(ev.getDate()));
            pst.setString(4, ev.getAdresse());
            pst.setString(5, ev.getDescription());
            pst.setInt(6, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public List<Evennement> readAll() {
        String request="select * from evennement";
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
                        rs.getString(5),
                        rs.getString(6)));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }








    public List<Evennement> rechercherEvent(String evnt) throws SQLException {
        String requeteSQL = "SELECT * FROM evennement WHERE nom_event LIKE ? OR montant LIKE ? OR date LIKE ? OR adresse LIKE ? OR description LIKE ? ";



        // Ajoutez le joker % pour correspondre à n'importe quelle partie de la chaîne
        evnt= "%" + evnt + "%";


        List<Evennement> resultatsRecherche = new ArrayList<>();
        try (PreparedStatement statement = connexion.prepareStatement(requeteSQL)) {

            statement.setString(1, evnt);
            statement.setString(2, evnt);
            statement.setString(3, evnt);
            statement.setString(4, evnt);
            statement.setString(5,evnt);


            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Evennement evennement = new Evennement(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getFloat(3),
                            resultSet.getDate(4).toLocalDate(),
                            resultSet.getString(5),
                            resultSet.getString(6));



                    resultatsRecherche.add(evennement);
                }
            }
        }

        return resultatsRecherche;
    }


    public Evennement readById(int id) {
        String request = "select * from evennement where id =?";
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
                String description= rs.getString(6);


                return new Evennement(evid,nom,montantev,date,adresse,description);




            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }





    public Evennement showUpComingEvent() {
        String requete = "SELECT * FROM evennement WHERE date > NOW() ORDER BY date ASC LIMIT 1";
        Evennement upcomingEvent = null;
        try {
            ste = connexion.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            // Check if there are any rows returned
            if (rs.next()) {
                // Retrieve the date from the result set
                java.sql.Date sqlDate = rs.getDate("date");

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



    public void updateRating(int eventId, int rating) {
        String request = "UPDATE evennement SET rating = ? WHERE id = ?";
        try{
            pst=connexion.prepareStatement(request);
            pst.setInt(1, rating);
            pst.setInt(2, eventId);

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public int getRating(int eventId) {
        String query = "SELECT rating FROM evennement WHERE id = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("rating");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Return a default value if the rating is not found
        return 0;
    }



    public static EvennementService getInstance() {
        if (instance == null) {
            instance = new EvennementService();
        }
        return instance;
    }


}