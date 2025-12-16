package com.finance.dao;

import com.finance.model.SavingsGoal;
import com.finance.util.DBConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SavingsGoalDAO {

    public boolean createGoal(SavingsGoal goal) {
        String sql = "INSERT INTO savings_goals (user_id, name, target_amount, current_amount, deadline) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, goal.getUserId());
            pstmt.setString(2, goal.getName());
            pstmt.setBigDecimal(3, goal.getTargetAmount());
            pstmt.setBigDecimal(4, goal.getCurrentAmount());
            pstmt.setDate(5, goal.getDeadline());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SavingsGoal> getGoalsByUserId(int userId) {
        List<SavingsGoal> goals = new ArrayList<>();
        String sql = "SELECT * FROM savings_goals WHERE user_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SavingsGoal goal = new SavingsGoal();
                    goal.setId(rs.getInt("id"));
                    goal.setUserId(rs.getInt("user_id"));
                    goal.setName(rs.getString("name"));
                    goal.setTargetAmount(rs.getBigDecimal("target_amount"));
                    goal.setCurrentAmount(rs.getBigDecimal("current_amount"));
                    goal.setDeadline(rs.getDate("deadline"));
                    goal.setCreatedAt(rs.getTimestamp("created_at"));
                    goals.add(goal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goals;
    }

    public boolean addFunds(int goalId, BigDecimal amount) {
        String sql = "UPDATE savings_goals SET current_amount = current_amount + ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBigDecimal(1, amount);
            pstmt.setInt(2, goalId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteGoal(int goalId) {
        String sql = "DELETE FROM savings_goals WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, goalId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public SavingsGoal getGoalById(int goalId) {
        String sql = "SELECT * FROM savings_goals WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, goalId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    SavingsGoal goal = new SavingsGoal();
                    goal.setId(rs.getInt("id"));
                    goal.setUserId(rs.getInt("user_id"));
                    goal.setName(rs.getString("name"));
                    goal.setTargetAmount(rs.getBigDecimal("target_amount"));
                    goal.setCurrentAmount(rs.getBigDecimal("current_amount"));
                    goal.setDeadline(rs.getDate("deadline"));
                    goal.setCreatedAt(rs.getTimestamp("created_at"));
                    return goal;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
