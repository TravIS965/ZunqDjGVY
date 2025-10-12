// 代码生成时间: 2025-10-12 22:55:54
package com.example.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ModalDialog extends JDialog {

    // Constructor
    public ModalDialog(JFrame parent) {
        super(parent, true); // Set the dialog to be modal
        initializeUI(); // Initialize the user interface
    }

    // Initializes the user interface components
    private void initializeUI() {
        // Set the title and size of the dialog
        setTitle("Modal Dialog");
        setSize(300, 200);
        setLocationRelativeTo(null); // Center the dialog

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        getLayeredPane().add(panel);

        // Create a label and a button
        JLabel label = new JLabel("This is a modal dialog.");
        JButton closeButton = new JButton("Close");

        // Add action listener to the button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the dialog when the button is clicked
                dispose();
            }
        });

        // Layout the components
        panel.add(label);
        panel.add(closeButton);
    }

    // Main method for testing the dialog
    public static void main(String[] args) {
        try {
            // Create a JFrame instance to act as the parent of the dialog
            JFrame frame = new JFrame();
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            // Show the modal dialog
            ModalDialog dialog = new ModalDialog(frame);
            dialog.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions that occur
        }
    }
}
