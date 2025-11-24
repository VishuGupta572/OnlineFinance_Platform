package com.finance.controller;

import com.finance.dao.FeedbackDAO;
import com.finance.dao.FinanceDAO;
import com.finance.dao.UserDAO;
import com.finance.model.Feedback;
import com.finance.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private UserDAO userDAO;
    private FinanceDAO financeDAO;
    private FeedbackDAO feedbackDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        financeDAO = new FinanceDAO();
        feedbackDAO = new FeedbackDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "dashboard";

        switch (action) {
            case "dashboard":
                List<User> users = userDAO.getAllUsers();
                List<Feedback> feedbackList = feedbackDAO.getAllFeedback();
                double totalPlatformExpenses = financeDAO.getTotalPlatformExpenses();
                
                req.setAttribute("users", users);
                req.setAttribute("feedbackList", feedbackList);
                req.setAttribute("totalPlatformExpenses", totalPlatformExpenses);
                req.setAttribute("totalUsers", users.size());
                
                req.getRequestDispatcher("admin/dashboard.jsp").forward(req, resp);
                break;
            case "deleteUser":
                deleteUser(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/admin?action=dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String action = req.getParameter("action");
        if ("updateSettings".equals(action)) {
            // Mock settings update
            req.setAttribute("message", "System settings updated successfully.");
            doGet(req, resp);
        } else if ("updateSecurity".equals(action)) {
            // Mock security update
            req.setAttribute("message", "Security settings updated successfully.");
            doGet(req, resp);
        }
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        if (userDAO.deleteUser(id)) {
            resp.sendRedirect(req.getContextPath() + "/admin?action=dashboard&message=User deleted successfully");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin?action=dashboard&error=Failed to delete user");
        }
    }
}
