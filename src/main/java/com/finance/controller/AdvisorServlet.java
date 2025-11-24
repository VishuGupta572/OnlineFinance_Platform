package com.finance.controller;

import com.finance.dao.AdviceDAO;
import com.finance.dao.UserDAO;
import com.finance.model.Advice;
import com.finance.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/advisor")
public class AdvisorServlet extends HttpServlet {
    private UserDAO userDAO;
    private AdviceDAO adviceDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        adviceDAO = new AdviceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !"ADVISOR".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "dashboard";

        switch (action) {
            case "dashboard":
                List<User> users = userDAO.getAllUsers(); // In real app, filter by assigned users
                List<Advice> history = adviceDAO.getAdviceByAdvisorId(user.getId());
                
                // Simple stats
                int adviceCount = history.size();
                long distinctUsers = history.stream().map(Advice::getUserId).distinct().count();
                
                req.setAttribute("users", users);
                req.setAttribute("history", history);
                req.setAttribute("adviceCount", adviceCount);
                req.setAttribute("distinctUsers", distinctUsers);
                
                req.getRequestDispatcher("advisor/dashboard.jsp").forward(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/advisor?action=dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !"ADVISOR".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String action = req.getParameter("action");
        if ("sendAdvice".equals(action)) {
            sendAdvice(req, resp, user);
        }
    }

    private void sendAdvice(HttpServletRequest req, HttpServletResponse resp, User advisor) throws IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        String message = req.getParameter("message");

        Advice advice = new Advice(advisor.getId(), userId, message);
        if (adviceDAO.sendAdvice(advice)) {
            resp.sendRedirect(req.getContextPath() + "/advisor?action=dashboard&message=Advice sent successfully");
        } else {
            resp.sendRedirect(req.getContextPath() + "/advisor?action=dashboard&error=Failed to send advice");
        }
    }
}
