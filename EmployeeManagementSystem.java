import java.sql.*;
import java.util.Scanner;

public class EmployeeManagementSystem {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/vijay";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Mysql@1901";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View All Employees");
            System.out.println("2. View Employee by ID");
            System.out.println("3. Insert Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Count Total Employees");
            System.out.println("7. Calculate Average Salary");
            System.out.println("8. View Performance Ranking");
            System.out.println("9. Mark Attendance");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        viewAllEmployees();
                        break;
                    case 2:
                        viewEmployeeById();
                        break;
                    case 3:
                        insertEmployee();
                        break;
                    case 4:
                        updateEmployee();
                        break;
                    case 5:
                        deleteEmployee();
                        break;
                    case 6:
                        countTotalEmployees();
                        break;
                    case 7:
                        calculateAverageSalary();
                        break;
                    case 8:
                        viewPerformanceRanking();
                        break;
                    case 9:
                        markAttendance();
                        break;
                    case 10:
                        System.out.println("Exiting... Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void viewAllEmployees() throws Exception {
        String query = "SELECT * FROM employee";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("\nEmployee Details:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("empid") + ", Name: " + resultSet.getString("ename") + ", Salary: " + resultSet.getInt("salary"));
            }
        }
    }

    private static void viewEmployeeById() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Employee ID: ");
        int empId = scanner.nextInt();

        String query = "SELECT * FROM employee WHERE empid = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("empid") + ", Name: " + resultSet.getString("ename") + ", Salary: " + resultSet.getInt("salary"));
            } else {
                System.out.println("No employee found with ID: " + empId);
            }
        }
    }

    private static void insertEmployee() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Employee Salary: ");
        int salary = scanner.nextInt();

        String query = "INSERT INTO employee (ename, salary) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setInt(2, salary);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Employee inserted successfully.");
            }
        }
    }

    private static void updateEmployee() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Employee ID to update: ");
        int empId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter New Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Salary: ");
        int salary = scanner.nextInt();

        String query = "UPDATE employee SET ename = ?, salary = ? WHERE empid = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setInt(2, salary);
            statement.setInt(3, empId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("No employee found with ID: " + empId);
            }
        }
    }

    private static void deleteEmployee() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Employee ID to delete: ");
        int empId = scanner.nextInt();

        String query = "DELETE FROM employee WHERE empid = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, empId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("No employee found with ID: " + empId);
            }
        }
    }

    private static void countTotalEmployees() throws Exception {
        String query = "SELECT COUNT(*) AS total FROM employee";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                System.out.println("Total Employees: " + resultSet.getInt("total"));
            }
        }
    }

    private static void calculateAverageSalary() throws Exception {
        String query = "SELECT AVG(salary) AS average_salary FROM employee";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                System.out.println("Average Salary: " + resultSet.getDouble("average_salary"));
            }
        }
    }

    private static void viewPerformanceRanking() throws SQLException {
        String query = """
            SELECT e.empid, e.ename, p.performance_score, p.ranking 
            FROM employee e 
            INNER JOIN performance_ranking p ON e.empid = p.empid 
            ORDER BY p.performance_score DESC
        """;

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("\nPerformance Ranking:");
            int rank = 1;
            while (resultSet.next()) {
                System.out.println("Rank " + rank + ": ID: " + resultSet.getInt("empid") +
                        ", Name: " + resultSet.getString("ename") +
                        ", Score: " + resultSet.getInt("performance_score") +
                        ", Rank Description: " + resultSet.getString("ranking"));
                rank++;
            }
        }
    }

    private static void markAttendance() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Employee ID: ");
        int empId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter Attendance Status (Present/Absent): ");
        String status = scanner.nextLine();

        String query = "INSERT INTO attendance (empid, date, status) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, empId);
            statement.setString(2, date);
            statement.setString(3, status);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Attendance marked successfully.");
            }
        }
    }
}
