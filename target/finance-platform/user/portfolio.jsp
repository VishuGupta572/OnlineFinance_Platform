<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <title>Crypto Portfolio - Finance Platform</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=3">
                <!-- Font Awesome -->
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
            </head>

            <body>
                <div class="dashboard-layout">
                    <!-- Top Navigation -->
                    <header class="top-bar">
                        <div class="logo-small"><i class="fa-solid fa-wallet"></i> FinancePlatform</div>
                        <div class="user-menu">
                            <a href="${pageContext.request.contextPath}/user?action=dashboard" class="back-link"
                                style="margin: 0; font-weight: 600;"><i class="fa-solid fa-arrow-left"></i> Back to
                                Dashboard</a>
                        </div>
                    </header>

                    <main class="dashboard-content">
                        <div class="welcome-section">
                            <h1 style="font-size: 2rem; margin-bottom: 0.5rem;">Crypto Portfolio</h1>
                            <p style="color: var(--text-secondary);">Track your digital assets in real-time.</p>
                        </div>

                        <c:if test="${not empty param.message}">
                            <div class="message"><i class="fa-solid fa-check-circle"></i> ${param.message}</div>
                        </c:if>
                        <c:if test="${not empty param.error}">
                            <div class="error"><i class="fa-solid fa-exclamation-circle"></i> ${param.error}</div>
                        </c:if>

                        <div class="dashboard-grid">
                            <!-- Holdings Table (Takes 2 columns on large screens) -->
                            <div class="card" style="grid-column: span 2;">
                                <h3><i class="fa-solid fa-coins" style="color: var(--primary-color);"></i> Your Holdings
                                </h3>
                                <div class="table-container">
                                    <table>
                                        <thead>
                                            <tr>
                                                <th>Asset</th>
                                                <th>Quantity</th>
                                                <th>Avg Price</th>
                                                <th>Current Price</th>
                                                <th>Value</th>
                                                <th>P/L</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="inv" items="${investments}">
                                                <tr>
                                                    <td style="text-transform: capitalize; font-weight: 600;">
                                                        ${inv.symbol}</td>
                                                    <td>${inv.quantity}</td>
                                                    <td>
                                                        <fmt:formatNumber value="${inv.avgBuyPrice}" type="currency"
                                                            currencySymbol="$" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${inv.currentPrice}" type="currency"
                                                            currencySymbol="$" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${inv.currentValue}" type="currency"
                                                            currencySymbol="$" />
                                                    </td>
                                                    <td>
                                                        <span
                                                            class="badge ${inv.profitLoss >= 0 ? 'badge-green' : 'badge-red'}">
                                                            ${inv.profitLoss >= 0 ? '+' : ''}
                                                            <fmt:formatNumber value="${inv.profitLoss}" type="currency"
                                                                currencySymbol="$" />
                                                        </span>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <c:if test="${empty investments}">
                                                <tr>
                                                    <td colspan="6"
                                                        style="text-align: center; padding: 2rem; color: var(--text-secondary);">
                                                        No investments yet. Use the form to buy some crypto!</td>
                                                </tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                            <!-- Buy Crypto Form -->
                            <div class="card">
                                <h3><i class="fa-solid fa-rocket" style="color: var(--primary-color);"></i> Buy Crypto
                                </h3>
                                <div
                                    style="padding: 1rem; background: #f0f9ff; border-radius: 0.5rem; margin-bottom: 1.5rem; font-size: 0.9rem; color: #0369a1;">
                                    Balance Available: <strong>
                                        <fmt:formatNumber value="${user.balance}" type="currency" currencySymbol="$" />
                                    </strong>
                                </div>

                                <form action="user?action=buyInvestment" method="post">
                                    <div class="form-group">
                                        <label>Asset Symbol</label>
                                        <input type="text" name="symbol" placeholder="bitcoin" required
                                            style="text-transform: lowercase;">
                                        <small style="color: var(--text-secondary);">Supported: bitcoin, ethereum,
                                            solana, etc.</small>
                                    </div>
                                    <div class="form-group">
                                        <label>Quantity</label>
                                        <input type="number" name="quantity" step="0.00000001" placeholder="0.5"
                                            required>
                                    </div>
                                    <button type="submit">Buy Asset</button>
                                </form>
                            </div>
                        </div>
                    </main>
                </div>
            </body>

            </html>