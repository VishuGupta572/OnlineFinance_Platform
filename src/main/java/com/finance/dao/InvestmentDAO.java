package com.finance.dao;

import com.finance.model.Investment;
import com.finance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestmentDAO {

    public boolean buyInvestment(int userId, String symbol, double quantity, double price) {
        String checkSql = "SELECT id, quantity, avg_buy_price FROM investments WHERE user_id = ? AND symbol = ?";
        String updateSql = "UPDATE investments SET quantity = ?, avg_buy_price = ? WHERE id = ?";
        String insertSql = "INSERT INTO investments (user_id, symbol, quantity, avg_buy_price) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection()) {
            // Check if already owns this asset
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, userId);
                checkStmt.setString(2, symbol);
                ResultSet rs = checkStmt.executeQuery();
                
                if (rs.next()) {
                    // Update existing position (Weighted Average Price)
                    int id = rs.getInt("id");
                    double currentQty = rs.getDouble("quantity");
                    double currentAvg = rs.getDouble("avg_buy_price");
                    
                    double newQty = currentQty + quantity;
                    double totalCost = (currentQty * currentAvg) + (quantity * price);
                    double newAvg = totalCost / newQty;
                    
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setDouble(1, newQty);
                        updateStmt.setDouble(2, newAvg);
                        updateStmt.setInt(3, id);
                        return updateStmt.executeUpdate() > 0;
                    }
                } else {
                    // Insert new position
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, userId);
                        insertStmt.setString(2, symbol);
                        insertStmt.setDouble(3, quantity);
                        insertStmt.setDouble(4, price);
                        return insertStmt.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Investment> getInvestmentsByUserId(int userId) {
        List<Investment> investments = new ArrayList<>();
        String sql = "SELECT * FROM investments WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                investments.add(new Investment(
                    rs.getInt("user_id"),
                    rs.getString("symbol"),
                    rs.getDouble("quantity"),
                    rs.getDouble("avg_buy_price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return investments;
    }
}
