import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class ProblemReportPanel extends JPanel {
    private ProblemDetailDAO problemDetailDAO;
    private JTable problemsTable;
    private DefaultTableModel tableModel;

    public ProblemReportPanel() {
        problemDetailDAO = new ProblemDetailDAO();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Initialize the table model and table
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Problem ID", "Description", "Status", "Date Reported", "Resolution Date", "Resolution Detail"});
        problemsTable = new JTable(tableModel);
        add(new JScrollPane(problemsTable), BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add Problem");
        JButton updateButton = new JButton("Update Problem");
        JButton deleteButton = new JButton("Delete Problem");

        // Attach actions
        addButton.addActionListener(this::addProblemAction);
        updateButton.addActionListener(this::updateProblemAction);
        deleteButton.addActionListener(this::deleteProblemAction);

        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        try {
            List<ProblemDetail> problems = problemDetailDAO.getAllProblemDetails();
            tableModel.setRowCount(0); // Clear existing data
            for (ProblemDetail problem : problems) {
                tableModel.addRow(new Object[]{
                        problem.getProblemId(),
                        problem.getDescription(),
                        problem.getStatus(),
                        problem.getDateReported(),
                        problem.getDateResolved(),
                        problem.getResolutionDetail()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading problem reports: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProblemAction(ActionEvent event) {
        // Show dialog or another panel to input new problem details
        // After adding, reload data
        loadData();
    }

    private void updateProblemAction(ActionEvent event) {
        // Get selected problem, show dialog or another panel to update problem details
        // After updating, reload data
        loadData();
    }

    private void deleteProblemAction(ActionEvent event) {
        // Get selected problem, confirm deletion, then delete
        // After deleting, reload data
        loadData();
    }
}
