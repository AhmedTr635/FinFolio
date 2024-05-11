package com.example.finfolio.Investissement.Service;

import com.example.finfolio.Investissement.Entite.RealEstate;
import com.example.finfolio.Investissement.Entite.User;
import com.example.finfolio.util.DataSource;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class RealEstateService implements IService<RealEstate>{
    private Connection cnx;
    private PreparedStatement pst;

    public RealEstateService() {
        cnx= DataSource.getInstance().getCnx();
    }

    @Override
    public void add(RealEstate re) {
        try {
            String query = "INSERT INTO real_estate (id,emplacement, ROI, valeur, nbChambres, superficie,name) VALUES (?,?,?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1,re.getId());
            pst.setString(2, re.getEmplacement());
            pst.setFloat(3, re.getROI());
            pst.setDouble(4, re.getValeur());
            pst.setInt(5, re.getNbChambres());
            pst.setFloat(6, re.getSuperficie());
            pst.setString(7, re.getName());
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
    public void addWImg(RealEstate re) {
        try {
            String query = "INSERT INTO real_estate (id,emplacement, ROI, valeur, nbChambres, superficie,name,image_data) VALUES (?,?,?,?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1,re.getId());
            pst.setString(2, re.getEmplacement());
            pst.setFloat(3, re.getROI());
            pst.setDouble(4, re.getValeur());
            pst.setInt(5, re.getNbChambres());
            pst.setFloat(6, re.getSuperficie());
            pst.setString(7, re.getName());
            pst.setBytes(8, re.getImageData());
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
    public Map<User, Double> fetchUserParticipation2(int realEstateId) {
        Map<User, Double> userParticipations = new HashMap<>();
        try {
            String query = "SELECT u.*, i.montant FROM user u JOIN investissement i ON u.id = i.idUser WHERE i.idRE = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, realEstateId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("id");
                String name = rs.getString("name");
                String prenom = rs.getString("prenom");
                double montant = rs.getDouble("montant");

                User user = new User(userId, name, prenom);
                userParticipations.put(user, montant);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userParticipations;
    }
    private double calculateTotalParticipation(RealEstate re) {
        double totalParticipation = 0.0;
        Map<User, Double> userParticipation = fetchUserParticipation(re.getId());
        for (Map.Entry<User, Double> entry : userParticipation.entrySet()) {
            totalParticipation += entry.getValue();
        }
        return totalParticipation;
    }
    public List<RealEstate> getAllDigitalCoinsWithUserId() {
        List<RealEstate> RealEstatesList = new ArrayList<>();
        String query = "SELECT * FROM real_estate";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String montant = rs.getString("emplacement");
                double valeur = rs.getInt("valeur");
                //LocalDate dateAchat = rs.getDate("dateAchat").toLocalDate();
                float ROI = rs.getFloat("ROI");
                double totalPart=calculateTotalParticipation(readById(id));

                // Get other fields as needed

                // Get userId from the result set


                // Create DigitalCoins object and set userId
                /*int id, String name, String emplacement, float ROI, double valeur, double totalPart*/
                RealEstate re = new RealEstate(id,name,montant,ROI,valeur,totalPart);
                RealEstatesList.add(re);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return RealEstatesList;
    }
    public List<RealEstate> sortByNbrClick(Set<RealEstate> realEstates) {
        // Sort the real estates based on the number of clicks using streams
        List<RealEstate> sortedRealEstates = realEstates.stream()
                .sorted(Comparator.comparingInt(RealEstate::getNbrclick).reversed())
                .collect(Collectors.toList());

        return sortedRealEstates;
    }


    @Override
    public void update(RealEstate re, int id) {
        try {
            String query = "UPDATE real_estate SET emplacement=?, ROI=?, valeur=?, nbChambres=?, superficie=?,name=?,image_data=? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);

            pst.setString(1, re.getEmplacement());
            pst.setFloat(2, re.getROI());
            pst.setDouble(3, re.getValeur());
            pst.setInt(4, re.getNbChambres());
            pst.setFloat(5, re.getSuperficie());
            pst.setDouble(8,id);
            pst.setString(6, re.getName());
            pst.setBytes(7, re.getImageData());


            pst.executeUpdate();
            System.out.println("Real estate updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    /*public void updateNbrClick(RealEstate re, int id) {
        try {
            String query = "UPDATE real_estate SET nbrclick=? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, re.getNbrclick());
            pst.setFloat(2, re.getId());
            pst.executeUpdate();
            System.out.println("Real estate updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    public void updateNbrClick(int id, int nbrclick) {
        try {
            String query = "UPDATE real_estate SET nbrclick = ? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, nbrclick);
            pst.setInt(2, id);
            pst.executeUpdate();
            System.out.println("Real estate nbrclick updated successfully.");
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
    public int readNbrClickById(int id) {
        int nbrclick = 0;
        try {
            String query = "SELECT nbrclick FROM real_estate WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nbrclick = rs.getInt("nbrclick");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nbrclick;
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
    public Image readImage(int id) {
        int nbrclick = 0;
        Image img =new Image(new ByteArrayInputStream(new byte[0]));
        try {
            String query = "SELECT image_data FROM real_estate WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                byte[] imageData = rs.getBytes("image_data");
                img = new Image(new ByteArrayInputStream(imageData));
                // Create an Image object from the image data
                return img;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return img;
    }
    public Set<RealEstate> readAllNbrClick() {
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
                int nbrclick = rs.getInt("nbrclick");
                String name = rs.getString("name");
                //Fetch user participation information from another table if needed
                Map<User, Double> userParticipation = fetchUserParticipation(id);
                RealEstate re = new RealEstate(id,name, emplacement, ROI, valeur, nbChambres, superficie, nbrclick);
                realEstateSet.add(re);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return realEstateSet;
    }

    public List<RealEstate> rechercherEvent(String evnt) throws SQLException {
        String requeteSQL = "SELECT * FROM real_estate WHERE name LIKE ? OR emplacement LIKE ? ";



        // Ajoutez le joker % pour correspondre à n'importe quelle partie de la chaîne
        evnt= "%" + evnt + "%";


        List<RealEstate> resultatsRecherche = new ArrayList<>();
        try (PreparedStatement statement = cnx.prepareStatement(requeteSQL)) {

            statement.setString(1, evnt);
            statement.setString(2, evnt);



            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String emplacement = rs.getString("emplacement");
                    float ROI = rs.getFloat("ROI");
                    double valeur = rs.getDouble("valeur");
                    int nbChambres = rs.getInt("nbChambres");
                    float superficie = rs.getFloat("superficie");
                    int nbrclick = rs.getInt("nbrclick");
                    String name = rs.getString("name");
                    //Fetch user participation information from another table if needed
                    Map<User, Double> userParticipation = fetchUserParticipation(id);
                    RealEstate re = new RealEstate(id,name, emplacement, ROI, valeur, nbChambres, superficie, nbrclick);
                    resultatsRecherche.add(re);
                }
            }
        }

        return resultatsRecherche;
    }
    public List<RealEstate> sortROI(Set<RealEstate> realEstates) {
        // Convert the set to a list
        List<RealEstate> realEstateList = new ArrayList<>(realEstates);

        // Sort the list based on ROI in descending order
        Collections.sort(realEstateList, Comparator.comparingDouble(RealEstate::getROI).reversed());

        return realEstateList;
    }

}
