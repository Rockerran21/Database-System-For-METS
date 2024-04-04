package ui;

import dao.CallerDAO;
import model.Caller;
import util.DatabaseUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class CallerPanel extends JPanel {
    private CallerDAO callerDAO = new CallerDAO();
    private JTable table;
    private DefaultTableModel model;

    public CallerPanel() {
        setLayout(new BorderLayout());
        callerDAO = new CallerDAO();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Department", "Contact Info", "Address", "Account Number"});
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add User");
        JButton viewButton = new JButton("View Users");
        JButton updateButton = new JButton("Update User");
        JButton deleteButton = new JButton("Delete User");

        addButton.addActionListener(this::addCallerAction);
        viewButton.addActionListener(e -> loadData());
        updateButton.addActionListener(this::updateCallerAction);
        deleteButton.addActionListener(this::deleteCallerAction);

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        try {
            List<Caller> callers = callerDAO.getAllCallers();
            model.setRowCount(0); // Clear existing content
            for (Caller caller : callers) {
                model.addRow(new Object[]{
                        caller.getCallerId(), caller.getName(), caller.getDepartment(),
                        caller.getContactInfo(), caller.getAddress(), caller.getAccountNumber()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCallerAction(ActionEvent e) {
        JTextField nameField = new JTextField();
        JTextField departmentField = new JTextField();
        JTextField contactInfoField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField accountNumberField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Department:"));
        panel.add(departmentField);
        panel.add(new JLabel("Contact Info:"));
        panel.add(contactInfoField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Account Number:"));
        panel.add(accountNumberField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Caller", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int accountNumber = Integer.parseInt(accountNumberField.getText().trim());
                Caller newCaller = new Caller(nameField.getText().trim(), departmentField.getText().trim(), contactInfoField.getText().trim(), addressField.getText().trim(), accountNumber);
                callerDAO.addCaller(newCaller);
                JOptionPane.showMessageDialog(this, "Caller added successfully.");
                loadData(); // Refresh table data
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Account number must be a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding caller: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void updateCallerAction(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            String name = model.getValueAt(selectedRow, 1).toString();
            String department = model.getValueAt(selectedRow, 2).toString();
            String contactInfo = model.getValueAt(selectedRow, 3).toString();
            String address = model.getValueAt(selectedRow, 4).toString();
            int accountNumber = Integer.parseInt(model.getValueAt(selectedRow, 5).toString());

            JTextField nameField = new JTextField(name);
            JTextField departmentField = new JTextField(department);
            JTextField contactInfoField = new JTextField(contactInfo);
            JTextField addressField = new JTextField(address);
            JTextField accountNumberField = new JTextField(String.valueOf(accountNumber));

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Department:"));
            panel.add(departmentField);
            panel.add(new JLabel("Contact Info:"));
            panel.add(contactInfoField);
            panel.add(new JLabel("Address:"));
            panel.add(addressField);
            panel.add(new JLabel("Account Number:"));
            panel.add(accountNumberField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Update Caller", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                Caller updatedCaller = new Caller(nameField.getText(), departmentField.getText(), contactInfoField.getText(), addressField.getText(), Integer.parseInt(accountNumberField.getText()));
                updatedCaller.setCallerId(id);
                try {
                    callerDAO.updateCaller(updatedCaller);
                    JOptionPane.showMessageDialog(this, "Caller updated successfully.");
                    loadData(); // Refresh table data
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error updating caller: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a caller to update.", "No Caller Selected", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void deleteCallerAction(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this caller?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                try {
                    callerDAO.deleteCaller(id);
                    loadData(); // Refresh table data
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting caller: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a caller to delete.");
        }
    }
}
