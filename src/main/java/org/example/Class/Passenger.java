package org.example.Class;

public class Passenger {
    private int id;
    private String name;
    private int seatNumber;

    public Passenger(String name, int seatNumber) { // Original constructor with 2 arguments
        this.name = name;
        this.seatNumber = seatNumber;
    }

    public Passenger(String name, int seatNumber, int id) { // New constructor with 3 arguments
        this.name = name;
        this.seatNumber = seatNumber;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    @Override
    public String toString() {
        return "Passenger{id=" + id + ", name='" + name + "', seatNumber=" + seatNumber + "}";
    }
}

