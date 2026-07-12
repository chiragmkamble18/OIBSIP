package com.reservation;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:reservation.db";
    private static Connection connection;

    // Explicitly load the SQLite driver
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found. Ensure the JAR is in classpath.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    public static void initializeDatabase() {
        String createUsers = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL)";
        String createReservations = "CREATE TABLE IF NOT EXISTS reservations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pnr TEXT UNIQUE NOT NULL, " +
                "passenger_name TEXT NOT NULL, " +
                "train_number INTEGER NOT NULL, " +
                "train_name TEXT NOT NULL, " +
                "class_type TEXT NOT NULL, " +
                "journey_date TEXT NOT NULL, " +
                "source_station TEXT NOT NULL, " +
                "destination_station TEXT NOT NULL)";
        String createTrains = "CREATE TABLE IF NOT EXISTS trains (" +
                "train_number INTEGER PRIMARY KEY, " +
                "train_name TEXT NOT NULL)";
        String insertUsers = "INSERT OR IGNORE INTO users (username, password) VALUES ('admin', 'admin')";
        String insertTrains = "INSERT OR IGNORE INTO trains (train_number, train_name) VALUES " +
                "(12001, 'Rajdhani Express'), (12002, 'Shatabdi Express'), " +
                "(12627, 'Karnataka Express'), (12951, 'Mumbai Rajdhani')";

        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(createUsers);
            stmt.execute(createReservations);
            stmt.execute(createTrains);
            stmt.execute(insertUsers);
            stmt.execute(insertTrains);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String saveReservation(Reservation res) {
        String pnr = "PNR" + System.currentTimeMillis() + (int)(Math.random()*1000);
        String query = "INSERT INTO reservations (pnr, passenger_name, train_number, train_name, " +
                "class_type, journey_date, source_station, destination_station) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
            pstmt.setString(1, pnr);
            pstmt.setString(2, res.getPassengerName());
            pstmt.setInt(3, res.getTrainNumber());
            pstmt.setString(4, res.getTrainName());
            pstmt.setString(5, res.getClassType());
            pstmt.setString(6, res.getJourneyDate());
            pstmt.setString(7, res.getSource());
            pstmt.setString(8, res.getDestination());
            pstmt.executeUpdate();
            return pnr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTrainName(int trainNumber) {
        String query = "SELECT train_name FROM trains WHERE train_number = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
            pstmt.setInt(1, trainNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("train_name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Reservation getReservationByPNR(String pnr) {
        String query = "SELECT * FROM reservations WHERE pnr = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
            pstmt.setString(1, pnr);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Reservation(
                        rs.getString("pnr"),
                        rs.getString("passenger_name"),
                        rs.getInt("train_number"),
                        rs.getString("train_name"),
                        rs.getString("class_type"),
                        rs.getString("journey_date"),
                        rs.getString("source_station"),
                        rs.getString("destination_station")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean cancelReservation(String pnr) {
        String query = "DELETE FROM reservations WHERE pnr = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
            pstmt.setString(1, pnr);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}