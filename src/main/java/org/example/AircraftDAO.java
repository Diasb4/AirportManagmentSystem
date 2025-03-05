package org.example;

import org.example.Class.Aircraft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AircraftDAO {
    private Connection connection;

    public AircraftDAO(Connection connection) {
        this.connection = connection;
    }

    // Add method
    public void add(Aircraft aircraft) {
        String sql = "INSERT INTO airportdb.aircraft (name, capacity) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, aircraft.getName());
            statement.setInt(2, aircraft.getCapacity());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Aircraft> getAll() {
        List<Aircraft> aircrafts = new ArrayList<>();
        String sql = "SELECT * FROM airportdb.aircraft";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int capacity = resultSet.getInt("capacity");
                aircrafts.add(new Aircraft(name, capacity, id)); // Use the constructor with 3 arguments
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aircrafts;
    }

    // DisplayAll method (optional)
    public void displayAll() {
        List<Aircraft> aircrafts = getAll();
        for (Aircraft aircraft : aircrafts) {
            System.out.println(aircraft.getName() + " - Capacity: " + aircraft.getCapacity());
        }
    }
    public void delete(int id) {
        String sql = "DELETE FROM airportdb.aircraft WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}