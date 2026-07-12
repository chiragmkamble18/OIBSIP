package com.reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class ReservationUI extends JFrame {
    private JTextField passengerNameField, trainNumberField, trainNameField, dateField, sourceField, destField;
    private JComboBox<String> classCombo;

    public ReservationUI() {
        setTitle("Book Ticket");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Passenger Name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Passenger Name:"), gbc);
        gbc.gridx = 1;
        passengerNameField = new JTextField(20);
        panel.add(passengerNameField, gbc);

        // Train Number
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Train Number:"), gbc);
        gbc.gridx = 1;
        trainNumberField = new JTextField(20);
        panel.add(trainNumberField, gbc);

        // Train Name (auto-populated, non-editable)
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Train Name:"), gbc);
        gbc.gridx = 1;
        trainNameField = new JTextField(20);
        trainNameField.setEditable(false);
        panel.add(trainNameField, gbc);

        // Class Type
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Class Type:"), gbc);
        gbc.gridx = 1;
        String[] classes = {"Sleeper", "AC 3-Tier", "AC 2-Tier", "AC First Class"};
        classCombo = new JComboBox<>(classes);
        panel.add(classCombo, gbc);

        // Journey Date
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Journey Date (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        dateField = new JTextField(20);
        panel.add(dateField, gbc);

        // Source
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Source Station:"), gbc);
        gbc.gridx = 1;
        sourceField = new JTextField(20);
        panel.add(sourceField, gbc);

        // Destination
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Destination Station:"), gbc);
        gbc.gridx = 1;
        destField = new JTextField(20);
        panel.add(destField, gbc);

        // Buttons
        JButton bookBtn = new JButton("Book");
        JButton backBtn = new JButton("Back");
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnPanel.add(bookBtn);
        btnPanel.add(backBtn);
        panel.add(btnPanel, gbc);

        add(panel);

        // Auto-populate train name when train number is entered (on focus lost)
        trainNumberField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                populateTrainName();
            }
        });

        // Book action
        bookBtn.addActionListener(e -> bookTicket());

        // Back to menu
        backBtn.addActionListener(e -> {
            dispose();
            new MainMenuUI().setVisible(true);
        });
    }

    private void populateTrainName() {
        String numText = trainNumberField.getText().trim();
        if (numText.isEmpty()) {
            trainNameField.setText("");
            return;
        }
        try {
            int trainNum = Integer.parseInt(numText);
            String name = DatabaseManager.getTrainName(trainNum);
            if (name != null) {
                trainNameField.setText(name);
            } else {
                trainNameField.setText("Unknown");
                JOptionPane.showMessageDialog(this, "Train number not found.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            trainNameField.setText("");
            JOptionPane.showMessageDialog(this, "Train number must be numeric.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bookTicket() {
        // Validation
        String name = passengerNameField.getText().trim();
        String trainNumStr = trainNumberField.getText().trim();
        String trainName = trainNameField.getText().trim();
        String classType = (String) classCombo.getSelectedItem();
        String date = dateField.getText().trim();
        String source = sourceField.getText().trim();
        String dest = destField.getText().trim();

        if (name.isEmpty() || trainNumStr.isEmpty() || date.isEmpty() || source.isEmpty() || dest.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int trainNum;
        try {
            trainNum = Integer.parseInt(trainNumStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Train number must be numeric.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate date format
        if (!isValidDate(date)) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if train name is known
        if (trainName.isEmpty() || trainName.equals("Unknown")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid train number.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create Reservation object
        Reservation res = new Reservation();
        res.setPassengerName(name);
        res.setTrainNumber(trainNum);
        res.setTrainName(trainName);
        res.setClassType(classType);
        res.setJourneyDate(date);
        res.setSource(source);
        res.setDestination(dest);

        // Save to DB
        String pnr = DatabaseManager.saveReservation(res);
        if (pnr != null) {
            // Show confirmation
            String details = String.format(
                "Booking Confirmed!\n\nPNR: %s\nPassenger: %s\nTrain: %s (%d)\nClass: %s\nDate: %s\nFrom: %s\nTo: %s",
                pnr, name, trainName, trainNum, classType, date, source, dest
            );
            JOptionPane.showMessageDialog(this, details, "Booking Success", JOptionPane.INFORMATION_MESSAGE);
            // Clear fields for next booking
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Booking failed. Please try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (java.text.ParseException e) {
            return false;
        }
    }

    private void clearFields() {
        passengerNameField.setText("");
        trainNumberField.setText("");
        trainNameField.setText("");
        dateField.setText("");
        sourceField.setText("");
        destField.setText("");
        classCombo.setSelectedIndex(0);
    }
}