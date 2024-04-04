package dao;

import model.Specialist;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialistDAO {

    public SpecialistDAO() {}

    // Example: Fetch all specialists
    public List<Specialist> getAllSpecialists() throws SQLException {
        List<Specialist> specialists = new ArrayList<>();
        String sql = "SELECT * FROM specialist";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Specialist specialist = new Specialist(
                        rs.getString("name"),
                        rs.getString("expertise_area"));
                specialist.setSpecialistId(rs.getInt("specialist_id"));
                specialists.add(specialist);
            }
        }
        return specialists;
    }

    // Add, Update, and Delete methods go here
}
