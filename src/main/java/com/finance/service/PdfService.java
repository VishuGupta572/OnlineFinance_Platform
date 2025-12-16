package com.finance.service;

import com.finance.model.Expense;
import com.finance.model.Transaction;
import com.finance.model.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

public class PdfService {

    public ByteArrayOutputStream generateStatement(User user, List<Transaction> transactions, List<Expense> expenses) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // 1. Header
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph header = new Paragraph("Finance Platform - Monthly Statement", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(Chunk.NEWLINE);

            // 2. User Info
            Font subHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            document.add(new Paragraph("User Details:", subHeaderFont));
            document.add(new Paragraph("Name: " + user.getName()));
            document.add(new Paragraph("Email: " + user.getEmail()));
            document.add(new Paragraph("Date: " + new Date().toString()));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // 3. Transactions Table
            document.add(new Paragraph("Recent Transactions:", subHeaderFont));
            document.add(Chunk.NEWLINE);

            PdfPTable transactionTable = new PdfPTable(4); // Date, Type, Counterparty, Amount
            transactionTable.setWidthPercentage(100);
            
            addTableHeader(transactionTable, "Date", "Type", "Details", "Amount");

            for (Transaction t : transactions) {
                transactionTable.addCell(t.getTimestamp().toString());
                transactionTable.addCell(t.getType());
                
                String details = "From: " + t.getSenderName() + " To: " + t.getReceiverName();
                transactionTable.addCell(details);
                
                String amountPrefix = (t.getReceiverId() == user.getId()) ? "+" : "-";
                transactionTable.addCell(amountPrefix + "Rs. " + t.getAmount());
            }
            document.add(transactionTable);
            document.add(Chunk.NEWLINE);

            // 4. Expenses Table
            document.add(new Paragraph("My Expenses:", subHeaderFont));
            document.add(Chunk.NEWLINE);

            PdfPTable expenseTable = new PdfPTable(4);
            expenseTable.setWidthPercentage(100);
            addTableHeader(expenseTable, "Date", "Category", "Description", "Amount");

            for (Expense e : expenses) {
                expenseTable.addCell(e.getDate().toString());
                expenseTable.addCell(e.getCategory());
                expenseTable.addCell(e.getDescription());
                expenseTable.addCell("Rs. " + e.getAmount());
            }
            document.add(expenseTable);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return out;
    }

    private void addTableHeader(PdfPTable table, String... headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setPhrase(new Phrase(header));
            table.addCell(headerCell);
        }
    }
}
