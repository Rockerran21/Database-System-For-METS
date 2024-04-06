package dao;

import model.ProblemResolution;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProblemResolutionDAO {

    public void addProblemResolution(ProblemResolution resolution) throws SQLException {
        String sql = "INSERT INTO problem_resolution (problem_id, date_resolved, resolution_detail) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, resolution.getProblemId());
            pstmt.setDate(2, new java.sql.Date(resolution.getDateResolved().getTime()));
            pstmt.setString(3, resolution.getResolutionDetail());
            pstmt.executeUpdate();
        }
    }

    public List<ProblemResolution> getAllProblemResolutions() throws SQLException {
        List<ProblemResolution> resolutions = new ArrayList<>();
        String sql = "SELECT * FROM problem_resolution";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                resolutions.add(new ProblemResolution(
                        rs.getInt("resolution_id"),
                        rs.getInt("problem_id"),
                        rs.getDate("date_resolved"),
                        rs.getString("resolution_detail")
                ));
            }
        }
        return resolutions;
    }

    public void updateProblemResolution(ProblemResolution resolution) throws SQLException {
        String sql = "UPDATE problem_resolution SET problem_id = ?, date_resolved = ?, resolution_detail = ? WHERE resolution_id = ?";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, resolution.getProblemId());
            pstmt.setDate(2, new java.sql.Date(resolution.getDateResolved().getTime()));
            pstmt.setString(3, resolution.getResolutionDetail());
            pstmt.setInt(4, resolution.getResolutionId());
            pstmt.executeUpdate();
        }
    }

    public void deleteProblemResolution(int resolutionId) throws SQLException {
        String sql = "DELETE FROM problem_resolution WHERE resolution_id = ?";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, resolutionId);
            pstmt.executeUpdate();
        }
    }
}
