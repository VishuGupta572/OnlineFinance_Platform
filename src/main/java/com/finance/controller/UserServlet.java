package com.finance.controller;

import com.finance.dao.AdviceDAO;
import com.finance.dao.FeedbackDAO;
import com.finance.dao.FinanceDAO;
import com.finance.dao.UserDAO;
import com.finance.model.Advice;
import com.finance.model.Budget;
import com.finance.model.Expense;
import com.finance.model.Feedback;
import com.finance.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private FinanceDAO financeDAO;
    private AdviceDAO adviceDAO;
    private UserDAO userDAO;
    private FeedbackDAO feedbackDAO;

    @Override
    public void init() throws ServletException {
        financeDAO = new FinanceDAO();
        adviceDAO = new AdviceDAO();
        userDAO = new UserDAO();
        feedbackDAO = new FeedbackDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !"USER".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "dashboard";

        switch (action) {
            case "dashboard":
                List<Expense> expenses = financeDAO.getExpensesByUserId(user.getId());
                List<Budget> budgets = financeDAO.getBudgetsByUserId(user.getId());
                List<Advice> adviceList = adviceDAO.getAdviceByUserId(user.getId());
                List<String> categories = financeDAO.getExpenseCategories(user.getId());
                
                // Prepare data for charts
                StringBuilder categoryLabels = new StringBuilder("[");
                StringBuilder categoryData = new StringBuilder("[");
                for (int i = 0; i < categories.size(); i++) {
                    String cat = categories.get(i);
                    double total = financeDAO.getTotalExpensesByCategory(user.getId(), cat);
                    categoryLabels.append("'").append(cat).append("'");
                    categoryData.append(total);
                    if (i < categories.size() - 1) {
                        categoryLabels.append(",");
                        categoryData.append(",");
                    }
                }
                categoryLabels.append("]");
                categoryData.append("]");

                req.setAttribute("expenses", expenses);
                req.setAttribute("budgets", budgets);
                req.setAttribute("adviceList", adviceList);
                req.setAttribute("chartLabels", categoryLabels.toString());
                req.setAttribute("chartData", categoryData.toString());
                
                req.getRequestDispatcher("user/dashboard.jsp").forward(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/user?action=dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !"USER".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String action = req.getParameter("action");
        if ("addExpense".equals(action)) {
            addExpense(req, resp, user);
        } else if ("setBudget".equals(action)) {
            setBudget(req, resp, user);
        } else if ("updateProfile".equals(action)) {
            updateProfile(req, resp, user);
        } else if ("submitFeedback".equals(action)) {
            submitFeedback(req, resp, user);
        }
    }

    private void addExpense(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        String category = req.getParameter("category");
        double amount = Double.parseDouble(req.getParameter("amount"));
        Date date = Date.valueOf(req.getParameter("date"));
        String description = req.getParameter("description");

        Expense expense = new Expense(user.getId(), category, amount, date, description);
        if (financeDAO.addExpense(expense)) {
            resp.sendRedirect(req.getContextPath() + "/user?action=dashboard&message=Expense added successfully");
        } else {
            resp.sendRedirect(req.getContextPath() + "/user?action=dashboard&error=Failed to add expense");
        }
    }

    private void setBudget(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        String category = req.getParameter("category");
        double limitAmount = Double.parseDouble(req.getParameter("limitAmount"));

        Budget budget = new Budget(user.getId(), category, limitAmount);
        if (financeDAO.addBudget(budget)) {
            resp.sendRedirect(req.getContextPath() + "/user?action=dashboard&message=Budget set successfully");
        } else {
            resp.sendRedirect(req.getContextPath() + "/user?action=dashboard&error=Failed to set budget");
        }
    }

    private void updateProfile(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        
        user.setName(name);
        user.setEmail(email);
        
        if (userDAO.updateUser(user)) {
            req.getSession().setAttribute("user", user); // Update session
            resp.sendRedirect(req.getContextPath() + "/user?action=dashboard&message=Profile updated successfully");
        } else {
            resp.sendRedirect(req.getContextPath() + "/user?action=dashboard&error=Failed to update profile");
        }
    }

    private void submitFeedback(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        String message = req.getParameter("message");
        Feedback feedback = new Feedback(user.getId(), message);
        
        if (feedbackDAO.submitFeedback(feedback)) {
            resp.sendRedirect(req.getContextPath() + "/user?action=dashboard&message=Feedback submitted successfully");
        } else {
            resp.sendRedirect(req.getContextPath() + "/user?action=dashboard&error=Failed to submit feedback");
        }
    }
}
