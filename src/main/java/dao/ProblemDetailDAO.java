package dao;

import model.ProblemDetail;
import util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemDetailDAO {

    // Method to get all problem details without date_resolved
    public List<ProblemDetail> getAllProblemsWithDetails() throws SQLException {
        List<ProblemDetail> problemDetails = new ArrayList<>();
        String sql = "SELECT p.problem_id, p.description, p.status, p.date_reported, p.caller_id, p.equipment_id, p.specialist_id, pr.resolution_details " +
                "FROM problem p LEFT JOIN problem_resolution pr ON p.problem_id = pr.problem_id";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                problemDetails.add(new ProblemDetail(
                        rs.getInt("problem_id"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getDate("date_reported"),
                        rs.getInt("caller_id"),
                        rs.getInt("equipment_id"),
                        rs.getInt("specialist_id"),
                        rs.getString("resolution_details") // Now correctly referenced from the joined table
                ));
            }
        }
        return problemDetails;
    }

    public ProblemDetail getProblemById(int problemId) throws SQLException {
        String sql = "SELECT * FROM problem WHERE problem_id = ?";
        ProblemDetail problemDetail = null;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, problemId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                problemDetail = new ProblemDetail(
                        rs.getInt("problem_id"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getDate("date_reported"),
                        rs.getInt("caller_id"),
                        rs.getInt("equipment_id"),
                        rs.getInt("specialist_id"),
                        rs.getString("resolution_details") // If you have this field in your table
                );
            }
        }
        return problemDetail;
    }


    // Methods to add, update, and delete ProblemDetail
    public void addProblem(ProblemDetail problemDetail) throws SQLException {
        String sql = "INSERT INTO problem (description, status, date_reported, caller_id, equipment_id, specialist_id, resolution_details) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, problemDetail.getDescription());
            pstmt.setString(2, problemDetail.getStatus());
            pstmt.setDate(3, new java.sql.Date(problemDetail.getDateReported().getTime()));
            pstmt.setInt(4, problemDetail.getCallerId());
            pstmt.setInt(5, problemDetail.getEquipmentId());
            pstmt.setInt(6, problemDetail.getSpecialistId());
            pstmt.setString(7, problemDetail.getResolutionDetails());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating problem failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    problemDetail.setProblemId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating problem failed, no ID obtained.");
                }
            }
        }
    }

    // Updates an existing problem detail record
    public void updateProblem(ProblemDetail problemDetail) throws SQLException {
        String sql = "UPDATE problem SET description = ?, status = ?, date_reported = ?, caller_id = ?, equipment_id = ?, specialist_id = ?, resolution_details = ? WHERE problem_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, problemDetail.getDescription());
            pstmt.setString(2, problemDetail.getStatus());
            pstmt.setDate(3, new java.sql.Date(problemDetail.getDateReported().getTime()));
            pstmt.setInt(4, problemDetail.getCallerId());
            pstmt.setInt(5, problemDetail.getEquipmentId());
            pstmt.setInt(6, problemDetail.getSpecialistId());
            pstmt.setString(7, problemDetail.getResolutionDetails());
            pstmt.setInt(8, problemDetail.getProblemId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating problem failed, no rows affected.");
            }
        }
    }

    // Deletes an existing problem detail record
    public void deleteProblem(int problemId) throws SQLException {
        String sql = "DELETE FROM problem WHERE problem_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, problemId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting problem failed, no rows affected.");
            }
        }
    }
}