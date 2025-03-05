package org.example.Class;

public class Aircraft {
    private int id;
    private String name;
    private int capacity;

    public Aircraft(String name, int capacity) { // Original constructor with 2 arguments
        this.name = name;
        this.capacity = capacity;
    }

    public Aircraft(String name, int capacity, int id) { // New constructor with 3 arguments
        this.name = name;
        this.capacity = capacity;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Aircraft{id=" + id + ", name='" + name + "', capacity=" + capacity + "}";
    }
}