package com.finance.dao;

import com.finance.model.Feedback;
import com.finance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    public boolean submitFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback (user_id, message) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, feedback.getUserId());
            pstmt.setString(2, feedback.getMessage());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.*, u.name as user_name FROM feedback f JOIN users u ON f.user_id = u.id ORDER BY f.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Feedback f = new Feedback(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("message"),
                    rs.getTimestamp("created_at")
                );
                f.setUserName(rs.getString("user_name"));
                feedbackList.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }
}
