package org.example.Class;

public class Airport {
    private int id;
    private String name;

    public Airport(String name) { // Original constructor with 1 argument
        this.name = name;
    }

    public Airport(String name, int id) { // New constructor with 2 arguments
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Airport{id=" + id + ", name='" + name + "'}";
    }
}