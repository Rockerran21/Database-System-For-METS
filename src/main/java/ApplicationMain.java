import javax.swing.*;
import java.awt.*;
import ui.CallerPanel;
import ui.EquipmentPanel;
import ui.SpecialistPanel;


public class ApplicationMain extends JFrame {
    public ApplicationMain() {
        setTitle("CRM System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Add panels
        CallerPanel callerPanel = new CallerPanel();
        EquipmentPanel equipmentPanel = new EquipmentPanel();
        SpecialistPanel specialistPanel = new SpecialistPanel();

        tabbedPane.addTab("Callers", callerPanel);
        tabbedPane.addTab("Equipment", equipmentPanel);
        tabbedPane.addTab("Specialists", specialistPanel);

        // Add tabbedPane to the frame
        add(tabbedPane, BorderLayout.CENTER);
    }
}