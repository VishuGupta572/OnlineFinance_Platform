<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>Register - Finance Platform</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=3">
    </head>

    <body class="auth-page">
        <div class="auth-card">
            <a href="${pageContext.request.contextPath}/" class="back-link">&larr; Back to Home</a>

            <div class="auth-header">
                <h2>Create Account</h2>
                <p>Join us to manage your wealth smartly.</p>
            </div>

            <% if (request.getAttribute("error") !=null) { %>
                <div class="error">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>

                    <form action="auth" method="post">
                        <input type="hidden" name="action" value="register">
                        <div class="form-group">
                            <label>Full Name</label>
                            <input type="text" name="name" placeholder="John Doe" required>
                        </div>
                        <div class="form-group">
                            <label>Email Address</label>
                            <input type="email" name="email" placeholder="john@example.com" required>
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" name="password" required
                                pattern="(?=.*\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,}"
                                title="Must be at least 8 characters and include 1 number, 1 uppercase letter, and 1 special character (@#$%!)">
                        </div>
                        <div class="form-group">
                            <label>I am a</label>
                            <select name="role">
                                <option value="USER">User (Personal Finance)</option>
                                <option value="ADVISOR">Financial Advisor</option>
                            </select>
                        </div>
                        <button type="submit">Create Account</button>
                    </form>

                    <div
                        style="text-align: center; margin-top: 1.5rem; font-size: 0.9rem; color: var(--text-secondary);">
                        Already have an account? <a href="login.jsp" style="font-weight: 600;">Login here</a>
                    </div>
        </div>
    </body>

    </html>