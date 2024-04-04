package ui;

import dao.EquipmentDAO;
import model.Equipment;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class EquipmentPanel extends JPanel {
    private EquipmentDAO equipmentDAO = new EquipmentDAO();

    public EquipmentPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton addButton = new JButton("Add Equipment");
        addButton.addActionListener(this::addEquipmentAction);
        add(addButton);

        JButton listButton = new JButton("List Equipment");
        listButton.addActionListener(this::listEquipmentAction);
        add(listButton);

        // Implement UPDATE and DELETE buttons and actions in a similar manner
    }

    private void addEquipmentAction(ActionEvent event) {
        JTextField typeField = new JTextField(5);
        JTextField makeField = new JTextField(5);
        JTextField modelField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Type:"));
        myPanel.add(typeField);
        myPanel.add(new JLabel("Make:"));
        myPanel.add(makeField);
        myPanel.add(new JLabel("Model:"));
        myPanel.add(modelField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Enter Equipment Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Equipment equipment = new Equipment(typeField.getText(), makeField.getText(),
                        modelField.getText());
                equipmentDAO.addEquipment(equipment);
                JOptionPane.showMessageDialog(this, "Equipment added successfully!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error adding equipment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listEquipmentAction(ActionEvent event) {
        try {
            java.util.List<Equipment> equipmentList = equipmentDAO.getAllEquipment();
            JList<Equipment> list = new JList<>(equipmentList.toArray(new Equipment[0]));
            JOptionPane.showMessageDialog(this, new JScrollPane(list), "List of Equipment", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error listing equipment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Methods for UPDATE and DELETE actions
}
