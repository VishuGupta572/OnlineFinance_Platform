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

                        <button id="themeToggle" class="btn-logout"
                            style="margin-right: 10px; border: none; background: transparent; font-size: 1.2rem; padding: 5px; cursor: pointer;">
                            <i class="fa-solid fa-moon"></i>
                        </button>
                        <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn-logout"><i
                                class="fa-solid fa-sign-out-alt"></i> Logout</a>
                    </div>
                </header>

                <script>
                    // Dark Mode Logic
                    const toggleBtn = document.getElementById('themeToggle');
                    const icon = toggleBtn.querySelector('i');
                    const body = document.body;

                    // Check saved preference
                    if (localStorage.getItem('theme') === 'dark') {
                        body.classList.add('dark-mode');
                        icon.classList.replace('fa-moon', 'fa-sun');
                    }

                    toggleBtn.addEventListener('click', () => {
                        body.classList.toggle('dark-mode');
                        const isDark = body.classList.contains('dark-mode');

                        // Save preference
                        localStorage.setItem('theme', isDark ? 'dark' : 'light');

                        // Update Icon
                        icon.className = isDark ? 'fa-solid fa-sun' : 'fa-solid fa-moon';

                        // Update Chart if it exists
                        if (window.expenseChartInstance) {
                            const chart = window.expenseChartInstance;
                            const borderColor = isDark ? '#1f2937' : '#ffffff';
                            const textColor = isDark ? '#9ca3af' : '#4b5563';

                            chart.data.datasets[0].borderColor = borderColor;
                            chart.data.datasets[0].hoverBorderColor = borderColor;
                            chart.options.plugins.legend.labels.color = textColor;
                            chart.update();
                        }
                    });
                </script>

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

                        <!-- Left Column: Main Content (Goals, Activity) -->
                        <div style="display: flex; flex-direction: column; gap: 2rem;">

                            <!-- Savings Goals Section -->
                            <div class="card">
                                <div
                                    style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                                    <h3><i class="fa-solid fa-piggy-bank" style="color: var(--primary-color);"></i>
                                        Savings Goals</h3>
                                    <button onclick="document.getElementById('createGoalModal').style.display='block'"
                                        style="background: var(--primary-color); color: white; border: none; padding: 5px 15px; border-radius: 5px; cursor: pointer;">
                                        <i class="fa-solid fa-plus"></i> New Goal
                                    </button>
                                </div>

                                <div
                                    style="display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 1.5rem;">
                                    <c:forEach var="goal" items="${savingsGoals}">
                                        <div
                                            style="background: var(--bg-color); padding: 1rem; border-radius: 10px; border: 1px solid var(--border-color);">
                                            <div
                                                style="display: flex; justify-content: space-between; margin-bottom: 0.5rem;">
                                                <strong style="font-size: 1.1rem;">${goal.name}</strong>
                                                <span style="font-size: 0.9rem; color: var(--text-secondary);">Target:
                                                    $${goal.targetAmount}</span>
                                            </div>

                                            <div
                                                style="display: flex; align-items: center; gap: 10px; margin-bottom: 0.5rem;">
                                                <div
                                                    style="flex-grow: 1; height: 10px; background: #e0e7ff; border-radius: 5px; overflow: hidden;">
                                                    <div
                                                        style="height: 100%; width: ${goal.progressPercentage}%; background: var(--secondary-color); transition: width 0.5s;">
                                                    </div>
                                                </div>
                                                <span
                                                    style="font-size: 0.8rem; font-weight: bold;">${String.format("%.1f",
                                                    goal.progressPercentage)}%</span>
                                            </div>

                                            <div
                                                style="display: flex; justify-content: space-between; align-items: center; margin-top: 1rem;">
                                                <span style="font-size: 0.9rem;">Saved:
                                                    <strong>$${goal.currentAmount}</strong></span>

                                                <div style="display: flex; gap: 5px;">
                                                    <form action="user" method="post"
                                                        style="display: flex; gap: 5px; margin: 0;">
                                                        <input type="hidden" name="action" value="addFundsToGoal">
                                                        <input type="hidden" name="goalId" value="${goal.id}">
                                                        <input type="number" name="amount" placeholder="$" step="1"
                                                            style="width: 70px; padding: 5px; border: 1px solid var(--border-color); border-radius: 5px;"
                                                            required>
                                                        <button type="submit" title="Add Funds"
                                                            style="padding: 5px 10px; background: var(--success-color); color: white; border: none; border-radius: 5px; cursor: pointer;">
                                                            <i class="fa-solid fa-coins"></i>
                                                        </button>
                                                    </form>

                                                    <form action="user" method="post"
                                                        onsubmit="return confirm('Delete this goal and refund money?');"
                                                        style="margin: 0;">
                                                        <input type="hidden" name="action" value="deleteGoal">
                                                        <input type="hidden" name="goalId" value="${goal.id}">
                                                        <button type="submit" title="Delete Goal"
                                                            style="padding: 5px; background: transparent; color: var(--error-color); border: none; cursor: pointer;">
                                                            <i class="fa-solid fa-trash"></i>
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <c:if test="${empty savingsGoals}">
                                        <p style="color: var(--text-secondary); text-align: center; padding: 20px;">No
                                            savings goals yet. Create one to start saving!</p>
                                    </c:if>
                                </div>
                            </div>

                            <!-- Recent Activity Table -->
                            <div class="card">
                                <div
                                    style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                                    <h3><i class="fa-solid fa-list-ul" style="color: var(--primary-color);"></i> Recent
                                        Activity</h3>
                                    <a href="${pageContext.request.contextPath}/download?type=pdf"
                                        style="font-size: 0.9rem; border: 1px solid var(--primary-color); padding: 5px 10px; border-radius: 5px;">
                                        <i class="fa-solid fa-file-pdf"></i> Download PDF
                                    </a>
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
                                                            <c:when test="${t.type == 'TRANSFER'}"><span
                                                                    class="badge badge-green">TRANSFER</span></c:when>
                                                            <c:when test="${t.type == 'EXPENSE'}"><span
                                                                    class="badge badge-warning">EXPENSE</span></c:when>
                                                            <c:otherwise><span class="badge badge-red">${t.type}</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${t.type == 'TRANSFER'}">${t.senderName}
                                                                &rarr; ${t.receiverName}</c:when>
                                                            <c:otherwise>${t.receiverName}</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td style="text-align: right;"
                                                        class="${t.type == 'TRANSFER' && t.receiverId == sessionScope.user.id ? 'text-green' : 'text-red'}">
                                                        <c:choose>
                                                            <c:when
                                                                test="${t.type == 'TRANSFER' && t.receiverId == sessionScope.user.id}">
                                                                +${t.amount}</c:when>
                                                            <c:otherwise>-${t.amount}</c:otherwise>
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
                            <!-- Recurring Bills (Moved from Sidebar) -->
                            <div class="card">
                                <h3><i class="fa-solid fa-clock" style="color: var(--primary-color);"></i> Recurring
                                    Bills</h3>
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
                                    <button type="submit" style="background: var(--secondary-color); padding: 8px;"><i
                                            class="fa-solid fa-check"></i> Setup Recurring</button>
                                </form>
                            </div>
                        </div>

                        <!-- Right Column: Sidebar (Actions, Charts) -->
                        <div style="display: flex; flex-direction: column; gap: 2rem;">

                            <!-- Expense Chart -->
                            <div class="card">
                                <h3><i class="fa-solid fa-chart-pie" style="color: var(--primary-color);"></i> Analysis
                                </h3>
                                <div class="chart-container" style="height: 250px;">
                                    <canvas id="expenseChart"></canvas>
                                </div>
                            </div>

                            <!-- Quick Actions Accordion/Cards -->
                            <div class="card">
                                <h3><i class="fa-solid fa-bolt" style="color: var(--primary-color);"></i> Quick Actions
                                </h3>

                                <!-- Add Expense Form -->
                                <details open
                                    style="margin-bottom: 1rem; border-bottom: 1px solid var(--border-color); padding-bottom: 1rem;">
                                    <summary
                                        style="cursor: pointer; font-weight: 600; color: var(--text-primary); margin-bottom: 0.5rem;">
                                        Add Expense</summary>
                                    <form action="${pageContext.request.contextPath}/user" method="post"
                                        style="margin-top: 10px;">
                                        <input type="hidden" name="action" value="addExpense">
                                        <div style="display: grid; gap: 10px;">
                                            <select name="category" required>
                                                <option value="" disabled selected>Category</option>
                                                <option>Food</option>
                                                <option>Transport</option>
                                                <option>Utilities</option>
                                                <option>Entertainment</option>
                                                <option>Other</option>
                                            </select>
                                            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
                                                <input type="number" step="0.01" name="amount" placeholder="Amount (₹)"
                                                    required>
                                                <input type="date" name="date" required>
                                            </div>
                                            <input type="text" name="description" placeholder="Description">
                                            <button type="submit" style="padding: 10px;"><i
                                                    class="fa-solid fa-plus"></i> Add</button>
                                        </div>
                                    </form>
                                </details>

                                <!-- Transfer Funds Form -->
                                <details>
                                    <summary
                                        style="cursor: pointer; font-weight: 600; color: var(--text-primary); margin-bottom: 0.5rem;">
                                        Transfer Funds</summary>
                                    <form action="${pageContext.request.contextPath}/user" method="post"
                                        style="margin-top: 10px;">
                                        <input type="hidden" name="action" value="transferFunds">
                                        <div style="display: grid; gap: 10px;">
                                            <input type="email" name="toEmail" required placeholder="Recipient Email">
                                            <input type="number" step="0.01" name="amount" placeholder="Amount (₹)"
                                                required>
                                            <button type="submit" style="padding: 10px;"><i
                                                    class="fa-solid fa-paper-plane"></i> Send</button>
                                        </div>
                                    </form>
                                </details>
                            </div>

                        </div>
                    </div> <!-- End Grid -->
                </main>
            </div>

            <!-- Create Goal Modal -->
            <div id="createGoalModal" class="modal">
                <div class="modal-content">
                    <span class="close"
                        onclick="document.getElementById('createGoalModal').style.display='none'">&times;</span>
                    <h3 style="margin-bottom: 1.5rem;">Create Savings Goal</h3>
                    <form action="user" method="post" style="display: grid; gap: 15px;">
                        <input type="hidden" name="action" value="createGoal">
                        <div>
                            <label style="display: block; margin-bottom: 5px; font-weight: 500;">Goal Name</label>
                            <input type="text" name="name" required placeholder="e.g. New Laptop">
                        </div>
                        <div>
                            <label style="display: block; margin-bottom: 5px; font-weight: 500;">Target Amount</label>
                            <input type="number" name="targetAmount" step="0.01" required placeholder="Amount (₹)">
                        </div>
                        <div>
                            <label style="display: block; margin-bottom: 5px; font-weight: 500;">Target Date</label>
                            <input type="date" name="deadline" required>
                        </div>
                        <button type="submit" style="margin-top: 5px;">Create Goal</button>
                    </form>
                </div>
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

                // Gradient Generation Helper
                const createGradient = (ctx, colorStart, colorEnd) => {
                    const gradient = ctx.createLinearGradient(0, 0, 0, 400);
                    gradient.addColorStop(0, colorStart);
                    gradient.addColorStop(1, colorEnd);
                    return gradient;
                };

                // Premium Colors
                const gradients = [
                    createGradient(ctx, '#4f46e5', '#818cf8'), // Primary
                    createGradient(ctx, '#ec4899', '#f472b6'), // Pink
                    createGradient(ctx, '#10b981', '#34d399'), // Green
                    createGradient(ctx, '#f59e0b', '#fbbf24'), // Amber
                    createGradient(ctx, '#8b5cf6', '#a78bfa'), // Violet
                    createGradient(ctx, '#ef4444', '#f87171')  // Red
                ];

                window.expenseChartInstance = new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: labels,
                        datasets: [{
                            data: data,
                            backgroundColor: gradients,
                            borderWidth: 2,
                            borderColor: document.body.classList.contains('dark-mode') ? '#1f2937' : '#ffffff',
                            hoverOffset: 15,
                            hoverBorderColor: document.body.classList.contains('dark-mode') ? '#1f2937' : '#ffffff',
                            hoverBorderWidth: 4
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        animation: {
                            animateScale: true,
                            animateRotate: true,
                            duration: 2000,
                            easing: 'easeOutQuart'
                        },
                        layout: {
                            padding: 20
                        },
                        plugins: {
                            legend: {
                                position: 'bottom',
                                labels: {
                                    padding: 20,
                                    usePointStyle: true,
                                    pointStyle: 'circle',
                                    font: {
                                        family: "'Inter', sans-serif",
                                        size: 11
                                    },
                                    color: document.body.classList.contains('dark-mode') ? '#9ca3af' : '#4b5563'
                                }
                            },
                            tooltip: {
                                backgroundColor: 'rgba(17, 24, 39, 0.9)',
                                titleFont: { family: "'Inter', sans-serif", size: 13 },
                                bodyFont: { family: "'Inter', sans-serif", size: 13, weight: 'bold' },
                                padding: 12,
                                cornerRadius: 8,
                                displayColors: true,
                                callbacks: {
                                    label: function (context) {
                                        let label = context.label || '';
                                        if (label) {
                                            label += ': ';
                                        }
                                        if (context.parsed !== null) {
                                            label += new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(context.parsed);
                                        }
                                        return label;
                                    }
                                }
                            }
                        },
                        cutout: '75%',
                    },
                    plugins: [{
                        id: 'textCenter',
                        beforeDraw: function (chart) {
                            var width = chart.width,
                                height = chart.height,
                                ctx = chart.ctx;

                            ctx.restore();
                            var fontSize = (height / 210).toFixed(2);
                            ctx.font = "bold " + fontSize + "em Inter";
                            ctx.textBaseline = "middle";
                            ctx.fillStyle = document.body.classList.contains('dark-mode') ? '#f3f4f6' : '#111827';

                            var text = "Expenses",
                                textX = Math.round((width - ctx.measureText(text).width) / 2),
                                textY = height / 2;

                            ctx.fillText(text, textX, textY);
                            ctx.save();
                        }
                    }]
                });
                // Chart.js - End of block
            </script>
        </body>

        </html>