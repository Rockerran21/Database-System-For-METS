import javax.swing.*;
import dao.CallerDAO;
import dao.EquipmentDAO;
import dao.SpecialistDAO;
import model.Caller;
import model.Equipment;
import model.Specialist;
import javax.swing.SwingUtilities;
import java.sql.SQLException;
import java.util.List;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;


public class Main {
    public static void main(String[] args) {
        setLookAndFeel();
        showWelcomeScreen();

        // Initialize and display the main application window.
        SwingUtilities.invokeLater(() -> {
            ApplicationMain mainFrame = new ApplicationMain();
            mainFrame.setVisible(true);
        });

        // Here you can call your database operations for testing if needed.
        // testDatabaseOperations();
    }

    private static void setLookAndFeel() {
        // Set Nimbus Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to the default look and feel.
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    private static void showWelcomeScreen() {
        JWindow welcomeScreen = new JWindow();
        welcomeScreen.setSize(800, 600);
        welcomeScreen.setLocationRelativeTo(null);

        // Create a panel with a border for padding
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setLayout(new BorderLayout());

        // Set a nice background color
        panel.setBackground(new Color(43, 255, 255)); // You can choose a color that fits your theme

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome to the Database Management System Of METS", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Sans-serif", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(50, 50, 50)); // Dark text color for contrast

        // Optionally set an image icon
        ImageIcon icon = new ImageIcon("./Welcome.png");
        // Ensure the image path is correct
        JLabel iconLabel = new JLabel(icon);

        // Add components to the panel
        panel.add(welcomeLabel, BorderLayout.CENTER);
        panel.add(iconLabel, BorderLayout.NORTH);

        // Add panel to the welcome screen
        welcomeScreen.getContentPane().add(panel);
        welcomeScreen.setVisible(true);

        try {
            Thread.sleep(2000); // Show the welcome screen for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        welcomeScreen.dispose(); // Close the welcome screen
    }

}
