package Service;

import Entity.Offre;
import utile.dataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreService implements IOffre<Offre> {

    private Connection connection;

    public OffreService() {
        connection = dataSource.getInstance().getCnx();
    }


    public boolean addOffreToCredit(int creditId, double montant, double interet, int userId) {
        String insertOffreQuery = "INSERT INTO Offre (montant, interet, user_id, credit_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertOffreQuery)) {
            insertStatement.setDouble(1, montant);
            insertStatement.setDouble(2, interet);
            insertStatement.setInt(3, userId);
            insertStatement.setInt(4, creditId);

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Credit added to Offre successfully!");
            } else {
                System.out.println("Failed to add credit to Offre!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding credit to Offre: " + e.getMessage());
        }
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


}
