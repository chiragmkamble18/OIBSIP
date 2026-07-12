package com.reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginUI() {
        setTitle("Online Reservation System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Buttons
        JButton loginBtn = new JButton("Login");
        JButton exitBtn = new JButton("Exit");
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(loginBtn, gbc);
        gbc.gridx = 1;
        panel.add(exitBtn, gbc);

        add(panel);

        // Event handlers
        loginBtn.addActionListener(e -> authenticate());
        exitBtn.addActionListener(e -> System.exit(0));

        // Enter key triggers login
        getRootPane().setDefaultButton(loginBtn);
    }

    private void authenticate() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (DatabaseManager.validateLogin(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // close login window
            new MainMenuUI().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.",
                    "Access Denied", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseManager.initializeDatabase();
            new LoginUI().setVisible(true);
        });
    }
}