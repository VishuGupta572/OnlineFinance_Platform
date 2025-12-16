package com.finance.test;

import com.finance.model.Expense;
import com.finance.model.Transaction;
import com.finance.model.User;
import com.finance.service.PdfService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestPdfGeneration {
    public static void main(String[] args) {
        System.out.println("Testing PDF Generation...");

        try {
            // 1. Mock Data
            User user = new User("Test User", "test@finance.com", "pass", "USER");
            
            List<Transaction> transactions = new ArrayList<>();
            Transaction t = new Transaction();
            t.setAmount(100.0);
            t.setType("TRANSFER");
            t.setSenderName("Admin");
            t.setReceiverName("Test User");
            t.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
            transactions.add(t);

            List<Expense> expenses = new ArrayList<>();
            expenses.add(new Expense(1, 1, "Food", 50.0, new java.sql.Date(System.currentTimeMillis()), "Lunch"));

            // 2. Generate PDF
            PdfService service = new PdfService();
            ByteArrayOutputStream out = service.generateStatement(user, transactions, expenses);

            // 3. Verify Output
            if (out.size() > 0) {
                System.out.println("PASS: PDF generated successfully. Size: " + out.size() + " bytes.");
            } else {
                System.err.println("FAIL: PDF output is empty.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("FAIL: Exception during PDF generation.");
        }
    }
}
