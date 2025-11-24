package com.finance.dao;

import com.finance.model.Budget;
import com.finance.model.Expense;
import com.finance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FinanceDAO {

    // Expense Methods
    public boolean addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (user_id, category, amount, date, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, expense.getUserId());
            pstmt.setString(2, expense.getCategory());
            pstmt.setDouble(3, expense.getAmount());
            pstmt.setDate(4, expense.getDate());
            pstmt.setString(5, expense.getDescription());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Expense> getExpensesByUserId(int userId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE user_id = ? ORDER BY date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                expenses.add(new Expense(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("category"),
                    rs.getDouble("amount"),
                    rs.getDate("date"),
                    rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    // Budget Methods
    public boolean addBudget(Budget budget) {
        String sql = "INSERT INTO budgets (user_id, category, limit_amount) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, budget.getUserId());
            pstmt.setString(2, budget.getCategory());
            pstmt.setDouble(3, budget.getLimitAmount());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Budget> getBudgetsByUserId(int userId) {
        List<Budget> budgets = new ArrayList<>();
        String sql = "SELECT * FROM budgets WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                budgets.add(new Budget(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("category"),
                    rs.getDouble("limit_amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgets;
    }
    
    public double getTotalExpensesByCategory(int userId, String category) {
        String sql = "SELECT SUM(amount) FROM expenses WHERE user_id = ? AND category = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, category);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Aggregation Methods for Charts
    public List<String> getExpenseCategories(int userId) {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM expenses WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public double getTotalPlatformExpenses() {
        String sql = "SELECT SUM(amount) FROM expenses";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
