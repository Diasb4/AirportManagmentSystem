package org.example;
import org.example.Class.Passenger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAO {
    private Connection connection;

    public PassengerDAO(Connection connection) {
        this.connection = connection;
    }

    public void add(Passenger passenger) {
        String sql = "INSERT INTO airportdb.passenger (name, seat_number) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, passenger.getName());
            statement.setInt(2, passenger.getSeatNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Passenger> getAll() {
        List<Passenger> passengers = new ArrayList<>();
        String sql = "SELECT * FROM airportdb.passenger";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int seatNumber = resultSet.getInt("seat_number");
                passengers.add(new Passenger(name, seatNumber, id)); // Use the constructor with 3 arguments
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengers;
    }

    public void delete(int id) {
        String sql = "DELETE FROM airportdb.passenger WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}