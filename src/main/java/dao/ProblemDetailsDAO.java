package dao;

import model.ProblemDetail;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemDetailDAO {

    public List<ProblemDetail> getAllProblemsWithDetails() throws SQLException {
        List<ProblemDetail> problemDetails = new ArrayList<>();
        String sql = "SELECT p.problem_id, p.description, p.status, p.date_reported, p.caller_id, p.equipment_id, p.specialist_id, pr.date_resolved, pr.resolution_detail " +
                "FROM problem p " +
                "LEFT JOIN problem_resolution pr ON p.problem_id = pr.problem_id";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ProblemDetail pd = new ProblemDetail(
                        rs.getInt("problem_id"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("date_reported"),
                        rs.getInt("caller_id"),
                        rs.getInt("equipment_id"),
                        rs.getInt("specialist_id"),
                        rs.getTimestamp("date_resolved"),
                        rs.getString("resolution_detail")
                );
                problemDetails.add(pd);
            }
        }
        return problemDetails;
    }

    public void addProblem(ProblemDetail pd) throws SQLException {
        String sqlProblem = "INSERT INTO problem (description, status, date_reported, caller_id, equipment_id, specialist_id) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseUtil.getConnection();
        try (PreparedStatement pstmtProblem = conn.prepareStatement(sqlProblem, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);

            pstmtProblem.setString(1, pd.getDescription());
            pstmtProblem.setString(2, pd.getStatus());
            pstmtProblem.setTimestamp(3, new Timestamp(pd.getDateReported().getTime()));
            pstmtProblem.setInt(4, pd.getCallerId());
            pstmtProblem.setInt(5, pd.getEquipmentId());
            pstmtProblem.setInt(6, pd.getSpecialistId());

            int affectedRows = pstmtProblem.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating problem failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmtProblem.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pd.setProblemId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating problem failed, no ID obtained.");
                }
            }

            if (pd.getDateResolved() != null && pd.getResolutionDetail() != null && !pd.getResolutionDetail().isEmpty()) {
                String sqlResolution = "INSERT INTO problem_resolution (problem_id, date_resolved, resolution_detail) VALUES (?, ?, ?)";
                try (PreparedStatement pstmtResolution = conn.prepareStatement(sqlResolution)) {
                    pstmtResolution.setInt(1, pd.getProblemId());
                    pstmtResolution.setTimestamp(2, new Timestamp(pd.getDateResolved().getTime()));
                    pstmtResolution.setString(3, pd.getResolutionDetail());

                    pstmtResolution.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            throw ex;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }

    public void updateProblem(ProblemDetail pd) throws SQLException {
        String sqlUpdateProblem = "UPDATE problem SET description = ?, status = ?, date_reported = ?, caller_id = ?, equipment_id = ?, specialist_id = ? WHERE problem_id = ?";
        String sqlUpdateResolution = "UPDATE problem_resolution SET date_resolved = ?, resolution_detail = ? WHERE problem_id = ?";
        String sqlInsertResolution = "INSERT INTO problem_resolution (problem_id, date_resolved, resolution_detail) VALUES (?, ?, ?)";
        Connection conn = DatabaseUtil.getConnection();
        try {
            conn.setAutoCommit(false);

            // Update the problem record
            try (PreparedStatement pstmtUpdateProblem = conn.prepareStatement(sqlUpdateProblem)) {
                pstmtUpdateProblem.setString(1, pd.getDescription());
                pstmtUpdateProblem.setString(2, pd.getStatus());
                pstmtUpdateProblem.setTimestamp(3, new Timestamp(pd.getDateReported().getTime()));
                pstmtUpdateProblem.setInt(4, pd.getCallerId());
                pstmtUpdateProblem.setInt(5, pd.getEquipmentId());
                pstmtUpdateProblem.setInt(6, pd.getSpecialistId());
                pstmtUpdateProblem.setInt(7, pd.getProblemId());
                pstmtUpdateProblem.executeUpdate();
            }

            // Update or insert the resolution
            int affectedRows;
            try (PreparedStatement pstmtUpdateResolution = conn.prepareStatement(sqlUpdateResolution)) {
                pstmtUpdateResolution.setTimestamp(1, new Timestamp(pd.getDateResolved().getTime()));
                pstmtUpdateResolution.setString(2, pd.getResolutionDetail());
                pstmtUpdateResolution.setInt(3, pd.getProblemId());
                affectedRows = pstmtUpdateResolution.executeUpdate();
            }

            // If no resolution was updated and we have resolution details, insert a new resolution
            if (affectedRows == 0 && pd.getDateResolved() != null && pd.getResolutionDetail() != null) {
                try (PreparedStatement pstmtInsertResolution = conn.prepareStatement(sqlInsertResolution)) {
                    pstmtInsertResolution.setInt(1, pd.getProblemId());
                    pstmtInsertResolution.setTimestamp(2, new Timestamp(pd.getDateResolved().getTime()));
                    pstmtInsertResolution.setString(3, pd.getResolutionDetail());
                    pstmtInsertResolution.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            throw ex;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }

    public void deleteProblem(int problemId) throws SQLException {
        String sqlDeleteResolution = "DELETE FROM problem_resolution WHERE problem_id = ?";
        String sqlDeleteProblem = "DELETE FROM problem WHERE problem_id = ?";
        Connection conn = DatabaseUtil.getConnection();
        try {
            conn.setAutoCommit(false);

            // First, delete the resolution if it exists
            try (PreparedStatement pstmtDeleteResolution = conn.prepareStatement(sqlDeleteResolution)) {
                pstmtDeleteResolution.setInt(1, problemId);
                pstmtDeleteResolution.executeUpdate();
            }

            // Then, delete the problem
            try (PreparedStatement pstmtDeleteProblem = conn.prepareStatement(sqlDeleteProblem)) {
                pstmtDeleteProblem.setInt(1, problemId);
                pstmtDeleteProblem.executeUpdate();
            }

            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            throw ex;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }
}
