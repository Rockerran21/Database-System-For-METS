package dao;

import model.Caller;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CallerDAO {

    public void addCaller(Caller caller) throws SQLException {
        String sql = "INSERT INTO caller (name, department, contact_info, address, account_number) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, caller.getName());
            pstmt.setString(2, caller.getDepartment());
            pstmt.setString(3, caller.getContactInfo());
            pstmt.setString(4, caller.getAddress());
            pstmt.setInt(5, caller.getAccountNumber());
            pstmt.executeUpdate();
        }
    }

    public List<Caller> getAllCallers() throws SQLException {
        List<Caller> callers = new ArrayList<>();
        String sql = "SELECT * FROM caller";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Caller caller = new Caller(
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("contact_info"),
                        rs.getString("address"),
                        rs.getInt("account_number"));
                caller.setCallerId(rs.getInt("caller_id"));
                callers.add(caller);
            }
        }
        return callers;
    }

    public void updateCaller(Caller caller) throws SQLException {
        String sql = "UPDATE caller SET name = ?, department = ?, contact_info = ?, address = ?, account_number = ? WHERE caller_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, caller.getName());
            pstmt.setString(2, caller.getDepartment());
            pstmt.setString(3, caller.getContactInfo());
            pstmt.setString(4, caller.getAddress());
            pstmt.setInt(5, caller.getAccountNumber());
            pstmt.setInt(6, caller.getCallerId());
            pstmt.executeUpdate();
        }
    }

    public void deleteCaller(int callerId) throws SQLException {
        String sql = "DELETE FROM caller WHERE caller_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, callerId);
            pstmt.executeUpdate();
        }
    }
}
