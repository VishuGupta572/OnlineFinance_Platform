package com.finance.test;

import com.finance.dao.InvestmentDAO;
import com.finance.dao.UserDAO;
import com.finance.model.Investment;
import com.finance.model.User;
import com.finance.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestInvestment {

    @Test
    public void testInvestmentFlow() {
        System.out.println("Running TestInvestment...");
        
        UserDAO userDAO = new UserDAO();
        InvestmentDAO investmentDAO = new InvestmentDAO();
        
        // 1. Setup Test User
        String email = "investor_test@example.com";
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            user = new User();
            user.setName("Investor Test");
            user.setEmail(email);
            user.setPassword(PasswordUtil.hashPassword("pass123"));
            user.setRole("USER");
            user.setBalance(50000.0);
            userDAO.createUser(user);
            user = userDAO.getUserByEmail(email);
        }
        int userId = user.getId();
        System.out.println("Test User ID: " + userId);

        // Ensure table exists 
        try (java.sql.Connection conn = com.finance.util.DBConnection.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXISTS investments (" +
                                 "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                 "user_id INT NOT NULL, " +
                                 "symbol VARCHAR(20) NOT NULL, " +
                                 "quantity DECIMAL(18, 8) NOT NULL, " +
                                 "avg_buy_price DECIMAL(18, 8) NOT NULL, " +
                                 "FOREIGN KEY (user_id) REFERENCES users(id))";
            stmt.execute(createTable);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Buy Investment (New Position)
        // Buy 0.1 BTC at 30000
        boolean bought = investmentDAO.buyInvestment(userId, "bitcoin", 0.1, 30000.0);
        assertTrue(bought, "Failed to buy initial investment");
        
        List<Investment> portfolio = investmentDAO.getInvestmentsByUserId(userId);
        assertFalse(portfolio.isEmpty(), "Portfolio should not be empty");
        
        Investment btc = portfolio.stream().filter(i -> "bitcoin".equals(i.getSymbol())).findFirst().orElse(null);
        assertNotNull(btc, "Should have bitcoin position");
        assertEquals(0.1, btc.getQuantity(), 0.0001);
        assertEquals(30000.0, btc.getAvgBuyPrice(), 0.0001);

        // 3. Buy More (DCA)
        // Buy 0.1 BTC at 40000
        // Total Qty: 0.2
        // Total Cost: 3000 + 4000 = 7000
        // Avg Price: 7000 / 0.2 = 35000
        bought = investmentDAO.buyInvestment(userId, "bitcoin", 0.1, 40000.0);
        assertTrue(bought, "Failed to buy second investment");
        
        portfolio = investmentDAO.getInvestmentsByUserId(userId);
        btc = portfolio.stream().filter(i -> "bitcoin".equals(i.getSymbol())).findFirst().orElse(null);
        
        assertNotNull(btc);
        assertEquals(0.2, btc.getQuantity(), 0.0001);
        assertEquals(35000.0, btc.getAvgBuyPrice(), 0.0001);
        
        System.out.println("Verified: Investment average cost calculation works.");
    }
}
