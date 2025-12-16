<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>Login - Finance Platform</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=3">
    </head>

    <body class="auth-page">
        <div class="auth-card">
            <a href="${pageContext.request.contextPath}/" class="back-link">&larr; Back to Home</a>

            <div class="auth-header">
                <h2>Welcome Back</h2>
                <p>Login to access your dashboard.</p>
            </div>

            <% if (request.getAttribute("error") !=null) { %>
                <div class="error">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>
                    <% if (request.getParameter("message") !=null) { %>
                        <div class="message">
                            <%= request.getParameter("message") %>
                        </div>
                        <% } %>

                            <form action="auth" method="post">
                                <input type="hidden" name="action" value="login">
                                <div class="form-group">
                                    <label>Email Address</label>
                                    <input type="email" name="email" placeholder="Enter your email" required>
                                </div>
                                <div class="form-group">
                                    <label>Password</label>
                                    <input type="password" name="password" placeholder="Enter your password" required>
                                </div>
                                <button type="submit">Sign In</button>
                            </form>

                            <div
                                style="text-align: center; margin-top: 1.5rem; font-size: 0.9rem; color: var(--text-secondary);">
                                New to FinancePlatform? <a href="register.jsp" style="font-weight: 600;">Create
                                    account</a>
                            </div>
        </div>
    </body>

    </html>