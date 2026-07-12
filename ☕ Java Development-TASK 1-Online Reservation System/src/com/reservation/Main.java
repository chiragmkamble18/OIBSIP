package com.reservation;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseManager.initializeDatabase();   // create tables & sample data
            new LoginUI().setVisible(true);
        });
    }
}