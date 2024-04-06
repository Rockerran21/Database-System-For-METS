package ui;

import dao.ProblemDetailDAO;
import model.ProblemDetail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ProblemPanel extends JPanel {
    private ProblemDetailDAO problemDetailDAO;
    private JTable problemTable;
    private DefaultTableModel tableModel;

    public ProblemPanel() {
        problemDetailDAO = new ProblemDetailDAO();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Problem ID", "Description", "Status", "Date Reported", "Caller ID", "Equipment ID", "Specialist ID", "Resolution Detail"});
        problemTable = new JTable(tableModel);
        add(new JScrollPane(problemTable), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");

        addButton.addActionListener(this::addProblemAction);
        updateButton.addActionListener(this::updateProblemAction);
        deleteButton.addActionListener(this::deleteProblemAction);
        refreshButton.addActionListener(e -> loadData());

        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(refreshButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        try {
            List<ProblemDetail> problemList = problemDetailDAO.getAllProblemsWithDetails();
            tableModel.setRowCount(0); // Clear table
            for (ProblemDetail problem : problemList) {
                tableModel.addRow(new Object[]{
                        problem.getProblemId(),
                        problem.getDescription(),
                        problem.getStatus(),
                        problem.getDateReported(),
                        problem.getCallerId(),
                        problem.getEquipmentId(),
                        problem.getSpecialistId(),
                        problem.getResolutionDetails()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading problems: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProblemAction(ActionEvent event) {
        // Assuming ProblemDetail constructor and all setters are properly defined
        // This dialog is a simplification. Adjust according to your actual data and validation needs
        JTextField descriptionField = new JTextField();
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Open", "Closed", "In Progress"});
        JTextField callerIdField = new JTextField();
        JTextField equipmentIdField = new JTextField();
        JTextField specialistIdField = new JTextField();
        // Assuming resolution details are entered elsewhere or at a different time
        // Date reported can be set to the current date/time

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);
        panel.add(new JLabel("Caller ID:"));
        panel.add(callerIdField);
        panel.add(new JLabel("Equipment ID:"));
        panel.add(equipmentIdField);
        panel.add(new JLabel("Specialist ID:"));
        panel.add(specialistIdField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Problem", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Simplified: Parse integer fields directly, without validation. In real scenarios, validate or use safer parsing methods.
                ProblemDetail newProblem = new ProblemDetail(
                        -1, // Assuming ID is auto-generated
                        descriptionField.getText(),
                        (String) statusComboBox.getSelectedItem(),
                        new java.util.Date(), // Assuming current date for dateReported
                        Integer.parseInt(callerIdField.getText()),
                        Integer.parseInt(equipmentIdField.getText()),
                        Integer.parseInt(specialistIdField.getText()),
                        ""// Assuming resolution detail is empty initially
                );
                problemDetailDAO.addProblem(newProblem);
                JOptionPane.showMessageDialog(this, "Problem added successfully.");
                loadData(); // Refresh table data
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error adding problem: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error in number format: " + e.getMessage(), "Format Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateProblemAction(ActionEvent event) {
        // Similar approach as addProblemAction, but you'll need to first select a problem from the table
        // Then, populate the dialog with the selected problem's details for editing
        // Finally, call problemDetailDAO.updateProblem(updatedProblem) and refresh the table
        int selectedRow = problemTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Fetch the problem ID from the table model, assuming it's the first column
            int problemId = (Integer) tableModel.getValueAt(selectedRow, 0);
            try {
                ProblemDetail problem = problemDetailDAO.getProblemById(problemId);
                if (problem != null) {
                    // Open a dialog pre-populated with the problem's details for updating
                    // Similar to addProblemAction but populate fields with `problem` data
                    // After getting updatedProblem from dialog, call problemDetailDAO.updateProblem(updatedProblem)
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error fetching problem details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a problem to update.", "No Problem Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteProblemAction(ActionEvent event) {
        int selectedRow = problemTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this problem?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                int problemId = (Integer) tableModel.getValueAt(selectedRow, 0);
                try {
                    problemDetailDAO.deleteProblem(problemId);
                    JOptionPane.showMessageDialog(this, "Problem deleted successfully.");
                    loadData(); // Refresh table data
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error deleting problem: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a problem to delete.", "No Problem Selected", JOptionPane.WARNING_MESSAGE);
        }
    }
}