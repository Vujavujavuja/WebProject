package touristapp.services;

import touristapp.util.DatabaseUtil;
import touristapp.enteties.Destination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DestinationService {

    public List<Destination> getAllDestinations() {
        List<Destination> destinations = new ArrayList<>();
        String query = "SELECT * FROM destinations";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Destination destination = new Destination(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("country"),
                        rs.getInt("rating")
                );
                destinations.add(destination);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return destinations;
    }

    public void addDestination(Destination destination) {
        String query = "INSERT INTO destinations (name, description, country, rating) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, destination.getName());
            stmt.setString(2, destination.getDescription());
            stmt.setString(3, destination.getCountry());
            stmt.setInt(4, destination.getRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeDestination(int id) {
        String query = "DELETE FROM destinations WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Destination getDestinationById(int id) {
        String query = "SELECT * FROM destinations WHERE id = ?";
        Destination destination = null;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                destination = new Destination(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("country"),
                        rs.getInt("rating")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return destination;
    }

    public void editDestination(int id, Destination destination) {
        String query = "UPDATE destinations SET name = ?, description = ?, country = ?, rating = ? WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, destination.getName());
            stmt.setString(2, destination.getDescription());
            stmt.setString(3, destination.getCountry());
            stmt.setInt(4, destination.getRating());
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Destination getDestinationByName(String name) {
        String query = "SELECT * FROM destinations WHERE name = ?";
        Destination destination = null;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                destination = new Destination(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("country"),
                        rs.getInt("rating")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return destination;
    }

    public boolean doesDestinationExist(int id) {
        String query = "SELECT * FROM destinations WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
