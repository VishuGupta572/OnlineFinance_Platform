<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>Login - Finance Platform</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <div class="container">
            <h2>Login</h2>
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
                                    <label>Email:</label>
                                    <input type="email" name="email" required>
                                </div>
                                <div class="form-group">
                                    <label>Password:</label>
                                    <input type="password" name="password" required>
                                </div>
                                <button type="submit">Login</button>
                            </form>
                            <p>Don't have an account? <a href="register.jsp">Register here</a></p>
        </div>
    </body>

    </html>