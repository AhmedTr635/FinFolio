package com.example.finfolio.Service;

import com.example.finfolio.Entite.Offre;
import com.example.finfolio.Entite.User;
import com.example.finfolio.util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreService implements IOffre<Offre> {

    private Connection connection;

    public OffreService() {
        connection = DataSource.getInstance().getCnx();
    }

    // Updated method to accept User object
    public boolean addOffreToCredit(int creditId, double montant, double interet, User user) {
        String insertOffreQuery = "INSERT INTO Offre (montant, interet, user_id, credit_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertOffreQuery)) {
            insertStatement.setDouble(1, montant);
            insertStatement.setDouble(2, interet);
            insertStatement.setInt(3, user.getId()); // Set user ID
            insertStatement.setInt(4, creditId);

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Credit added to Offre successfully!");
                return true;
            } else {
                System.out.println("Failed to add credit to Offre!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding credit to Offre: " + e.getMessage());
        }
        return false;
    }

//    @Override
//    public boolean addOffreToCredit(int creditId, double montant, double interet, int userId) {
//        return false;
//    }

    @Override
    public boolean addOffreToCredit(int creditId, double montant, double interet, int userId) {
        return false;
    }

    @Override
    public List<Offre> getAllOffres() {
        String query = "SELECT * FROM Offre";
        List<Offre> offreList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double montant = resultSet.getDouble("montant");
                double interet = resultSet.getDouble("interet");
                int user_id = resultSet.getInt("user_id");
                int credit_id = resultSet.getInt("credit_id");

                Offre offre = new Offre(id, montant, interet, user_id, credit_id);
                offreList.add(offre);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching Offres: " + e.getMessage());
        }

        return offreList;
    }

    @Override
    public boolean deleteOffre(int OffreId) {
        String query = "DELETE FROM Offre WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, OffreId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Offre deleted successfully!");
                return true;
            } else {
                System.out.println("Offre not found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Offre: " + e.getMessage());
        }
        return false;

    }


    public boolean updateOffre(Offre offre) {
        String updateQuery = "UPDATE Offre SET montant = ?, interet = ? WHERE id = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setDouble(1, offre.getMontant());
            updateStatement.setDouble(2, offre.getInteret());
            updateStatement.setInt(3, offre.getId());

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Offre updated successfully!");
                return true; // Return true if the update was successful
            } else {
                System.out.println("Failed to update Offre. Offre not found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Offre: " + e.getMessage());
        }

        return false; // Return false if the update failed
    }

    @Override
    public void updateOffre(int id, Offre offre) {
        // Implement updateOffre method if needed
    }

    @Override
    public void updateOffre(int id, double montant, double interet) {
        String updateQuery = "UPDATE Offre SET montant = ?, interet = ? WHERE id = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setDouble(1, montant);
            updateStatement.setDouble(2, interet);
            updateStatement.setInt(3, id);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Offre updated successfully!");
            } else {
                System.out.println("Failed to update offre. Offre not found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating offre: " + e.getMessage());
        }
    }


    public List<Offre> getOffersByCreditId(int creditId) {
        String query = "SELECT * FROM Offre WHERE credit_id = ?";
        List<Offre> offreList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, creditId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    double montant = resultSet.getDouble("montant");
                    double interet = resultSet.getDouble("interet");
                    int userId = resultSet.getInt("user_id");

                    Offre offre = new Offre(id, montant, interet, userId, creditId);
                    offreList.add(offre);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching offers for credit ID " + creditId + ": " + e.getMessage());
        }

        return offreList;
    }

    public List<Offre> getOffersByUserId(int userId) {
        String query = "SELECT * FROM Offre WHERE user_id = ?";
        List<Offre> offreList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    double montant = resultSet.getDouble("montant");
                    double interet = resultSet.getDouble("interet");
                    int user_id = resultSet.getInt("user_id");
                    int credit_id = resultSet.getInt("credit_id");

                    Offre offre = new Offre(id, montant, interet, user_id, credit_id);
                    offreList.add(offre);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching Offres for user ID " + userId + ": " + e.getMessage());
        }

        return offreList;
    }

    // Method to get offers sent to credits belonging to a specific user
    public List<Offre> getOffersByCreditOwnerId(int ownerId) {
        List<Offre> offreList = new ArrayList<>();
        String query = "SELECT o.id, o.montant, o.interet, o.user_id, o.credit_id, u.nom " +
                "FROM Offre o " +
                "INNER JOIN credit c ON o.credit_id = c.id " +
                "LEFT JOIN user u ON o.user_id = u.id " + // Use LEFT JOIN to handle null values
                "WHERE c.user_id = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ownerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    double montant = resultSet.getDouble("montant");
                    double interet = resultSet.getDouble("interet");
                    int userId = resultSet.getInt("user_id");
                    int creditId = resultSet.getInt("credit_id");
                    String userName = resultSet.getString("nom"); // Fetch sender's name

                    Offre offre = new Offre(id, montant, interet, userId, creditId, userName); // Include sender's name in Offre object
                    offreList.add(offre);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return offreList;
    }
    public double getSoldeForUser(int userId) {
        String query = "SELECT solde FROM user WHERE id = ?";
        double solde = 0; // Initialize solde to default value

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    solde = resultSet.getDouble("solde");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching solde for user ID " + userId + ": " + e.getMessage());
        }

        return solde;
    }
    public double calculateAndDisplayRatingForUsers(int us) throws SQLException {
        UserService userService = new UserService();
        CreditService creditService = new CreditService();

        // Get all users from the database
        List<User> userList = userService.readAll();

        // Iterate through each user
        for (User user : userList) {
            // Retrieve solde for the user
            double solde = getSoldeForUser(user.getId());

            // Retrieve ROI from investments for the user
            double investmentROI = getROIFromInvestissement(user.getId());

            // Calculate rating based on solde and ROI
            double calculatedRating = calculateRating(solde, investmentROI);

            // Display the rating for the user (you can choose how to display it, for example, in a label or console)
            System.out.println("Rating for user " + user.getId() + ": " + calculatedRating);
        }
        return 0;
    }

    // Method to get solde for a user from the database


    // Method to get ROI from investments for a user from the database
    private double getROIFromInvestissement(int userId) {
        String query = "SELECT roi FROM investissement WHERE user_id = ?";
        double roi = 0; // Initialize ROI to default value

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    roi = resultSet.getDouble("roi");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential errors here
        }

        return roi;
    }


    // Method to calculate the rating based on solde and ROI
    private double calculateRating(double solde, double investmentROI) {
        // Your implementation to calculate the rating
        // You can use the method you provided earlier: calculateRating(solde, investmentROI)
        return (solde + investmentROI) / 2.0;
    }

    }



