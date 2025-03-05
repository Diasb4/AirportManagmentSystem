package org.example;
public interface EntityDAO<T> {
    void add(T entity);
    void update(T entity);
    void delete(int id);
    void displayAll();
}