package ui;

import dao.CallerDAO;
import model.Caller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;


public class CallerPanel extends JPanel {
    private CallerDAO callerDAO;
    private JTable callerTable;
    private DefaultTableModel tableModel;

    public CallerPanel() {
        callerDAO = new CallerDAO();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Department", "Contact Info", "Address", "Account Number"});
        callerTable = new JTable(tableModel);
        add(new JScrollPane(callerTable), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(this::addCallerAction);
        updateButton.addActionListener(this::updateCallerAction);
        deleteButton.addActionListener(this::deleteCallerAction);

        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        try {
            List<Caller> callers = callerDAO.getAllCallers();
            tableModel.setRowCount(0); // Clear table
            for (Caller caller : callers) {
                Vector<Object> row = new Vector<>();
                row.add(caller.getCallerId());
                row.add(caller.getName());
                row.add(caller.getDepartment());
                row.add(caller.getContactInfo());
                row.add(caller.getAddress());
                row.add(caller.getAccountNumber());
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading callers: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCallerAction(ActionEvent event) {
        // Your implementation for adding a new caller
        // After adding, call loadData to refresh the table
    }

    private void updateCallerAction(ActionEvent event) {
        int selectedRow = callerTable.getSelectedRow();
        if (selectedRow >= 0) {
            Integer callerId = (Integer) tableModel.getValueAt(selectedRow, 0);
            // Fetch caller from the database by ID and populate the update dialog
            // Your implementation for updating a caller
            // After updating, call loadData to refresh the table
        } else {
            JOptionPane.showMessageDialog(this, "Please select a caller to update.", "No Caller Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteCallerAction(ActionEvent event) {
        int selectedRow = callerTable.getSelectedRow();
        if (selectedRow >= 0) {
            Integer callerId = (Integer) tableModel.getValueAt(selectedRow, 0);
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this caller?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                // Your implementation for deleting a caller
                // After deleting, call loadData to refresh the table
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a caller to delete.", "No Caller Selected", JOptionPane.WARNING_MESSAGE);
        }
    }
}
