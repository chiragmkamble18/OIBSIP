package com.reservation;

import javax.swing.*;
import java.awt.*;

public class MainMenuUI extends JFrame {
    public MainMenuUI() {
        setTitle("Reservation System - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton bookBtn = new JButton("Book Ticket");
        JButton cancelBtn = new JButton("Cancel Ticket");
        JButton logoutBtn = new JButton("Logout");

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(bookBtn, gbc);
        gbc.gridy = 1;
        panel.add(cancelBtn, gbc);
        gbc.gridy = 2;
        panel.add(logoutBtn, gbc);

        add(panel);

        bookBtn.addActionListener(e -> {
            new ReservationUI().setVisible(true);
            dispose();
        });

        cancelBtn.addActionListener(e -> {
            new CancellationUI().setVisible(true);
            dispose();
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginUI().setVisible(true);
        });
    }
}