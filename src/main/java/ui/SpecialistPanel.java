package ui;

import dao.SpecialistDAO;
import model.Specialist;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class SpecialistPanel extends JPanel {
    private SpecialistDAO specialistDAO = new SpecialistDAO();

    public SpecialistPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton addButton = new JButton("Add Specialist");
        addButton.addActionListener(this::addSpecialistAction);
        add(addButton);

        JButton listButton = new JButton("List Specialists");
        listButton.addActionListener(this::listSpecialistsAction);
        add(listButton);

        // Implement UPDATE and DELETE buttons and actions in a similar manner
    }

    private void addSpecialistAction(ActionEvent event) {
        JTextField nameField = new JTextField(5);
        JTextField expertiseAreaField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Name:"));
        myPanel.add(nameField);
        myPanel.add(new JLabel("Expertise Area:"));
        myPanel.add(expertiseAreaField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Enter Specialist Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Specialist specialist = new Specialist(nameField.getText(), expertiseAreaField.getText());
                specialistDAO.addSpecialist(specialist);
                JOptionPane.showMessageDialog(this, "Specialist added successfully!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error adding specialist: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listSpecialistsAction(ActionEvent event) {
        try {
            java.util.List<Specialist> specialists = specialistDAO.getAllSpecialists();
            JList<Specialist> list = new JList<>(specialists.toArray(new Specialist[0]));
            JOptionPane.showMessageDialog(this, new JScrollPane(list), "List of Specialists", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error listing specialists: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Methods for UPDATE and DELETE actions
}
