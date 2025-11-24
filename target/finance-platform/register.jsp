<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>Register - Finance Platform</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <div class="container">
            <h2>Register</h2>
            <% if (request.getAttribute("error") !=null) { %>
                <div class="error">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>
                    <form action="auth" method="post">
                        <input type="hidden" name="action" value="register">
                        <div class="form-group">
                            <label>Name:</label>
                            <input type="text" name="name" required>
                        </div>
                        <div class="form-group">
                            <label>Email:</label>
                            <input type="email" name="email" required>
                        </div>
                        <div class="form-group">
                            <label>Password:</label>
                            <input type="password" name="password" required>
                        </div>
                        <div class="form-group">
                            <label>Role:</label>
                            <select name="role">
                                <option value="USER">User</option>
                                <option value="ADVISOR">Financial Advisor</option>
                                <!-- Admin registration usually restricted, but allowed here for demo -->
                                <option value="ADMIN">Admin</option>
                            </select>
                        </div>
                        <button type="submit">Register</button>
                    </form>
                    <p>Already have an account? <a href="index.jsp">Login here</a></p>
        </div>
    </body>

    </html>