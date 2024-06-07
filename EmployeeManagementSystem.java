 


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeManagementSystem extends JFrame {
    private Company company;

    public EmployeeManagementSystem() {
        company = new Company();
        initUI();
    }

    private void initUI() {
        setTitle("Employee Details Manager");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Welcome Panel
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Employee Details Manager", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        JButton continueButton = new JButton("Click Here");
        continueButton.setFont(new Font("Serif", Font.BOLD, 18));

        continueButton.addActionListener(e -> showMainMenu());

        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        welcomePanel.add(continueButton, BorderLayout.SOUTH);

        // Set the welcome panel as the initial content pane
        setContentPane(welcomePanel);
    }

    private void showMainMenu() {
        // Main Menu Panel
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(7, 1));

        JButton addEmployeeButton = new JButton("Add Employee");
        JButton removeEmployeeButton = new JButton("Remove Employee");
        JButton editEmployeeButton = new JButton("Edit Employee");
        JButton listEmployeeButton = new JButton("List Employees");
        JButton searchButton = new JButton("Search");
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        mainMenuPanel.add(addEmployeeButton);
        mainMenuPanel.add(removeEmployeeButton);
        mainMenuPanel.add(editEmployeeButton);
        mainMenuPanel.add(listEmployeeButton);
        mainMenuPanel.add(searchButton);
        mainMenuPanel.add(backButton);
        mainMenuPanel.add(exitButton);

        addEmployeeButton.addActionListener(e -> showAddEmployeeDialog());
        removeEmployeeButton.addActionListener(e -> showRemoveEmployeeDialog());
        editEmployeeButton.addActionListener(e -> showEditEmployeeDialog());
        listEmployeeButton.addActionListener(e -> showListEmployeeDialog());
        searchButton.addActionListener(e -> showSearchDialog());
        backButton.addActionListener(e -> showWelcomePanel());
        exitButton.addActionListener(e -> System.exit(0));

        // Set the main menu panel as the content pane
        setContentPane(mainMenuPanel);
        revalidate();
        repaint();
    }

    private void showWelcomePanel() {
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Employee Details Manager", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        JButton continueButton = new JButton("Click Here");
        continueButton.setFont(new Font("Serif", Font.BOLD, 18));

        continueButton.addActionListener(e -> showMainMenu());

        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        welcomePanel.add(continueButton, BorderLayout.SOUTH);

        // Set the welcome panel as the content pane
        setContentPane(welcomePanel);
        revalidate();
        repaint();
    }

        private void showAddEmployeeDialog() {
        JDialog dialog = new JDialog(this, "Add Employee", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(7, 2, 10, 10));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField departmentField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();

        dialog.add(new JLabel("ID:"));
        dialog.add(idField);
        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Department:"));
        dialog.add(departmentField);
        dialog.add(new JLabel("Salary:"));
        dialog.add(salaryField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);
        dialog.add(new JLabel("Phone Number:"));
        dialog.add(phoneField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String department = departmentField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                String email = emailField.getText();
                String phone = phoneField.getText();

                Employee employee = new Employee(id, name, department, salary, email, phone);
                company.addEmployee(employee);
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel());
        dialog.add(addButton);

        dialog.setVisible(true);
    }

      private void showRemoveEmployeeDialog() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Employee ID to remove:");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                company.removeEmployee(id);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditEmployeeDialog() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Employee ID to edit:");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                if (!company.employeeExists(id)) {
                    throw new IllegalArgumentException("Employee not found.");
                }

                JDialog dialog = new JDialog(this, "Edit Employee", true);
                dialog.setSize(400, 300);
                dialog.setLayout(new GridLayout(7, 2, 10, 10));

                JTextField nameField = new JTextField();
                JTextField departmentField = new JTextField();
                JTextField salaryField = new JTextField();
                JTextField emailField = new JTextField();
                JTextField phoneField = new JTextField();

                dialog.add(new JLabel("Name:"));
                dialog.add(nameField);
                dialog.add(new JLabel("Department:"));
                dialog.add(departmentField);
                dialog.add(new JLabel("Salary:"));
                dialog.add(salaryField);
                dialog.add(new JLabel("Email:"));
                dialog.add(emailField);
                dialog.add(new JLabel("Phone Number:"));
                dialog.add(phoneField);

                JButton editButton = new JButton("Edit");
                editButton.addActionListener(e -> {
                    try {
                        String name = nameField.getText();
                        String department = departmentField.getText();
                        double salary = Double.parseDouble(salaryField.getText());
                        String email = emailField.getText();
                        String phone = phoneField.getText();

                        Employee newDetails = new Employee(id, name, department, salary, email, phone);
                        company.editEmployee(id, newDetails);
                        dialog.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                dialog.add(new JLabel());
                dialog.add(editButton);

                dialog.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void showListEmployeeDialog() {
        JDialog dialog = new JDialog(this, "List of Employees", true);
        dialog.setSize(600, 400);

        List<Employee> employees = company.listEmployees();
        String[] columnNames = {"ID", "Name", "Department", "Salary", "Email", "Phone Number"};
        String[][] data = new String[employees.size()][6];

        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);
            data[i][0] = String.valueOf(emp.getId());
            data[i][1] = emp.getName();
            data[i][2] = emp.getDepartment();
            data[i][3] = String.valueOf(emp.getSalary());
            data[i][4] = emp.getEmail();
            data[i][5] = emp.getPhoneNumber();
        }

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);

        dialog.setVisible(true);
    }

    private void showSearchDialog() {
        String searchCriteria = JOptionPane.showInputDialog(this, "Enter Name/Department");
        if (searchCriteria != null && !searchCriteria.isEmpty()) {
            List<Employee> searchResults = company.searchEmployees(searchCriteria);
            if (!searchResults.isEmpty()) {
                displaySearchResults(searchResults);
            } else {
                JOptionPane.showMessageDialog(this, "No matching employees found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void displaySearchResults(List<Employee> searchResults) {
        JDialog dialog = new JDialog(this, "Search Results", true);
        dialog.setSize(600, 400);

        String[] columnNames = {"ID", "Name", "Department", "Salary", "Email", "Phone Number"};
        String[][] data = new String[searchResults.size()][6];

        for (int i = 0; i < searchResults.size(); i++) {
            Employee emp = searchResults.get(i);
            data[i][0] = String.valueOf(emp.getId());
            data[i][1] = emp.getName();
            data[i][2]= emp.getDepartment();
            data[i][3] = String.valueOf(emp.getSalary());
            data[i][4] = emp.getEmail();
            data[i][5] = emp.getPhoneNumber();
        }

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementSystem ex = new EmployeeManagementSystem();
            ex.setVisible(true);
        });
    }
}

