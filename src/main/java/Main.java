import dao.CallerDAO;
import dao.EquipmentDAO;
import dao.SpecialistDAO;
import model.Caller;
import model.Equipment;
import model.Specialist;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CallerDAO callerDAO = new CallerDAO();
        EquipmentDAO equipmentDAO = new EquipmentDAO();
        SpecialistDAO specialistDAO = new SpecialistDAO();

        try {
            // Testing Caller operations
            Caller newCaller = new Caller("New Caller", "Sales", "newcaller@example.com", "1234 Main St", 56789);
            callerDAO.addCaller(newCaller);
            System.out.println("Caller added successfully");

            List<Caller> callers = callerDAO.getAllCallers();
            System.out.println("List of Callers:");
            for (Caller caller : callers) {
                System.out.println(caller);
            }

            // Assume ID generation is handled by the database, hence not included in constructor
            // Testing Equipment operations
            Equipment newEquipment = new Equipment("Printer", "HP", "LaserJet Pro 400");
            equipmentDAO.addEquipment(newEquipment);
            System.out.println("Equipment added successfully");

            List<Equipment> equipments = equipmentDAO.getAllEquipment();
            System.out.println("List of Equipment:");
            for (Equipment equipment : equipments) {
                System.out.println(equipment);
            }

            // Testing Specialist operations
            Specialist newSpecialist = new Specialist("John Doe", "Software Development");
            specialistDAO.addSpecialist(newSpecialist);
            System.out.println("Specialist added successfully");

            List<Specialist> specialists = specialistDAO.getAllSpecialists();
            System.out.println("List of Specialists:");
            for (Specialist specialist : specialists) {
                System.out.println(specialist);
            }

            // Example update and delete operations
            // Note: Update these operations with actual IDs and data you want to test with
            // equipmentDAO.updateEquipment(new Equipment(1, "UpdatedType", "UpdatedMake", "UpdatedModel"));
            // equipmentDAO.deleteEquipment(1);
            // specialistDAO.updateSpecialist(new Specialist(1, "UpdatedName", "UpdatedExpertise"));
            // specialistDAO.deleteSpecialist(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
