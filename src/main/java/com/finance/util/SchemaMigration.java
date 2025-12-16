package com.finance.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaMigration {
    public static void main(String[] args) {
        System.out.println("Starting database migration...");
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
             
            // Attempt to add the column. If it exists, it might fail or we can catch it.
            // HSQLDB support: ALTER TABLE users ADD COLUMN profile_picture BLOB
            // MySQL support: ALTER TABLE users ADD COLUMN profile_picture MEDIUMBLOB
            
            // We'll try the MySQL syntax first as per schema.sql
            // Create savings_goals table
            String sql = "CREATE TABLE IF NOT EXISTS savings_goals (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "user_id INT, " +
                         "name VARCHAR(100) NOT NULL, " +
                         "target_amount DECIMAL(10,2) NOT NULL, " +
                         "current_amount DECIMAL(10,2) DEFAULT 0.00, " +
                         "deadline DATE, " +
                         "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                         "FOREIGN KEY (user_id) REFERENCES users(id))";
            stmt.executeUpdate(sql);
            System.out.println("Migration successful: Added savings_goals table.");
            
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate column") || e.getMessage().contains("exists")) {
                System.out.println("Column profile_picture already exists.");
            } else {
                System.err.println("Migration failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
