import javax.swing.*;
import java.awt.*;
import ui.CallerPanel;
import ui.EquipmentPanel;
import ui.SpecialistPanel;
import ui.ProblemPanel;

public class ApplicationMain extends JFrame {
    public ApplicationMain() {
        setTitle("Database Management System for METS Call Center");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Add panels
        CallerPanel callerPanel = new CallerPanel();
        EquipmentPanel equipmentPanel = new EquipmentPanel();
        SpecialistPanel specialistPanel = new SpecialistPanel();
        ProblemPanel problemPanel = new ProblemPanel();

        tabbedPane.addTab("Callers", callerPanel);
        tabbedPane.addTab("Equipment", equipmentPanel);
        tabbedPane.addTab("Specialists", specialistPanel);
        tabbedPane.addTab("Problems", problemPanel);

        // Add tabbedPane to the frame
        add(tabbedPane, BorderLayout.CENTER);
    }

}
