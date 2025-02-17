import java.util.*;

class Employee {
    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Salary: " + salary;
    }
}

class EmployeeManagementSystem {
    private List<Employee> employeeList;

    public EmployeeManagementSystem() {
        employeeList = new ArrayList<>();
    }

    public void addEmployee(int id, String name, double salary) {
        for (Employee emp : employeeList) {
            if (emp.id == id) {
                System.out.println("Error: Employee with ID " + id + " already exists.");
                return;
            }
        }
        employeeList.add(new Employee(id, name, salary));
        System.out.println("Employee Added: ID=" + id + ", Name=" + name + ", Salary=" + salary);
    }

    public void updateEmployeeSalary(int id, double newSalary) {
        for (Employee emp : employeeList) {
            if (emp.id == id) {
                emp.salary = newSalary;
                System.out.println("Employee ID " + id + " updated successfully.");
                return;
            }
        }
        System.out.println("Error: Employee with ID " + id + " not found.");
    }

    public void removeEmployee(int id) {
        Iterator<Employee> iterator = employeeList.iterator();
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if (emp.id == id) {
                iterator.remove();
                System.out.println("Employee ID " + id + " removed successfully.");
                return;
            }
        }
        System.out.println("Error: Employee with ID " + id + " not found.");
    }

    public void searchEmployee(String query) {
        boolean found = false;
        for (Employee emp : employeeList) {
            if (String.valueOf(emp.id).equals(query) || emp.name.equalsIgnoreCase(query)) {
                System.out.println("Employee Found: " + emp);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No employee found with the given ID or Name.");
        }
    }

    public void displayEmployees() {
        if (employeeList.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee emp : employeeList) {
                System.out.println(emp);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem();

        System.out.println("Test Case 1: Display Employees (No employees initially)");
        ems.displayEmployees();

        System.out.println("\nTest Case 2: Add Employees");
        ems.addEmployee(101, "Anish", 50000);
        ems.addEmployee(102, "Bobby", 60000);

        System.out.println("\nTest Case 3: Update Employee Salary");
        ems.updateEmployeeSalary(101, 55000);

        System.out.println("\nTest Case 4: Search Employee by ID");
        ems.searchEmployee("102");

        System.out.println("\nTest Case 5: Remove Employee");
        ems.removeEmployee(101);

        
        System.out.println("\nTest Case 6: Display All Employees");
        ems.displayEmployees();

        
        System.out.println("\nTest Case 7: Adding Duplicate Employee ID");
        ems.addEmployee(101, "Charlie", 70000);
    }
}
