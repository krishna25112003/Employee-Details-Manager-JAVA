import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private Connection connection;
    public Company() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "root", "root");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    
    
    public void addEmployee(Employee employee) {
        String query = "INSERT INTO employees (id, name, department, salary, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employee.getId());
            stmt.setString(2, employee.getName());
            stmt.setString(3, employee.getDepartment());
            stmt.setDouble(4, employee.getSalary());
            stmt.setString(5, employee.getEmail());
            stmt.setString(6, employee.getPhoneNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeEmployee(int id) {
        String query = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editEmployee(int id, Employee newDetails) {
        String query = "UPDATE employees SET name = ?, department = ?, salary = ?, email = ?, phone_number = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newDetails.getName());
            stmt.setString(2, newDetails.getDepartment());
            stmt.setDouble(3, newDetails.getSalary());
            stmt.setString(4, newDetails.getEmail());
            stmt.setString(5, newDetails.getPhoneNumber());
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean employeeExists(int id) {
        String query = "SELECT id FROM employees WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Employee> listEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String department = rs.getString("department");
                double salary = rs.getDouble("salary");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                Employee employee = new Employee(id, name, department, salary, email, phoneNumber);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<Employee> searchEmployees(String searchCriteria) {
        List<Employee> searchResults = new ArrayList<>();
        String query = "SELECT * FROM employees WHERE name LIKE ? OR department LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String searchTerm = "%" + searchCriteria + "%";
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String department = rs.getString("department");
                double salary = rs.getDouble("salary");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                Employee employee = new Employee(id, name, department, salary, email, phoneNumber);
                searchResults.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
}
