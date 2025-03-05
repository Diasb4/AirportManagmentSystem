package org.example;
import org.example.Class.Airport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportDAO {
    private Connection connection;

    public AirportDAO(Connection connection) {
        this.connection = connection;
    }

    // Add method
    public void add(Airport airport) {
        String sql = "INSERT INTO  (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, airport.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Airport> getAll() {
        List<Airport> airports = new ArrayList<>();
        String sql = "SELECT * FROM airportdb.airport";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                airports.add(new Airport(name, id)); // Use the constructor with 2 arguments
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airports;
    }

    // DisplayAll method (optional)
    public void displayAll() {
        List<Airport> airports = getAll();
        for (Airport airport : airports) {
            System.out.println(airport.getName());
        }
    }
    public void delete(int id) {
        String sql = "DELETE FROM airportdb.airport WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}