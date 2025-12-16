package com.finance.test;

import com.finance.dao.FinanceDAO;
import com.finance.dao.UserDAO;
import com.finance.model.User;

public class TestTransaction {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        FinanceDAO financeDAO = new FinanceDAO();

        // Patch DB schema for test environment
        patchDatabase();

        // 1. Get default users
        User admin = userDAO.getUserByEmail("admin@finance.com");
        User user = userDAO.getUserByEmail("user@finance.com");

        // Always recreate users to ensure known state
        System.out.println("Recreating users for test...");
        
        // Define test users
        admin = new User("Test Admin", "admin_test@finance.com", "pass", "ADMIN");
        admin.setBalance(1000.0);
        user = new User("Test User", "user_test@finance.com", "pass", "USER");
        user.setBalance(500.0);
        
        // Delete if exist
        User existingAdmin = userDAO.getUserByEmail(admin.getEmail());
        if (existingAdmin != null) userDAO.deleteUser(existingAdmin.getId());
        User existingUser = userDAO.getUserByEmail(user.getEmail());
        if (existingUser != null) userDAO.deleteUser(existingUser.getId());
        
        System.out.println("DEBUG: Creating Admin with balance: " + admin.getBalance());
        userDAO.createUser(admin);
        userDAO.createUser(user);

        // Re-fetch to get IDs
        admin = userDAO.getUserByEmail("admin_test@finance.com");
        user = userDAO.getUserByEmail("user_test@finance.com");

        System.out.println("Initial Balances:");
        System.out.println("Admin: " + admin.getBalance());
        System.out.println("User: " + user.getBalance());

        double transferAmount = 100.00;
        System.out.println("\nTransferring " + transferAmount + " from Admin to User...");

        boolean success = financeDAO.transferFunds(admin.getId(), user.getEmail(), transferAmount);

        if (success) {
            System.out.println("Transfer Result: SUCCESS");
        } else {
            System.out.println("Transfer Result: FAILED");
        }

        // Verify balances
        User adminAfter = userDAO.getUserByEmail(admin.getEmail());
        User userAfter = userDAO.getUserByEmail(user.getEmail());

        System.out.println("\nFinal Balances:");
        System.out.println("Admin: " + adminAfter.getBalance());
        System.out.println("User: " + userAfter.getBalance());

        if (Math.abs(adminAfter.getBalance() - (admin.getBalance() - transferAmount)) < 0.01 &&
            Math.abs(userAfter.getBalance() - (user.getBalance() + transferAmount)) < 0.01) {
            System.out.println("\nVERIFICATION PASSED: Balances updated correctly.");
        } else {
            System.err.println("\nVERIFICATION FAILED: Balance mismatch.");
        }
        
        // Verify Transaction History
        System.out.println("\nVerifying Transaction History...");
        // 2. Verify Transaction History
        java.util.List<com.finance.model.Transaction> history = financeDAO.getUnifiedActivity(admin.getId());
        boolean recordFound = false;
        for (com.finance.model.Transaction t : history) {
            if (t.getSenderId() == admin.getId() && t.getReceiverId() == user.getId() && Math.abs(t.getAmount() - transferAmount) < 0.01) {
                recordFound = true;
                System.out.println("Found Transaction Log: " + t.getType() + " Rs. " + t.getAmount() + " at " + t.getTimestamp());
                break;
            }
        }
        
        if (recordFound) {
            System.out.println("VERIFICATION PASSED: Transaction logged successfully.");
        } else {
            System.err.println("VERIFICATION FAILED: Transaction log not found.");
        }
    }

    private static void patchDatabase() {
        try (java.sql.Connection conn = com.finance.util.DBConnection.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            try {
                stmt.execute("ALTER TABLE users ADD COLUMN balance DECIMAL(15, 2) DEFAULT 0.00");
                System.out.println("Database patched: balance column added.");
            } catch (Exception e) {
                if (e.getMessage().contains("Duplicate column")) {
                     System.out.println("Database patch info: Column 'balance' already exists.");
                } else {
                     System.out.println("Database patch info: " + e.getMessage());
                }
            }
            
            try {
                stmt.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "sender_id INT NOT NULL," +
                        "receiver_id INT NOT NULL," +
                        "amount DECIMAL(15, 2) NOT NULL," +
                        "type VARCHAR(50) DEFAULT 'TRANSFER'," +
                        "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (sender_id) REFERENCES users(id)," +
                        "FOREIGN KEY (receiver_id) REFERENCES users(id))");
                System.out.println("Database patched: transactions table ensured.");
            } catch (Exception e) {
                 System.out.println("Database patch info (transactions): " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
