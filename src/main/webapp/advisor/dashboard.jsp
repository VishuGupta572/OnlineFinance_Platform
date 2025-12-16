<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Advisor Dashboard</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        </head>

        <body>
            <div class="nav">
                <span>Advisor Dashboard</span>
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
                                    <div class="grid-2">
                                        <div class="card">
                                            <h3>Interaction Statistics</h3>
                                            <canvas id="interactionChart"></canvas>
                                        </div>
                                        <div class="card">
                                            <h3>Overview</h3>
                                            <p>Total Advice Given: <strong>${adviceCount}</strong></p>
                                            <p>Distinct Users Helped: <strong>${distinctUsers}</strong></p>
                                        </div>
                                    </div>

                                    <div class="grid-2">
                                        <div class="card">
                                            <h3>Provide Financial Advice</h3>
                                            <form action="${pageContext.request.contextPath}/advisor" method="post">
                                                <input type="hidden" name="action" value="sendAdvice">
                                                <div class="form-group">
                                                    <label>Select User:</label>
                                                    <select name="userId">
                                                        <c:forEach var="u" items="${users}">
                                                            <c:if test="${u.role == 'USER'}">
                                                                <option value="${u.id}">${u.name} (${u.email})</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <label>Advice / Message:</label>
                                                    <textarea name="message" rows="4" style="width:100%"
                                                        required></textarea>
                                                </div>
                                                <button type="submit">Send Advice</button>
                                            </form>
                                        </div>
                                    </div>

                                    <div class="card">
                                        <h3>Advice History</h3>
                                        <table>
                                            <thead>
                                                <tr>
                                                    <th>Date</th>
                                                    <th>User ID</th>
                                                    <th>Message</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="a" items="${history}">
                                                    <tr>
                                                        <td>${a.createdAt}</td>
                                                        <td>${a.userId}</td>
                                                        <td>${a.message}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
            </div>

            <script>
                const ctx = document.getElementById('interactionChart').getContext('2d');
                new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: ['Advice Given', 'Users Helped'],
                        datasets: [{
                            data: [${ adviceCount }, ${ distinctUsers }],
                            backgroundColor: ['#4f46e5', '#10b981']
                        }]
                    },
                    options: {
                        responsive: true
                    }
                });
            </script>
        </body>

        </html>