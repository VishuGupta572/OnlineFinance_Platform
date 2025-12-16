<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Dashboard - Finance Platform</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=5">
            <!-- Font Awesome for Icons -->
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
            <style>
                /* Additional custom dashboard tweaks can go here if needed */
                .chart-container {
                    position: relative;
                    height: 300px;
                    width: 100%;
                }
            </style>
        </head>

        <body>
            <div class="dashboard-layout">
                <!-- Top Navigation Bar -->
                <header class="top-bar">
                    <div class="logo-small"><i class="fa-solid fa-wallet"></i> FinancePlatform</div>
                    <div class="user-menu">
                        <div class="user-info">
                            <span class="user-name">${sessionScope.user.name}</span>
                            <span class="user-role">Personal Account</span>
                        </div>
                        <!-- Avatar -->
                        <div class="user-avatar-container" style="position: relative; display: inline-block;">
                            <c:choose>
                                <c:when test="${not empty sessionScope.user.profilePictureBase64}">
                                    <img src="data:image/png;base64,${sessionScope.user.profilePictureBase64}"
                                        style="width: 40px; height: 40px; border-radius: 50%; object-fit: cover; border: 2px solid #e0e7ff;"
                                        onclick="document.getElementById('profileUpload').click();"
                                        title="Click to change photo" alt="Profile">
                                </c:when>
                                <c:otherwise>
                                    <div style="width: 40px; height: 40px; background: #e0e7ff; color: #4f46e5; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold; cursor: pointer;"
                                        onclick="document.getElementById('profileUpload').click();"
                                        title="Click to upload photo">
                                        ${sessionScope.user.name.substring(0,1)}
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <!-- Hidden Form for Upload -->
                            <form action="${pageContext.request.contextPath}/user" method="post"
                                enctype="multipart/form-data" style="display: none;">
                                <input type="hidden" name="action" value="uploadProfilePicture">
                                <input type="file" id="profileUpload" name="profilePicture" accept="image/*"
                                    onchange="this.form.submit()">
                            </form>
                        </div>

                        <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn-logout"><i
                                class="fa-solid fa-sign-out-alt"></i> Logout</a>
                    </div>
                </header>

                <!-- Main Content -->
                <main class="dashboard-content">

                    <c:if test="${not empty requestScope.message}">
                        <div class="message"><i class="fa-solid fa-check-circle"></i> ${requestScope.message}</div>
                    </c:if>
                    <c:if test="${not empty param.message}">
                        <div class="message"><i class="fa-solid fa-check-circle"></i> ${param.message}</div>
                    </c:if>

                    <!-- Welcome & Balance Section -->
                    <div class="welcome-section">
                        <div
                            style="display: flex; justify-content: space-between; align-items: flex-end; flex-wrap: wrap; gap: 20px;">
                            <div>
                                <h1 style="font-size: 2rem; margin-bottom: 0.5rem;">Good <span
                                        id="timeGreeting">Day</span>, ${sessionScope.user.name}</h1>
                                <p style="color: var(--text-secondary);">Here's your financial overview.</p>
                            </div>

                            <div class="balance-card">
                                <div class="balance-label">Total Balance</div>
                                <div class="balance-amount">$${sessionScope.user.balance}</div>
                                <div style="margin-top: 1rem; font-size: 0.9rem; opacity: 0.9;">
                                    <a href="${pageContext.request.contextPath}/user?action=portfolio"
                                        style="color: white; text-decoration: underline;"><i
                                            class="fa-brands fa-bitcoin"></i> View Crypto Portfolio
                                        &rarr;</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Features Grid -->
                    <div class="dashboard-grid">

                        <!-- Quick Actions / Forms Wrapper -->
                        <div style="display: grid; gap: 2rem;">
                            <!-- Add Expense -->
                            <div class="card">
                                <h3><i class="fa-solid fa-circle-plus" style="color: var(--primary-color);"></i> Add
                                    Expense</h3>
                                <form action="${pageContext.request.contextPath}/user" method="post">
                                    <input type="hidden" name="action" value="addExpense">
                                    <div class="form-group">
                                        <label>Category</label>
                                        <select name="category">
                                            <option>Food</option>
                                            <option>Transport</option>
                                            <option>Utilities</option>
                                            <option>Entertainment</option>
                                            <option>Other</option>
                                        </select>
                                    </div>
                                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1rem;">
                                        <div class="form-group">
                                            <label>Amount</label>
                                            <input type="number" step="0.01" name="amount" placeholder="0.00" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Date</label>
                                            <input type="date" name="date" required>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>Description</label>
                                        <input type="text" name="description" placeholder="Lunch, Taxi, etc.">
                                    </div>
                                    <button type="submit"><i class="fa-solid fa-plus"></i> Add Expense</button>
                                </form>
                            </div>

                            <!-- Transfer Funds -->
                            <div class="card">
                                <h3><i class="fa-solid fa-money-bill-transfer" style="color: var(--primary-color);"></i>
                                    Transfer Funds</h3>
                                <form action="${pageContext.request.contextPath}/user" method="post">
                                    <input type="hidden" name="action" value="transferFunds">
                                    <div class="form-group">
                                        <label>Recipient Email</label>
                                        <input type="email" name="toEmail" required placeholder="friend@example.com">
                                    </div>
                                    <div class="form-group">
                                        <label>Amount</label>
                                        <input type="number" step="0.01" name="amount" placeholder="0.00" required>
                                    </div>
                                    <button type="submit"><i class="fa-solid fa-paper-plane"></i> Send Money</button>
                                </form>
                            </div>
                        </div>

                        <!-- Charts & Stats -->
                        <div style="display: grid; gap: 2rem;">
                            <div class="card">
                                <h3><i class="fa-solid fa-chart-pie" style="color: var(--primary-color);"></i> Expense
                                    Analysis</h3>
                                <div class="chart-container">
                                    <canvas id="expenseChart"></canvas>
                                </div>
                            </div>

                            <div class="card">
                                <h3><i class="fa-solid fa-calendar-alt" style="color: var(--primary-color);"></i>
                                    Recurring Bills</h3>
                                <p style="color: var(--text-secondary); margin-bottom: 1rem; font-size: 0.9rem;">Setup
                                    automatic payments.</p>
                                <form action="user?action=addRecurringExpense" method="post"
                                    style="display: grid; gap: 10px;">
                                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
                                        <select name="category">
                                            <option value="Rent">Rent</option>
                                            <option value="Utilities">Utilities</option>
                                            <option value="Internet">Internet</option>
                                        </select>
                                        <input type="number" step="0.01" name="amount" placeholder="Amount" required>
                                    </div>
                                    <input type="text" name="description" placeholder="Description (e.g. Monthly Rent)"
                                        required>
                                    <button type="submit" style="background: var(--secondary-color);"><i
                                            class="fa-solid fa-clock"></i> Setup
                                        Recurring</button>
                                </form>
                            </div>
                        </div>

                        <!-- Tables Section (Full Width if needed, or part of grid) -->
                        <div style="grid-column: 1 / -1;">
                            <div class="card">
                                <div
                                    style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                                    <h3><i class="fa-solid fa-list-ul" style="color: var(--primary-color);"></i> Recent
                                        Activity</h3>
                                    <a href="${pageContext.request.contextPath}/download?type=pdf"
                                        style="font-size: 0.9rem; border: 1px solid var(--primary-color); padding: 5px 10px; border-radius: 5px;"><i
                                            class="fa-solid fa-file-pdf"></i> Download PDF</a>
                                </div>
                                <div class="table-container">
                                    <table>
                                        <thead>
                                            <tr>
                                                <th>Date</th>
                                                <th>Type</th>
                                                <th>Details</th>
                                                <th style="text-align: right;">Amount</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="t" items="${transactions}" end="9">
                                                <tr>
                                                    <td>${t.timestamp}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${t.type == 'TRANSFER'}">
                                                                <span class="badge badge-green">TRANSFER</span>
                                                            </c:when>
                                                            <c:when test="${t.type == 'EXPENSE'}">
                                                                <span class="badge badge-red"
                                                                    style="background: #fef3c7; color: #d97706;">EXPENSE</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge badge-red">${t.type}</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${t.type == 'TRANSFER'}">
                                                                ${t.senderName} &rarr; ${t.receiverName}
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${t.receiverName}
                                                                <!-- Contains "Category: Description" -->
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td style="text-align: right;"
                                                        class="${t.type == 'TRANSFER' && t.receiverId == sessionScope.user.id ? 'text-green' : 'text-red'}">
                                                        <c:choose>
                                                            <c:when
                                                                test="${t.type == 'TRANSFER' && t.receiverId == sessionScope.user.id}">
                                                                +${t.amount}
                                                            </c:when>
                                                            <c:otherwise>
                                                                -${t.amount}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <c:if test="${empty transactions}">
                                                <tr>
                                                    <td colspan="4"
                                                        style="text-align:center; color: var(--text-secondary);">No
                                                        recent transactions</td>
                                                </tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                    </div> <!-- End Grid -->
                </main>
            </div>

            <script>
                // Time Greeting
                const hour = new Date().getHours();
                let greeting = 'Morning';
                if (hour >= 12 && hour < 17) greeting = 'Afternoon';
                else if (hour >= 17) greeting = 'Evening';
                document.getElementById('timeGreeting').textContent = greeting;

                // Chart.js
                const labels = [
                    <c:forEach items="${chartCategories}" var="cat" varStatus="loop">
                        '${cat}'${!loop.last ? ',' : ''}
                    </c:forEach>
                ];

                const data = [
                    <c:forEach items="${chartValues}" var="val" varStatus="loop">
                        ${val}${!loop.last ? ',' : ''}
                    </c:forEach>
                ];

                const ctx = document.getElementById('expenseChart').getContext('2d');
                new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: labels,
                        datasets: [{
                            data: data,
                            backgroundColor: ['#4f46e5', '#ec4899', '#10b981', '#f59e0b', '#8b5cf6', '#ef4444'],
                            borderWidth: 0,
                            hoverOffset: 4
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                position: 'bottom',
                                labels: { padding: 20, usePointStyle: true }
                            }
                        },
                        cutout: '70%'
                    }
                });
                // Chart.js - End of block
            </script>
        </body>

        </html>