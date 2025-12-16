<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Finance Platform - Your Wealth Partner</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=2">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>

    <body class="landing-page">

        <!-- Navigation -->
        <nav class="navbar">
            <div class="nav-container">
                <a href="#" class="logo">FinancePlatform</a>
                <div class="nav-links">
                    <a href="#features">Features</a>
                    <a href="#about">About</a>
                    <a href="login.jsp" class="nav-btn-outline">Login</a>
                    <a href="register.jsp" class="nav-btn-primary">Register</a>
                </div>
            </div>
        </nav>

        <!-- Hero Section -->
        <header class="hero">
            <div class="hero-content">
                <h1>Experience the Future of <span class="highlight">Financial Freedom</span></h1>
                <p>Track expenses, manage investments, and get AI-powered advice all in one place. Secure, intuitive,
                    and designed for you.</p>
                <div class="hero-buttons">
                    <a href="register.jsp" class="btn-large primary">Get Started Free</a>
                    <a href="login.jsp" class="btn-large secondary">Login</a>
                </div>
            </div>
            <div class="hero-image">
                <!-- Placeholder for illustration -->
                <div class="illustration-placeholder">
                    <div class="circle-bg"></div>
                    <div class="card-float card-1">
                        <span>Portfolio</span>
                        <strong>+24.5%</strong>
                    </div>
                    <div class="card-float card-2">
                        <span>Expenses</span>
                        <strong>$1,250</strong>
                    </div>
                </div>
            </div>
        </header>

        <!-- Features Section -->
        <section id="features" class="features">
            <div class="section-title">
                <h2>Why Choose Us?</h2>
                <p>Comprehensive tools to master your money.</p>
            </div>
            <div class="features-grid">
                <div class="feature-card">
                    <div class="icon" style="color: var(--primary-color);"><i class="fa-solid fa-chart-line"></i></div>
                    <h3>Expense Tracking</h3>
                    <p>Monitor your daily spending with intuitive charts and categorization.</p>
                </div>

                <div class="feature-card">
                    <div class="icon" style="color: var(--success-color);"><i class="fa-solid fa-robot"></i></div>
                    <h3>Smart Advisory</h3>
                    <p>Get personalized financial advice based on your spending habits.</p>
                </div>

                <div class="feature-card">
                    <div class="icon" style="color: #f59e0b;"><i class="fa-brands fa-bitcoin"></i></div>
                    <h3>Crypto Portfolio</h3>
                    <p>Track real-time cryptocurrency prices and manage your digital assets.</p>
                </div>

                <div class="feature-card">
                    <div class="icon" style="color: #6366f1;"><i class="fa-solid fa-calendar-check"></i></div>
                    <h3>Recurring Bills</h3>
                    <p>Never miss a payment with automated tracking for monthly subscriptions.</p>
                </div>
            </div>
        </section>

        <!-- CTA Section -->
        <section class="cta-section">
            <h2>Ready to take control?</h2>
            <p>Join thousands of users managing their finances smarter.</p>
            <a href="register.jsp" class="btn-large white">Create Free Account</a>
        </section>

        <!-- Footer -->
        <footer>
            <div class="footer-content">
                <div class="footer-col">
                    <h4>FinancePlatform</h4>
                    <p>Your trusted partner in financial growth.</p>
                </div>
                <div class="footer-col">
                    <h4>Product</h4>
                    <a href="#">Features</a>
                    <a href="#">Pricing</a>
                </div>
                <div class="footer-col">
                    <h4>Company</h4>
                    <a href="#">About Us</a>
                    <a href="#">Contact</a>
                </div>
            </div>
            <div class="footer-bottom">
                <p>&copy; 2025 FinancePlatform. All rights reserved.</p>
            </div>
        </footer>

    </body>

    </html>