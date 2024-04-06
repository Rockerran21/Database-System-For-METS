package ui;

import dao.SpecialistDAO;
import model.Specialist;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;



public class SpecialistPanel extends JPanel {
    private SpecialistDAO specialistDAO;
    private JTable specialistTable;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    private JTextField searchField; // For search bar
    private TableRowSorter<DefaultTableModel> rowSorter;

    public SpecialistPanel() {
        specialistDAO = new SpecialistDAO();
        initializeUI();
        initializeSearch();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Name", "Expertise Area"};
        tableModel = new DefaultTableModel(columnNames, 0);
        specialistTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(specialistTable);
        add(scrollPane, BorderLayout.CENTER);

        rowSorter = new TableRowSorter<>(tableModel);
        specialistTable.setRowSorter(rowSorter);

        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");

        addButton.addActionListener(this::addSpecialistAction);
        updateButton.addActionListener(this::updateSpecialistAction);
        deleteButton.addActionListener(this::deleteSpecialistAction);
        refreshButton.addActionListener(e -> loadData());

        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(refreshButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
    private void loadData() {
        try {
            List<Specialist> specialists = specialistDAO.getAllSpecialists();
            tableModel.setRowCount(0);
            for (Specialist specialist : specialists) {
                tableModel.addRow(new Object[]{specialist.getSpecialistId(), specialist.getName(), specialist.getExpertiseArea()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading specialists: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeSearch() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Search by Name/Expertise: "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        rowSorter = new TableRowSorter<>(tableModel);
        specialistTable.setRowSorter(rowSorter);

        searchButton.addActionListener(e -> {
            String text = searchField.getText();
            if (text.trim().length() == 0) {
                rowSorter.setRowFilter(null);
            } else {
                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });

        // Add a document listener to the search field to detect changes
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFilter();
            }

            private void updateFilter() {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

    private void addSpecialistAction(ActionEvent e) {
        JTextField nameField = new JTextField();
        JTextField expertiseAreaField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Expertise Area:"));
        panel.add(expertiseAreaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Specialist", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Specialist newSpecialist = new Specialist(nameField.getText().trim(), expertiseAreaField.getText().trim());
                specialistDAO.addSpecialist(newSpecialist);
                JOptionPane.showMessageDialog(this, "Specialist added successfully.");
                loadData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding specialist: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateSpecialistAction(ActionEvent e) {
        int selectedRow = specialistTable.getSelectedRow();
        if (selectedRow != -1) {
            Integer specialistId = (Integer) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            String expertiseArea = (String) tableModel.getValueAt(selectedRow, 2);

            JTextField nameField = new JTextField(name);
            JTextField expertiseAreaField = new JTextField(expertiseArea);

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Expertise Area:"));
            panel.add(expertiseAreaField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Update Specialist", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    Specialist updatedSpecialist = new Specialist(nameField.getText(), expertiseAreaField.getText());
                    updatedSpecialist.setSpecialistId(specialistId); // Assuming Specialist class has this method
                    specialistDAO.updateSpecialist(updatedSpecialist);
                    JOptionPane.showMessageDialog(this, "Specialist updated successfully.");
                    loadData(); // Refresh the table data
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error updating specialist: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a specialist to update.", "No Specialist Selected", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void deleteSpecialistAction(ActionEvent e) {
        int selectedRow = specialistTable.getSelectedRow();
        if (selectedRow >= 0) {
            Integer specialistId = (Integer) tableModel.getValueAt(selectedRow, 0);
            String specialistName = (String) tableModel.getValueAt(selectedRow, 1);

            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete " + specialistName + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    specialistDAO.deleteSpecialist(specialistId);
                    JOptionPane.showMessageDialog(this, "Specialist deleted successfully.");
                    loadData(); // Refresh the table to show updated list after deletion
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting specialist: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a specialist to delete.", "No Specialist Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

}
