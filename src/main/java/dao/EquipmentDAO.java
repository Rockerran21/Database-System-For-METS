package dao;

import model.Equipment;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {

    public EquipmentDAO() {}

    // Assuming CRUD methods are implemented correctly
    // Example: Fetch all equipment
    public List<Equipment> getAllEquipment() throws SQLException {
        List<Equipment> equipmentList = new ArrayList<>();
        String sql = "SELECT * FROM equipment";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Equipment equipment = new Equipment(
                        rs.getString("type"),
                        rs.getString("make"),
                        rs.getString("model"));
                equipment.setEquipmentId(rs.getInt("equipment_id"));
                equipmentList.add(equipment);
            }
        }
        return equipmentList;
    }

    // Add, Update, and Delete methods go here
}
