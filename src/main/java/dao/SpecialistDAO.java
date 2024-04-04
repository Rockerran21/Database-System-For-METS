package dao;

import model.Specialist;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecialistDAO {
    public void addSpecialist(Specialist specialist) throws SQLException {
        String sql = "INSERT INTO specialist (name, expertise_area) VALUES (?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, specialist.getName());
            pstmt.setString(2, specialist.getExpertiseArea());
            pstmt.executeUpdate();
        }
    }

    public List<Specialist> getAllSpecialists() throws SQLException {
        List<Specialist> specialists = new ArrayList<>();
        String sql = "SELECT * FROM specialist";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Specialist specialist = new Specialist(
                        rs.getString("name"),
                        rs.getString("expertise_area"));
                specialist.setSpecialistId(rs.getInt("specialist_id")); // Setting ID for fetched record
                specialists.add(specialist);
            }
        }
        return specialists;
    }

    // Implement updateSpecialist and deleteSpecialist as needed
}
