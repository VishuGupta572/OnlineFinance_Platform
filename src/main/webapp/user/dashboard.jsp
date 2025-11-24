<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>User Dashboard</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        </head>

        <body>
            <div class="nav">
                <span>User Dashboard</span>
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
                                        <h3>Expense Overview</h3>
                                        <canvas id="expenseChart" style="max-height: 400px;"></canvas>
                                    </div>

                                    <div class="card">
                                        <h3>Add Expense</h3>
                                        <form action="${pageContext.request.contextPath}/user" method="post">
                                            <input type="hidden" name="action" value="addExpense">
                                            <div class="form-group">
                                                <label>Category:</label>
                                                <select name="category">
                                                    <option>Food</option>
                                                    <option>Transport</option>
                                                    <option>Utilities</option>
                                                    <option>Entertainment</option>
                                                    <option>Other</option>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label>Amount:</label>
                                                <input type="number" step="0.01" name="amount" required>
                                            </div>
                                            <div class="form-group">
                                                <label>Date:</label>
                                                <input type="date" name="date" required>
                                            </div>
                                            <div class="form-group">
                                                <label>Description:</label>
                                                <input type="text" name="description">
                                            </div>
                                            <button type="submit">Add Expense</button>
                                        </form>
                                    </div>

                                    <div class="card">
                                        <h3>Set Budget</h3>
                                        <form action="${pageContext.request.contextPath}/user" method="post">
                                            <input type="hidden" name="action" value="setBudget">
                                            <div class="form-group">
                                                <label>Category:</label>
                                                <select name="category">
                                                    <option>Food</option>
                                                    <option>Transport</option>
                                                    <option>Utilities</option>
                                                    <option>Entertainment</option>
                                                    <option>Other</option>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label>Limit Amount:</label>
                                                <input type="number" step="0.01" name="limitAmount" required>
                                            </div>
                                            <button type="submit">Set Budget</button>
                                        </form>
                                    </div>
                                </div>

                                <h3>My Expenses</h3>
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Date</th>
                                            <th>Category</th>
                                            <th>Amount</th>
                                            <th>Description</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="e" items="${expenses}">
                                            <tr>
                                                <td>${e.date}</td>
                                                <td>${e.category}</td>
                                                <td>${e.amount}</td>
                                                <td>${e.description}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <h3>My Budgets</h3>
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Category</th>
                                            <th>Limit</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="b" items="${budgets}">
                                            <tr>
                                                <td>${b.category}</td>
                                                <td>${b.limitAmount}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <h3>Financial Advice Received</h3>
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Date</th>
                                            <th>Message</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="a" items="${adviceList}">
                                            <tr>
                                                <td>${a.createdAt}</td>
                                                <td>${a.message}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
            </div>
            </div>

            <script>
                const ctx = document.getElementById('expenseChart').getContext('2d');
                new Chart(ctx, {
                    type: 'pie',
                    data: {
                        labels: ${ chartLabels },
                    datasets: [{
                        label: 'Expenses by Category',
                        data: ${ chartData },
                    backgroundColor: [
                        '#4f46e5', '#10b981', '#f59e0b', '#ef4444', '#8b5cf6', '#ec4899'
                    ]
                }]
            },
                    options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
        });
            </script>
        </body>

        </html>