package ui;

import dao.EquipmentDAO;
import model.Equipment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class EquipmentPanel extends JPanel {
    private EquipmentDAO equipmentDAO;
    private JTable equipmentTable;
    private DefaultTableModel tableModel;

    public EquipmentPanel() {
        equipmentDAO = new EquipmentDAO();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Type", "Make", "Model"});
        equipmentTable = new JTable(tableModel);
        add(new JScrollPane(equipmentTable), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");

        addButton.addActionListener(this::addEquipmentAction);
        updateButton.addActionListener(this::updateEquipmentAction);
        deleteButton.addActionListener(this::deleteEquipmentAction);
        refreshButton.addActionListener(e -> loadData());

        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(refreshButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        try {
            List<Equipment> equipmentList = equipmentDAO.getAllEquipment();
            tableModel.setRowCount(0); // Clear table
            for (Equipment equipment : equipmentList) {
                tableModel.addRow(new Object[]{equipment.getEquipmentId(), equipment.getType(), equipment.getMake(), equipment.getModel()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading equipment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEquipmentAction(ActionEvent event) {
        JTextField typeField = new JTextField();
        JTextField makeField = new JTextField();
        JTextField modelField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Make:"));
        panel.add(makeField);
        panel.add(new JLabel("Model:"));
        panel.add(modelField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Equipment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Equipment newEquipment = new Equipment(typeField.getText().trim(), makeField.getText().trim(), modelField.getText().trim());
                equipmentDAO.addEquipment(newEquipment);
                JOptionPane.showMessageDialog(this, "Equipment added successfully.");
                loadData(); // Refresh table data
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error adding equipment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void updateEquipmentAction(ActionEvent event) {
        int selectedRow = equipmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            Integer equipmentId = (Integer) tableModel.getValueAt(selectedRow, 0);
            String type = (String) tableModel.getValueAt(selectedRow, 1);
            String make = (String) tableModel.getValueAt(selectedRow, 2);
            String model = (String) tableModel.getValueAt(selectedRow, 3);

            JTextField typeField = new JTextField(type);
            JTextField makeField = new JTextField(make);
            JTextField modelField = new JTextField(model);

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Type:"));
            panel.add(typeField);
            panel.add(new JLabel("Make:"));
            panel.add(makeField);
            panel.add(new JLabel("Model:"));
            panel.add(modelField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Update Equipment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    Equipment updatedEquipment = new Equipment(typeField.getText(), makeField.getText(), modelField.getText());
                    updatedEquipment.setEquipmentId(equipmentId); // Assuming Equipment class has this method
                    equipmentDAO.updateEquipment(updatedEquipment);
                    JOptionPane.showMessageDialog(this, "Equipment updated successfully.");
                    loadData(); // Refresh the table data
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error updating equipment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an equipment to update.", "No Equipment Selected", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void deleteEquipmentAction(ActionEvent event) {
        int selectedRow = equipmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this equipment?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                Integer equipmentId = (Integer) tableModel.getValueAt(selectedRow, 0);
                try {
                    equipmentDAO.deleteEquipment(equipmentId);
                    JOptionPane.showMessageDialog(this, "Equipment deleted successfully.");
                    loadData(); // Refresh table data
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error deleting equipment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an equipment to delete.", "No Equipment Selected", JOptionPane.WARNING_MESSAGE);
        }
    }
}
