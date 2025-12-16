<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Admin Dashboard</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        </head>

        <body>
            <div class="nav">
                <span>Admin Dashboard</span>
                <span id="currentDateTime"
                    style="font-size: 0.9em; color: var(--text-secondary); font-weight: 500;"></span>
                <div>
                    <a href="${pageContext.request.contextPath}/auth?action=logout">Logout</a>
                </div>
            </div>
            <script>
                function updateTime() {
                    const now = new Date();
                    document.getElementById('currentDateTime').textContent = now.toLocaleString();
                }
                setInterval(updateTime, 1000);
                updateTime();
            </script>

            <div class="dashboard-container">
                <% if (request.getAttribute("message") !=null) { %>
                    <div class="message">
                        <%= request.getAttribute("message") %>
                    </div>
                    <% } %>
                        <% if (request.getParameter("message") !=null) { %>
                            <div class="message">
                                <%= request.getParameter("message") %>
                            </div>
                            <% } %>

                                <h2 id="greeting" style="margin-bottom: 2rem;">Welcome, ${sessionScope.user.name}</h2>
                                <script>
                                    const hour = new Date().getHours();
                                    let greeting = 'Good Morning';
                                    if (hour >= 12 && hour < 17) greeting = 'Good Afternoon';
                                    else if (hour >= 17) greeting = 'Good Evening';
                                    document.getElementById('greeting').textContent = greeting + ', ${sessionScope.user.name}!';
                                </script>

                                <div class="grid-2">
                                    <div class="card">
                                        <h3>Financial Overview</h3>
                                        <canvas id="financeChart"></canvas>
                                        <p style="text-align: center; margin-top: 10px;">Total Platform Expenses:
                                            <strong>₹${totalPlatformExpenses}</strong>
                                        </p>
                                    </div>
                                    <div class="card">
                                        <h3>Platform Stats</h3>
                                        <p>Total Users: <strong>${totalUsers}</strong></p>
                                        <p>Active Budgets: <strong>N/A</strong></p> <!-- Placeholder -->
                                    </div>
                                </div>

                                <div class="card">
                                    <h3>User Management</h3>
                                    <table>
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Name</th>
                                                <th>Email</th>
                                                <th>Role</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="u" items="${users}">
                                                <tr>
                                                    <td>${u.id}</td>
                                                    <td>${u.name}</td>
                                                    <td>${u.email}</td>
                                                    <td>${u.role}</td>
                                                    <td>
                                                        <c:if test="${u.role != 'ADMIN'}">
                                                            <a href="${pageContext.request.contextPath}/admin?action=deleteUser&id=${u.id}"
                                                                onclick="return confirm('Are you sure?')">Delete</a>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                                <div class="grid-2">
                                    <div class="card">
                                        <h3>System Settings</h3>
                                        <form action="${pageContext.request.contextPath}/admin" method="post">
                                            <input type="hidden" name="action" value="updateSettings">
                                            <div class="form-group">
                                                <label>Site Name:</label>
                                                <input type="text" name="siteName" value="Finance Platform" required>
                                            </div>
                                            <button type="submit">Update Settings</button>
                                        </form>
                                    </div>

                                    <div class="card">
                                        <h3>Financial Data Security</h3>
                                        <form action="${pageContext.request.contextPath}/admin" method="post">
                                            <input type="hidden" name="action" value="updateSecurity">
                                            <div class="form-group">
                                                <label>Encryption Level:</label>
                                                <select name="encryption">
                                                    <option>Standard</option>
                                                    <option>High</option>
                                                </select>
                                            </div>
                                            <button type="submit">Update Security</button>
                                        </form>
                                    </div>
                                </div>
            </div>
        </body>

        </html>