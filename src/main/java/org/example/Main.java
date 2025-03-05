package org.example;

import org.example.AirportDAO;
import org.example.AircraftDAO;
import org.example.PassengerDAO;

import org.example.Class.Passenger;
import org.example.Class.Airport;
import org.example.Class.Aircraft;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main extends JFrame {
    private Connection connection;

    // DAO objects
    private AirportDAO airportDAO;
    private AircraftDAO aircraftDAO;
    private PassengerDAO passengerDAO;

    // Components for input forms
    private JTextField airportNameField;
    private JTextField aircraftNameField;
    private JTextField aircraftCapacityField;
    private JTextField passengerNameField;
    private JTextField passengerSeatNumberField; // Changed from luggage weight to seat number

    // Tables for displaying data
    private JTable airportTable;
    private JTable aircraftTable;
    private JTable passengerTable;

    public Main() {
        setTitle("Airport Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/airportdb";
            String user = "root";
            String password = "12345";
            connection = DriverManager.getConnection(url, user, password);

            airportDAO = new AirportDAO(connection);
            aircraftDAO = new AircraftDAO(connection);
            passengerDAO = new PassengerDAO(connection);

            initializeUI();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        // Create panels for input forms and tables
        JPanel inputPanel = new JPanel(new GridLayout(1, 3));
        JPanel tablePanel = new JPanel(new GridLayout(1, 3));

        // Airport Input Panel
        JPanel airportInputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel airportNameLabel = new JLabel("Airport Name:");
        airportNameField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        airportInputPanel.add(airportNameLabel, gbc);
        gbc.gridx = 1;
        airportInputPanel.add(airportNameField, gbc);

        // Aircraft Input Panel
        JPanel aircraftInputPanel = new JPanel(new GridBagLayout());
        JLabel aircraftNameLabel = new JLabel("Aircraft Name:");
        aircraftNameField = new JTextField(20);
        JLabel aircraftCapacityLabel = new JLabel("Aircraft Capacity:");
        aircraftCapacityField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        aircraftInputPanel.add(aircraftNameLabel, gbc);
        gbc.gridx = 1;
        aircraftInputPanel.add(aircraftNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        aircraftInputPanel.add(aircraftCapacityLabel, gbc);
        gbc.gridx = 1;
        aircraftInputPanel.add(aircraftCapacityField, gbc);

        // Passenger Input Panel
        JPanel passengerInputPanel = new JPanel(new GridBagLayout());
        JLabel passengerNameLabel = new JLabel("Passenger Name:");
        passengerNameField = new JTextField(20);
        JLabel passengerSeatNumberLabel = new JLabel("Seat Number:"); // Changed label text
        passengerSeatNumberField = new JTextField(20); // Changed field variable
        gbc.gridx = 0;
        gbc.gridy = 0;
        passengerInputPanel.add(passengerNameLabel, gbc);
        gbc.gridx = 1;
        passengerInputPanel.add(passengerNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        passengerInputPanel.add(passengerSeatNumberLabel, gbc);
        gbc.gridx = 1;
        passengerInputPanel.add(passengerSeatNumberField, gbc);

        // Add buttons for adding entities
        JButton addAirportButton = new JButton("Add Airport");
        JButton addAircraftButton = new JButton("Add Aircraft");
        JButton addPassengerButton = new JButton("Add Passenger");

        addAirportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = airportNameField.getText();
                if (!name.isEmpty()) {
                    airportDAO.add(new Airport(name));
                    refreshTables();
                    airportNameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(Main.this, "Please enter an airport name.");
                }
            }
        });

        addAircraftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = aircraftNameField.getText();
                String capacityStr = aircraftCapacityField.getText();
                if (!name.isEmpty() && !capacityStr.isEmpty()) {
                    try {
                        int capacity = Integer.parseInt(capacityStr);
                        aircraftDAO.add(new Aircraft(name, capacity));
                        refreshTables();
                        aircraftNameField.setText("");
                        aircraftCapacityField.setText("");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Main.this, "Please enter a valid capacity.");
                    }
                } else {
                    JOptionPane.showMessageDialog(Main.this, "Please fill in all fields.");
                }
            }
        });

        addPassengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = passengerNameField.getText();
                String seatNumberStr = passengerSeatNumberField.getText(); // Changed field reference
                if (!name.isEmpty() && !seatNumberStr.isEmpty()) {
                    try {
                        int seatNumber = Integer.parseInt(seatNumberStr);
                        passengerDAO.add(new Passenger(name, seatNumber));
                        refreshTables();
                        passengerNameField.setText("");
                        passengerSeatNumberField.setText(""); // Clear the seat number field
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Main.this, "Please enter a valid seat number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(Main.this, "Please fill in all fields.");
                }
            }
        });

        // Add delete buttons for each entity
        JButton deleteAirportButton = new JButton("Delete Airport");
        JButton deleteAircraftButton = new JButton("Delete Aircraft");
        JButton deletePassengerButton = new JButton("Delete Passenger");

        deleteAirportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = airportTable.getSelectedRow();
                if (selectedRow != -1) {
                    Object idObj = airportTable.getValueAt(selectedRow, 0); // Retrieve the ID as an Object
                    if (idObj instanceof Integer) {
                        int id = (Integer) idObj;
                        airportDAO.delete(id);
                        refreshTables();
                    } else if (idObj instanceof String) {
                        try {
                            int id = Integer.parseInt((String) idObj);
                            airportDAO.delete(id);
                            refreshTables();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(Main.this, "Invalid ID format.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(Main.this, "Unexpected data type for ID.");
                    }
                } else {
                    JOptionPane.showMessageDialog(Main.this, "Please select an airport to delete.");
                }
            }
        });

        deleteAircraftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = aircraftTable.getSelectedRow();
                if (selectedRow != -1) {
                    Object idObj = aircraftTable.getValueAt(selectedRow, 0); // Retrieve the ID as an Object
                    if (idObj instanceof Integer) {
                        int id = (Integer) idObj;
                        aircraftDAO.delete(id);
                        refreshTables();
                    } else if (idObj instanceof String) {
                        try {
                            int id = Integer.parseInt((String) idObj);
                            aircraftDAO.delete(id);
                            refreshTables();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(Main.this, "Invalid ID format.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(Main.this, "Unexpected data type for ID.");
                    }
                } else {
                    JOptionPane.showMessageDialog(Main.this, "Please select an aircraft to delete.");
                }
            }
        });

        deletePassengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = passengerTable.getSelectedRow();
                if (selectedRow != -1) {
                    Object idObj = passengerTable.getValueAt(selectedRow, 0); // Retrieve the ID as an Object
                    if (idObj instanceof Integer) {
                        int id = (Integer) idObj;
                        passengerDAO.delete(id);
                        refreshTables();
                    } else if (idObj instanceof String) {
                        try {
                            int id = Integer.parseInt((String) idObj);
                            passengerDAO.delete(id);
                            refreshTables();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(Main.this, "Invalid ID format.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(Main.this, "Unexpected data type for ID.");
                    }
                } else {
                    JOptionPane.showMessageDialog(Main.this, "Please select a passenger to delete.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addAirportButton);
        buttonPanel.add(addAircraftButton);
        buttonPanel.add(addPassengerButton);
        buttonPanel.add(deleteAirportButton);
        buttonPanel.add(deleteAircraftButton);
        buttonPanel.add(deletePassengerButton);

        // Add input panels to main input panel
        inputPanel.add(airportInputPanel);
        inputPanel.add(aircraftInputPanel);
        inputPanel.add(passengerInputPanel);

        // Add tables for displaying data
        airportTable = createTable(airportDAO.getAll());
        aircraftTable = createTable(aircraftDAO.getAll());
        passengerTable = createTable(passengerDAO.getAll());

        JScrollPane airportScrollPane = new JScrollPane(airportTable);
        JScrollPane aircraftScrollPane = new JScrollPane(aircraftTable);
        JScrollPane passengerScrollPane = new JScrollPane(passengerTable);

        tablePanel.add(airportScrollPane);
        tablePanel.add(aircraftScrollPane);
        tablePanel.add(passengerScrollPane);

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JTable createTable(List<?> items) {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        if (!items.isEmpty()) {
            // Get the class of the first item to determine the columns
            Class<?> clazz = items.get(0).getClass();
            java.lang.reflect.Field[] fields = clazz.getDeclaredFields();

            // Add columns based on the fields of the class
            for (java.lang.reflect.Field field : fields) {
                model.addColumn(field.getName());
            }

            // Add rows based on the items
            for (Object item : items) {
                Object[] row = new Object[fields.length];
                int index = 0;
                for (java.lang.reflect.Field field : fields) {
                    field.setAccessible(true);
                    try {
                        row[index++] = field.get(item);
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
                model.addRow(row);
            }
        }

        return table;
    }

    private void refreshTables() {
        airportTable.setModel(createTable(airportDAO.getAll()).getModel());
        aircraftTable.setModel(createTable(aircraftDAO.getAll()).getModel());
        passengerTable.setModel(createTable(passengerDAO.getAll()).getModel());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}