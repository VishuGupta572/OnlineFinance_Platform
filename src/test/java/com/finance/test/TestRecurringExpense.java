package com.finance.test;

import com.finance.dao.FinanceDAO;
import com.finance.dao.UserDAO;
import com.finance.model.RecurringExpense;
import com.finance.model.User;
import com.finance.scheduler.ExpenseScheduler;
import com.finance.util.PasswordUtil;

import java.lang.reflect.Method;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestRecurringExpense {

    @Test
    public void testRecurringExpenseFlow() {
        System.out.println("Running TestRecurringExpense...");
        
        FinanceDAO financeDAO = new FinanceDAO();
        UserDAO userDAO = new UserDAO();
        
        // 1. Setup Test User
        String email = "scheduler_test@example.com";
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            user = new User();
            user.setName("Scheduler Test");
            user.setEmail(email);
            user.setPassword(PasswordUtil.hashPassword("pass123"));
            user.setRole("USER");
            user.setBalance(1000.0);
            userDAO.createUser(user);
            user = userDAO.getUserByEmail(email); // Get ID
        }
        int userId = user.getId();
        System.out.println("Test User ID: " + userId);

        // Ensure table exists (Migration workaround)
        try (java.sql.Connection conn = com.finance.util.DBConnection.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXISTS recurring_expenses (" +
                                 "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                 "user_id INT NOT NULL, " +
                                 "category VARCHAR(50) NOT NULL, " +
                                 "amount DECIMAL(10, 2) NOT NULL, " +
                                 "description VARCHAR(255), " +
                                 "active BOOLEAN DEFAULT TRUE, " +
                                 "FOREIGN KEY (user_id) REFERENCES users(id))";
            stmt.execute(createTable);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add Recurring Expense
        RecurringExpense re = new RecurringExpense(userId, "Utilities", 50.00, "Monthly Internet");
        // We use a unique description or clean up to ensure repeatability?
        // Or just allow multiples for now as we just check insertion return value.
        
        assertTrue(financeDAO.addRecurringExpense(re), "Failed to add recurring expense");
        System.out.println("Recurring Expense added.");

        // 3. Verify it is in DB
        List<RecurringExpense> active = financeDAO.getAllActiveRecurringExpenses();
        boolean found = false;
        for (RecurringExpense r : active) {
            if (r.getUserId() == userId && r.getAmount() == 50.00 && "Monthly Internet".equals(r.getDescription())) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Recurring expense NOT found in DB");
        System.out.println("Verified: Recurring expense found in DB.");

        // 4. Trigger Scheduler Logic Manually
        System.out.println("Simulating Scheduler Run 1...");
        ExpenseScheduler scheduler = new ExpenseScheduler();
        // Inject FinanceDAO
        try {
            java.lang.reflect.Field field = ExpenseScheduler.class.getDeclaredField("financeDAO");
            field.setAccessible(true);
            field.set(scheduler, new FinanceDAO());
            
            Method method = ExpenseScheduler.class.getDeclaredMethod("processRecurringExpenses");
            method.setAccessible(true);
            method.invoke(scheduler, (Object[]) null); // null for no args
        } catch (Exception e) {
            e.printStackTrace();
            fail("Reflection failed: " + e.getMessage());
        }

        // 5. Verify Expense Created
        java.sql.Date today = java.sql.Date.valueOf(LocalDate.now());
        boolean expenseExists = financeDAO.checkIfExpenseExists(userId, 50.00, today, "Monthly Internet (Recurring)");
        assertTrue(expenseExists, "Scheduler did NOT create the expense");
        System.out.println("Success: Scheduler created the expense.");

        // 6. Simulate Scheduler Run 2 (Should skip)
        System.out.println("Simulating Scheduler Run 2...");
        try {
            Method method = ExpenseScheduler.class.getDeclaredMethod("processRecurringExpenses");
            method.setAccessible(true);
            method.invoke(scheduler, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Test Completed.");
    }
}
