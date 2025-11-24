package com.finance.dao;

import com.finance.model.Advice;
import com.finance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdviceDAO {

    public boolean sendAdvice(Advice advice) {
        String sql = "INSERT INTO advice (advisor_id, user_id, message) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, advice.getAdvisorId());
            pstmt.setInt(2, advice.getUserId());
            pstmt.setString(3, advice.getMessage());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Advice> getAdviceByUserId(int userId) {
        List<Advice> adviceList = new ArrayList<>();
        String sql = "SELECT * FROM advice WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                adviceList.add(new Advice(
                    rs.getInt("id"),
                    rs.getInt("advisor_id"),
                    rs.getInt("user_id"),
                    rs.getString("message"),
                    rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adviceList;
    }
    
    public List<Advice> getAdviceByAdvisorId(int advisorId) {
        List<Advice> adviceList = new ArrayList<>();
        String sql = "SELECT * FROM advice WHERE advisor_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, advisorId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                adviceList.add(new Advice(
                    rs.getInt("id"),
                    rs.getInt("advisor_id"),
                    rs.getInt("user_id"),
                    rs.getString("message"),
                    rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adviceList;
    }
}
