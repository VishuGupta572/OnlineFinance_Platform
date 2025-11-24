# Online Finance Platform

A comprehensive personal finance management system designed to help users track expenses, set budgets, and receive professional financial advice. This platform connects users with financial advisors and provides a robust admin dashboard for system management.

## Features

### 👤 User Module
- **Dashboard**: Real-time overview of financial health with interactive charts.
- **Expense Tracking**: Add, view, and categorize daily expenses.
- **Budgeting**: Set monthly budgets for different categories and track progress.
- **Feedback**: Submit feedback to the platform administrators.
- **Advice**: Receive personalized financial advice from certified advisors.

### 💼 Advisor Module
- **Client Management**: View user profiles and their financial data.
- **Advice System**: Send tailored financial advice to users based on their spending habits.

### 🛡️ Admin Module
- **User Management**: Monitor user activities and manage accounts.
- **System Overview**: High-level view of platform usage and statistics.

## Tech Stack

- **Backend**: Java Servlets, JSP (JavaServer Pages)
- **Database**: MySQL
- **Build Tool**: Maven
- **Frontend**: HTML5, CSS3, JavaScript (Chart.js for visualizations)
- **Server**: Apache Tomcat / Jetty

## Setup & Installation

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Maven 3.x
- MySQL Server 8.x

### Database Setup
1. Create a MySQL database named `finance_db`.
2. Import the schema from `database/schema.sql`:
   ```bash
   mysql -u root -p finance_db < database/schema.sql
   ```
3. Update database credentials in `src/main/java/com/finance/util/DBConnection.java` if necessary.

### Build & Run
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/finance-platform.git
   ```
2. Navigate to the project directory:
   ```bash
   cd finance-platform
   ```
3. Build the project using Maven:
   ```bash
   mvn clean package
   ```
4. Run using the Jetty plugin:
   ```bash
   mvn jetty:run
   ```
5. Access the application at `http://localhost:8080/finance`.

## Default Credentials

| Role | Email | Password |
|------|-------|----------|
| **Admin** | admin@finance.com | admin123 |
| **Advisor** | advisor@finance.com | advisor123 |
| **User** | user@finance.com | user123 |

## License
This project is licensed under the MIT License.
