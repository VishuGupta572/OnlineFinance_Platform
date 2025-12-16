package com.finance.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class DBInitializer {
    public static void initialize() {
        String schemaPath = "database/schema.sql";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             BufferedReader br = new BufferedReader(new FileReader(schemaPath))) {
            
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("--")) continue; // Skip comments
                sql.append(line).append("\n");
                if (line.trim().endsWith(";")) {
                    stmt.execute(sql.toString());
                    sql.setLength(0);
                }
            }
            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initialize();
    }
}
