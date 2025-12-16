package com.finance.util;

import com.finance.dao.FinanceDAO;
import com.finance.model.Transaction;
import java.util.List;

public class DebugTransactions {
    public static void main(String[] args) {
        System.out.println("Starting DebugTransactions...");
        FinanceDAO dao = new FinanceDAO();
        
        // Test for User 12
        System.out.println("Fetching activity for User 12:");
        List<Transaction> txs12 = dao.getUnifiedActivity(12);
        System.out.println("Count: " + txs12.size());
        for (Transaction t : txs12) {
            System.out.println(" - " + t.getType() + ": " + t.getAmount() + " | " + t.getReceiverName() + " | " + t.getTimestamp());
        }

        // Test for User 4
        System.out.println("\nFetching activity for User 4:");
        List<Transaction> txs4 = dao.getUnifiedActivity(4);
        System.out.println("Count: " + txs4.size());
        for (Transaction t : txs4) {
            System.out.println(" - " + t.getType() + ": " + t.getAmount() + " | " + t.getReceiverName() + " | " + t.getTimestamp());
        }
    }
}
