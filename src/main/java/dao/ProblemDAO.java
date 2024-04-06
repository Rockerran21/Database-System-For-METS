package dao;

import model.Problem;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProblemDAO {

    public void addProblem(Problem problem) throws SQLException {
        String sql = "INSERT INTO problem (description, status, date_reported, caller_id, equipment_id, specialist_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, problem.getDescription());
            pstmt.setString(2, problem.getStatus());
            pstmt.setDate(3, new java.sql.Date(problem.getDateReported().getTime()));
            pstmt.setInt(4, problem.getCallerId());
            pstmt.setInt(5, problem.getEquipmentId());
            pstmt.setInt(6, problem.getSpecialistId());
            pstmt.executeUpdate();
        }
    }

    public List<Problem> getAllProblems() throws SQLException {
        List<Problem> problems = new ArrayList<>();
        String sql = "SELECT * FROM problem";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                problems.add(new Problem(
                        rs.getInt("problem_id"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getDate("date_reported"),
                        rs.getInt("caller_id"),
                        rs.getInt("equipment_id"),
                        rs.getInt("specialist_id")
                ));
            }
        }
        return problems;
    }

    public void updateProblem(Problem problem) throws SQLException {
        String sql = "UPDATE problem SET description = ?, status = ?, date_reported = ?, caller_id = ?, equipment_id = ?, specialist_id = ? WHERE problem_id = ?";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, problem.getDescription());
            pstmt.setString(2, problem.getStatus());
            pstmt.setDate(3, new java.sql.Date(problem.getDateReported().getTime()));
            pstmt.setInt(4, problem.getCallerId());
            pstmt.setInt(5, problem.getEquipmentId());
            pstmt.setInt(6, problem.getSpecialistId());
            pstmt.setInt(7, problem.getProblemId());
            pstmt.executeUpdate();
        }
    }

    public void deleteProblem(int problemId) throws SQLException {
        String sql = "DELETE FROM problem WHERE problem_id = ?";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, problemId);
            pstmt.executeUpdate();
        }
    }
}
