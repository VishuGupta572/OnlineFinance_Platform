# 💰 Online Finance Platform

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge)

> A comprehensive personal finance management system designed to help users track expenses, set budgets, and receive professional financial advice.

---

## 🚀 Features

| Module | Description |
| :--- | :--- |
| **👤 User** | • **Dashboard**: Real-time financial health overview.<br>• **Expense Tracking**: Categorize and track daily expenses.<br>• **Budgeting**: Set and monitor monthly limits.<br>• **Advice**: Connect with certified financial advisors. |
| **💼 Advisor** | • **Client Management**: View user profiles and financial data.<br>• **Advice System**: Provide tailored financial guidance. |
| **🛡️ Admin** | • **User Management**: Monitor and manage platform accounts.<br>• **System Overview**: High-level usage statistics. |

---

## 🛠️ Tech Stack

- **Backend:** Java Servlets, JSP
- **Database:** MySQL
- **Build Tool:** Maven
- **Frontend:** HTML5, CSS3, JavaScript (Chart.js)
- **Server:** Apache Tomcat / Jetty

---

## 🏁 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8+
- Maven 3.x
- MySQL Server 8.x

### 💾 Database Setup

1. Create the database:
   ```sql
   CREATE DATABASE finance_db;
   ```
2. Import the schema:
   ```bash
   mysql -u root -p finance_db < database/schema.sql
   ```
3. Configure credentials in `src/main/java/com/finance/util/DBConnection.java`.

### 🏃‍♂️ Build & Run

```bash
# Clone the repository
git clone https://github.com/yourusername/finance-platform.git

# Navigate to project
cd finance-platform

# Build with Maven
mvn clean package

# Run with Jetty
mvn jetty:run
```

Access the application at: `http://localhost:8080/finance`

---

## 🔑 Default Credentials

| Role | Email | Password |
| :--- | :--- | :--- |
| **Admin** | `admin@finance.com` | `admin123` |
| **Advisor** | `advisor@finance.com` | `advisor123` |
| **User** | `user@finance.com` | `user123` |

---

## 📄 License

This project is licensed under the MIT License.
