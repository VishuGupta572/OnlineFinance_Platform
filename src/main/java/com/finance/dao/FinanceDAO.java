package com.finance.dao;

import com.finance.model.Budget;
import com.finance.model.Expense;
import com.finance.model.Transaction;
import com.finance.model.RecurringExpense;
import com.finance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FinanceDAO {

    // Recurring Expense Methods
    public boolean addRecurringExpense(RecurringExpense expense) {
        String sql = "INSERT INTO recurring_expenses (user_id, category, amount, description, active) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, expense.getUserId());
            pstmt.setString(2, expense.getCategory());
            pstmt.setDouble(3, expense.getAmount());
            pstmt.setString(4, expense.getDescription());
            pstmt.setBoolean(5, expense.isActive());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<RecurringExpense> getAllActiveRecurringExpenses() {
        List<RecurringExpense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM recurring_expenses WHERE active = TRUE";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                expenses.add(new RecurringExpense(
                    rs.getInt("user_id"),
                    rs.getString("category"),
                    rs.getDouble("amount"),
                    rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    public boolean checkIfExpenseExists(int userId, double amount, Date date, String description) {
        String sql = "SELECT id FROM expenses WHERE user_id = ? AND amount = ? AND date = ? AND description = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDouble(2, amount);
            pstmt.setDate(3, date);
            pstmt.setString(4, description);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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

    // Fund Transfer
    // Fund Transfer
    public boolean transferFunds(int fromUserId, String toEmail, double amount) {
        String checkBalanceSql = "SELECT balance FROM users WHERE id = ?";
        String getReceiverSql = "SELECT id FROM users WHERE email = ?";
        String debitSql = "UPDATE users SET balance = balance - ? WHERE id = ?";
        String creditSql = "UPDATE users SET balance = balance + ? WHERE id = ?";
        String logTransactionSql = "INSERT INTO transactions (sender_id, receiver_id, amount, type) VALUES (?, ?, ?, 'TRANSFER')";
        
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start Transaction

            // 1. Check Balance
            double balance = 0.0;
            try (PreparedStatement pstmt = conn.prepareStatement(checkBalanceSql)) {
                pstmt.setInt(1, fromUserId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    balance = rs.getDouble("balance");
                }
            }

            if (balance < amount) {
                conn.rollback();
                return false;
            }

            // 2. Get Receiver ID
            int receiverId = -1;
            try (PreparedStatement pstmt = conn.prepareStatement(getReceiverSql)) {
                pstmt.setString(1, toEmail);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    receiverId = rs.getInt("id");
                } else {
                    conn.rollback(); // Receiver not found
                    return false;
                }
            }
            
            // Prevent self-transfer (double check, though filtered in UI often)
            if (fromUserId == receiverId) {
                conn.rollback();
                return false;
            }

            // 3. Debit Sender
            try (PreparedStatement pstmt = conn.prepareStatement(debitSql)) {
                pstmt.setDouble(1, amount);
                pstmt.setInt(2, fromUserId);
                pstmt.executeUpdate();
            }

            // 4. Credit Receiver
            try (PreparedStatement pstmt = conn.prepareStatement(creditSql)) {
                pstmt.setDouble(1, amount);
                pstmt.setInt(2, receiverId);
                pstmt.executeUpdate();
            }
            
            // 5. Log Transaction
            try (PreparedStatement pstmt = conn.prepareStatement(logTransactionSql)) {
                pstmt.setInt(1, fromUserId);
                pstmt.setInt(2, receiverId);
                pstmt.setDouble(3, amount);
                pstmt.executeUpdate();
            }

            conn.commit(); // Commit Transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public List<Transaction> getUnifiedActivity(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        // UNION query to combine Transactions (Transfers) and Expenses
        String sql = "SELECT id, sender_id, receiver_id, amount, type, timestamp, sender_name, receiver_name FROM (" +
                     "  SELECT t.id, t.sender_id, t.receiver_id, t.amount, t.type, t.timestamp, " +
                     "         s.name as sender_name, r.name as receiver_name " +
                     "  FROM transactions t " +
                     "  JOIN users s ON t.sender_id = s.id " +
                     "  JOIN users r ON t.receiver_id = r.id " +
                     "  WHERE t.sender_id = ? OR t.receiver_id = ? " +
                     "  UNION ALL " +
                     "  SELECT e.id, e.user_id as sender_id, 0 as receiver_id, e.amount, 'EXPENSE' as type, e.date as timestamp, " +
                     "         u.name as sender_name, CONCAT(e.category, ': ', IFNULL(e.description, '')) as receiver_name " +
                     "  FROM expenses e " +
                     "  JOIN users u ON e.user_id = u.id " +
                     "  WHERE e.user_id = ? " +
                     ") AS combined_activity " +
                     "ORDER BY timestamp DESC LIMIT 10";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, userId);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction(
                    rs.getInt("id"),
                    rs.getInt("sender_id"),
                    rs.getInt("receiver_id"),
                    rs.getDouble("amount"),
                    rs.getString("type"),
                    rs.getTimestamp("timestamp")
                );
                t.setSenderName(rs.getString("sender_name"));
                t.setReceiverName(rs.getString("receiver_name"));
                transactions.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
