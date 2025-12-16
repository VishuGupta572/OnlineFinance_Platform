# Eclipse & Tomcat Setup Guide

This project is a standard Maven Web Application. Follow these steps to run it in Eclipse using Apache Tomcat.

## Prerequisites
1.  **Eclipse IDE for Enterprise Java and Web Developers** (contains Maven and Server tools).
2.  **Apache Tomcat 9.0** (or higher) installed locally.
3.  **MySQL Server** running.

## Step 1: Database Setup
1.  Ensure MySQL is running.
2.  Open the `database/schema.sql` file.
3.  Run the script in your MySQL Workbench or command line to create the database and tables.
    ```bash
    mysql -u root -p < database/schema.sql
    ```
4.  **Important**: Check `src/main/java/com/finance/util/DBConnection.java` and update the `URL`, `USER`, and `PASSWORD` to match your local MySQL configuration.

## Step 2: Import into Eclipse
1.  Open Eclipse.
2.  Go to **File > Import...**
3.  Select **Maven > Existing Maven Projects** and click Next.
4.  Browse to the project root directory (`FinancePlatform_Online`).
5.  Ensure the `pom.xml` is selected and click **Finish**.
6.  Wait for Maven to download dependencies (check the bottom right progress bar).

## Step 3: Configure Tomcat Server in Eclipse
1.  Go to the **Servers** view (Window > Show View > Servers).
2.  Click the link **"No servers are available. Click this link to create a new server..."**
3.  Select **Apache > Tomcat v9.0 Server** and click Next.
4.  Browse to your local Tomcat installation directory and click Finish.

## Step 4: Run the Application
1.  In the **Project Explorer**, right-click the project (`finance-platform`).
2.  Select **Run As > Run on Server**.
3.  Select your configured Tomcat server and click Finish.
4.  Eclipse will start Tomcat and open the application in the internal browser.
    - URL: `http://localhost:8080/finance` (or similar, depending on context path).

## Troubleshooting
- **"javax.servlet not found"**: Ensure `pom.xml` has `javax.servlet-api` as `provided` (it already does).
- **Database Connection Error**: Check the Console output. Verify your MySQL credentials in `DBConnection.java`.
