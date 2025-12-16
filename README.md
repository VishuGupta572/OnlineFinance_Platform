# 💰 Online Finance Platform

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge)

> **Finance Platform** is a professional-grade personal finance management ecosystem designed to empower users with comprehensive tools for tracking expenses, managing investments, and planning financial goals. Built with a robust Java backend and a modern, responsive frontend, it delivers a secure and intuitive banking experience.

---

## ✨ Key Features & Functionalities

### 1. 💳 Core Banking & Transactions
-   **Atomic Fund Transfers**: Secure, transactional money transfers between users with automatic rollback on failure.
-   **Transaction History**: Detailed logs of all incoming and outgoing funds with intuitive visual indicators (Green for Income, Red for Expense/Transfer).
-   **Unified Activity Feed**: A single stream combining stock transfers and daily expenses for a complete financial overview.

### 2. � Expense Management
-   **Smart Tracking**: Categorize daily spending (Food, Transport, Utilities, etc.) and visualize data with interactive doughnut charts.
-   **Recurring Bills Engine**: configure automated monthly payments for rent, subscriptions, or loans. The system processes these automatically via an integrated scheduler.
-   **Budgeting**: Set monthly limits per category and track utilization in real-time.

### 3. 📈 Investment Portfolio (Crypto)
-   **Real-Time Data**: Integrated **CoinGecko API** for live cryptocurrency prices (Bitcoin, Ethereum, etc.) in **INR (₹)**.
-   **Portfolio Tracker**: Buy digital assets using your account balance, track current value vs. invested amount, and monitor portfolio performance dynamically.
-   **Simulated Trading**: Execute buy orders with cheat-proof validation against user balance.

### 4. 🎯 Financial Goals
-   **Savings Goals**: Create custom targets (e.g., "New Car", "Vacation") and allocate funds.
-   **Progress Visualizers**: Dynamic progress bars show completion percentage and remaining amounts.
-   **Goal Management**: Easy top-up and deletion (with automatic refund to main balance).

### 5. 🎨 Modern UI/UX
-   **Glassmorphism Design**: A premium, modern aesthetic with translucent cards, smooth gradients, and backdrop blurs.
-   **Dark/Light Mode**: Fully thematic interface with a one-click toggle that persists user preference.
-   **Responsive Layout**: Adaptive grid system that works seamlessly on desktop and mobile.
-   **Profile Management**: Upload custom profile pictures and manage personal details.

### 6. 📄 Reports & Security
-   **PDF Statements**: Generate and download professional monthly statements using **iText**.
-   **Enterprise Security**:
    -   **BCrypt Hashing**: Industry-standard password encryption.
    -   **Session Management**: Secure, role-based access control (User, Admin, Advisor).
    -   **Input Validation**: Strict server-side validation for all financial inputs.

---

## 🛠️ Technology Stack

| Component | Technologies |
| :--- | :--- |
| **Backend** | Java Servlets (RMSE), JSP, JDBC, Multithreading |
| **Database** | MySQL / HSQLDB (Compatible), Connection Pooling |
| **Frontend** | HTML5, CSS3 (Variables, Flexbox, Grid), JavaScript (ES6+) |
| **Libraries** | Chart.js (Visuals), Gson (JSON Parsing), iText (PDF), JBCrypt |
| **Build** | Maven (Dependency Management) |
| **Server** | Apache Jetty / Tomcat |

---

## 🚀 Setup & Installation

### Prerequisites
-   Java Development Kit (JDK) 8 or higher
-   Maven 3.x
-   MySQL Server (Optional, defaults to embedded/local config)

### Step-by-Step Guide

1.  **Clone the Repository**
    ```bash
    git clone https://github.com/yourusername/fintoo-platform.git
    cd fintoo-platform
    ```

2.  **Database Configuration**
    -   The project includes a `schema.sql` file in `src/main/resources`.
    -   Configure your database credentials in `DBConnection.java`.

3.  **Build the Project**
    ```bash
    mvn clean package
    ```

4.  **Run the Application**
    ```bash
    mvn jetty:run
    ```

5.  **Access the Platform**
    -   Open your browser and navigate to: `http://localhost:8080/finance`

---

## 🔐 Default Credentials

For testing purposes, the system is pre-loaded with the following accounts:

| Role | Email | Password |
| :--- | :--- | :--- |
| **User** | `user@finance.com` | `user123` |
| **Admin** | `admin@finance.com` | `admin123` |
| **Advisor** | `advisor@finance.com` | `advisor123` |

---

## 📜 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
