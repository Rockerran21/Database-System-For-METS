package dao;

import model.Equipment;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {

    public void addEquipment(Equipment equipment) throws SQLException {
        String sql = "INSERT INTO equipment (type, make, model) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipment.getType());
            pstmt.setString(2, equipment.getMake());
            pstmt.setString(3, equipment.getModel());
            pstmt.executeUpdate();
        }
    }

    public List<Equipment> getAllEquipment() throws SQLException {
        List<Equipment> equipmentList = new ArrayList<>();
        String sql = "SELECT * FROM equipment";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
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

    public void updateEquipment(Equipment equipment) throws SQLException {
        String sql = "UPDATE equipment SET type = ?, make = ?, model = ? WHERE equipment_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipment.getType());
            pstmt.setString(2, equipment.getMake());
            pstmt.setString(3, equipment.getModel());
            pstmt.setInt(4, equipment.getEquipmentId());
            pstmt.executeUpdate();
        }
    }

    public void deleteEquipment(int equipmentId) throws SQLException {
        String sql = "DELETE FROM equipment WHERE equipment_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipmentId);
            pstmt.executeUpdate();
        }
    }
}
