package com.reservation;

import javax.swing.*;
import java.awt.*;

public class CancellationUI extends JFrame {
    private JTextField pnrField;
    private JTextArea detailsArea;

    public CancellationUI() {
        setTitle("Cancel Ticket");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel: PNR input and fetch
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("PNR Number:"));
        pnrField = new JTextField(15);
        topPanel.add(pnrField);
        JButton fetchBtn = new JButton("Fetch Details");
        topPanel.add(fetchBtn);
        panel.add(topPanel, BorderLayout.NORTH);

        // Center: Details display
        detailsArea = new JTextArea(10, 40);
        detailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bottom: Cancel and Back buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton cancelBtn = new JButton("Cancel Ticket");
        JButton backBtn = new JButton("Back");
        bottomPanel.add(cancelBtn);
        bottomPanel.add(backBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        // Fetch action
        fetchBtn.addActionListener(e -> fetchDetails());

        // Cancel action
        cancelBtn.addActionListener(e -> cancelTicket());

        // Back to menu
        backBtn.addActionListener(e -> {
            dispose();
            new MainMenuUI().setVisible(true);
        });
    }

    private void fetchDetails() {
        String pnr = pnrField.getText().trim();
        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a PNR number.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Reservation res = DatabaseManager.getReservationByPNR(pnr);
        if (res == null) {
            detailsArea.setText("No booking found for PNR: " + pnr);
            JOptionPane.showMessageDialog(this, "Booking not found.",
                    "Not Found", JOptionPane.WARNING_MESSAGE);
        } else {
            String details = String.format(
                "PNR: %s\nPassenger: %s\nTrain: %s (%d)\nClass: %s\nDate: %s\nFrom: %s\nTo: %s",
                res.getPnr(), res.getPassengerName(), res.getTrainName(),
                res.getTrainNumber(), res.getClassType(), res.getJourneyDate(),
                res.getSource(), res.getDestination()
            );
            detailsArea.setText(details);
        }
    }

    private void cancelTicket() {
        String pnr = pnrField.getText().trim();
        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a PNR number.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if booking exists
        Reservation res = DatabaseManager.getReservationByPNR(pnr);
        if (res == null) {
            JOptionPane.showMessageDialog(this, "No booking found for PNR: " + pnr,
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel this booking?\n" +
                "PNR: " + pnr + "\nPassenger: " + res.getPassengerName(),
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = DatabaseManager.cancelReservation(pnr);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                detailsArea.setText("");
                pnrField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Cancellation failed.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}