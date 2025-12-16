package com.finance.controller;

import com.finance.dao.FinanceDAO;
import com.finance.model.Expense;
import com.finance.model.Transaction;
import com.finance.model.User;
import com.finance.service.PdfService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

    private FinanceDAO financeDAO;
    private PdfService pdfService;

    @Override
    public void init() throws ServletException {
        financeDAO = new FinanceDAO();
        pdfService = new PdfService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("index.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String type = req.getParameter("type");

        if ("pdf".equalsIgnoreCase(type)) {
            // Fetch data
            List<Transaction> transactions = financeDAO.getUnifiedActivity(user.getId());
            List<Expense> expenses = financeDAO.getExpensesByUserId(user.getId());

            // Generate PDF
            ByteArrayOutputStream pdfStream = pdfService.generateStatement(user, transactions, expenses);

            // Set Headers
            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=Monthly_Statement.pdf");
            resp.setContentLength(pdfStream.size());

            // Write Output
            try (OutputStream out = resp.getOutputStream()) {
                pdfStream.writeTo(out);
                out.flush();
            }
        } else {
             resp.getWriter().write("Invalid download type requested.");
        }
    }
}
