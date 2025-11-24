package com.finance.controller;

import com.finance.dao.UserDAO;
import com.finance.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("login".equals(action)) {
            login(req, resp);
        } else if ("register".equals(action)) {
            register(req, resp);
        } else if ("logout".equals(action)) {
            logout(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("logout".equals(action)) {
            logout(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userDAO.authenticate(email, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            
            switch (user.getRole()) {
                case "ADMIN":
                    resp.sendRedirect(req.getContextPath() + "/admin?action=dashboard");
                    break;
                case "ADVISOR":
                    resp.sendRedirect(req.getContextPath() + "/advisor?action=dashboard");
                    break;
                case "USER":
                    resp.sendRedirect(req.getContextPath() + "/user?action=dashboard");
                    break;
                default:
                    resp.sendRedirect(req.getContextPath() + "/index.jsp?error=Unknown role");
            }
        } else {
            req.setAttribute("error", "Invalid email or password");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role"); // In a real app, role might be fixed or approved

        User user = new User(name, email, password, role);
        if (userDAO.createUser(user)) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?message=Registration successful, please login");
        } else {
            req.setAttribute("error", "Registration failed (Email might be taken)");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
