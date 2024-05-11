package com.example.finfolio.Service;

import com.example.finfolio.Entite.Credit;
import com.example.finfolio.Entite.User;
import com.example.finfolio.util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditService implements ICredit<Credit> {

    private Connection connection;

    public CreditService() {
        connection = DataSource.getInstance().getCnx();
    }
    private Statement ste;
    private PreparedStatement pst;
    private Connection cox;

    public void add(Credit credit) {
        // Check if the credit ID already exists
        if (creditExists(credit.getId())) {
            System.out.println("Credit with ID " + credit.getId() + " already exists.");
            return;
        }

        String query = "INSERT INTO credit (id, montant, interetMax, interetMin, dateD, dateF, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, credit.getId()); // Set the ID from the Credit object
            preparedStatement.setDouble(2, credit.getMontant());
            preparedStatement.setDouble(3, credit.getInteretMax());
            preparedStatement.setDouble(4, credit.getInteretMin());
            preparedStatement.setString(5, credit.getDateD());
            preparedStatement.setString(6, credit.getDateF());
            preparedStatement.setInt(7, credit.getUser().getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Credit added successfully!");
            } else {
                System.out.println("Failed to add credit!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding credit: " + e.getMessage());
        }
    }

    public void addi(Credit credit) {
        String query = "INSERT INTO credit (montant, interetMax, interetMin, dateD, dateF, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, credit.getMontant());
            preparedStatement.setDouble(2, credit.getInteretMax());
            preparedStatement.setDouble(3, credit.getInteretMin());
            preparedStatement.setString(4, credit.getDateD());
            preparedStatement.setString(5, credit.getDateF());
            preparedStatement.setInt(6, credit.getUser().getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        credit.setId(generatedId);
                        System.out.println("Credit added successfully with ID: " + generatedId);
                    }
                }
            } else {
                System.out.println("Failed to add credit!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding credit: " + e.getMessage());
        }
    }


    private boolean creditExists(int creditId) {
        String query = "SELECT COUNT(*) FROM credit WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, creditId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // Return true if the credit exists
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking if credit exists: " + e.getMessage());
        }
        return false; // Return false if an error occurs or if the credit does not exist
    }

    @Override
    public void addCredit(Credit credit) {

    }

    @Override
    public void deleteCredit(Credit credit) {

    }

//    @Override
//    public void deleteCredit(Credit credit) {
//        // Implement deleteCredit method if needed
//    }

    public boolean deleteCredit(int creditId) {
        String query = "DELETE FROM credit WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, creditId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Credit deleted successfully!");
                return true; // Deletion successful
            } else {
                System.out.println("Credit with ID " + creditId + " not found.");
                return false; // No credit found with the specified ID
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting credit: " + e.getMessage());
        }
    }



    public void modifierCredit(double montant, double interetMax, double interetMin, String dateD, String dateF, int id) {
        String query = "UPDATE credit SET montant = ?, interetMax = ?, interetMin = ?, dateD = ?, dateF = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, montant);
            preparedStatement.setDouble(2, interetMax);
            preparedStatement.setDouble(3, interetMin);
            preparedStatement.setString(4, dateD);
            preparedStatement.setString(5, dateF);
            preparedStatement.setInt(6, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Credit modified successfully!");
            } else {
                System.out.println("Failed to modify credit! No credit found with the specified ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error modifying credit: " + e.getMessage());
        }
    }

    public List<Credit> readAllCredits() {
        String query = "SELECT credit.*, user.nom FROM credit JOIN user ON credit.user_id = user.id";
        List<Credit> creditList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double montant = resultSet.getDouble("montant");
                double interetMax = resultSet.getDouble("interetMax");
                double interetMin = resultSet.getDouble("interetMin");

                // Handle dateD and dateF as strings
                String dateD = resultSet.getString("dateD");
                String dateF = resultSet.getString("dateF");

                // Retrieve the user name from the result set
                String userName = resultSet.getString("nom");

                // Check if userName is null and set it to a default value if it is
                if (userName == null) {
                    userName = "Unknown";
                }

                // Assuming you have a method to retrieve a User object by ID from the database
                // Replace this with your actual implementation
                UserService userService = new UserService();
                User user = userService.getUserByid(resultSet.getInt("user_id"));

                // Create the Credit object with the correct parameters
                Credit credit = new Credit(id, montant, interetMax, interetMin, dateD, dateF, user, userName);
                creditList.add(credit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return creditList;
    }


    public  List<Credit> getCreditsByUserId(int userId) {
        String query = "SELECT credit.*, user.nom FROM credit JOIN user ON credit.user_id = user.id WHERE credit.user_id = ?";
        List<Credit> creditList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    double montant = resultSet.getDouble("montant");
                    double interetMax = resultSet.getDouble("interetMax");
                    double interetMin = resultSet.getDouble("interetMin");

                    // Handle dateD and dateF as strings
                    String dateD = resultSet.getString("dateD");
                    String dateF = resultSet.getString("dateF");

                    // Retrieve the user name from the result set
                    String userName = resultSet.getString("nom");

                    // Check if userName is null and set it to a default value if it is
                    if (userName == null) {
                        userName = "Unknown";
                    }

                    // Assuming you have a method to retrieve a User object by ID from the database
                    // Replace this with your actual implementation
                    UserService userService = new UserService();
                    User user = userService.getUserByid(resultSet.getInt("user_id"));

                    // Create the Credit object with the correct parameters
                    Credit credit = new Credit(id, montant, interetMax, interetMin, dateD, dateF, user, userName);
                    creditList.add(credit);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching credits for user ID " + userId + ": " + e.getMessage());
        }

        return creditList;
    }

//    public Credit getCreditById(int creditId) {
//        String query = "SELECT * FROM Credit WHERE id = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setInt(1, creditId);
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    double montant = resultSet.getDouble("montant");
//                    double interetMax = resultSet.getDouble("interetMax");
//                    double interetMin = resultSet.getDouble("interetMin");
//                    String dateD = resultSet.getString("dateD");
//                    String dateF = resultSet.getString("dateF");
//                    int userId = resultSet.getInt("user_id");
//
//                    // Assuming you have a method to retrieve a User object by ID from the database
//                    UserService userService = new UserService();
//                    User user = userService.getUserByid(userId);
//
//                    return new Credit(creditId, montant, interetMax, interetMin, dateD, dateF, user);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error fetching credit details for credit ID " + creditId + ": " + e.getMessage());
//        }

//        return null; // Return null if no credit found with the specified ID
//    }


//    public int getReceiverUserIdByCreditId(String creditId) {
//        String query = "SELECT user_id FROM credit WHERE id = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setString(1, creditId);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    return resultSet.getInt("user_id");
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error fetching receiver user ID for credit ID " + creditId + ": " + e.getMessage());
//        }
//        return -1; // Return -1 if credit ID is not found or if an error occurs
//    }



    // Existing methods...

}




