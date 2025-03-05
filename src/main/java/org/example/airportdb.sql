CREATE DATABASE Airportdb;
USE Airportdb;

CREATE TABLE airports (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100)
);

CREATE TABLE aircraft (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100)
);

CREATE TABLE passengers (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100),
                            seat_number INT
);