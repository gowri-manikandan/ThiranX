package com.gowrimanikandan.thiranx.features.employee;

import com.gowrimanikandan.thiranx.data.dto.Employee;
import com.gowrimanikandan.thiranx.util.ConsoleInput;

import java.util.List;
import java.util.Scanner;

public class EmployeeListView {

    private final EmployeeListModel employeeListModel;
    private final Scanner scanner;

    public EmployeeListView() {
        this.employeeListModel = new EmployeeListModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        showEmployeeListMenu();
    }

    private void showEmployeeListMenu() {
        String filterBy = null;
        String filterValue = null;
        String sortBy = "name";
        boolean ascending = true;

        while (true) {
            System.out.println();
            System.out.println("Employee List Options");
            System.out.println("1. View all employees");
            System.out.println("2. Filter by name");
            System.out.println("3. Filter by email");
            System.out.println("4. Filter by role");
            System.out.println("5. Filter by status");
            System.out.println("6. Sort by name");
            System.out.println("7. Sort by email");
            System.out.println("8. Sort by role");
            System.out.println("9. Sort by status");
            System.out.println("10. Sort by creation date");
            System.out.println("11. Toggle sort order (current: " + (ascending ? "ascending" : "descending") + ")");
            System.out.println("12. Clear filters");
            System.out.println("13. Return to home");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    displayEmployees(employeeListModel.getFilteredAndSortedEmployees(filterBy, filterValue, sortBy, ascending));
                    break;
                case "2":
                    System.out.print("Enter name to filter: ");
                    filterBy = "name";
                    filterValue = scanner.nextLine().trim();
                    displayEmployees(employeeListModel.getFilteredAndSortedEmployees(filterBy, filterValue, sortBy, ascending));
                    break;
                case "3":
                    System.out.print("Enter email to filter: ");
                    filterBy = "email";
                    filterValue = scanner.nextLine().trim();
                    displayEmployees(employeeListModel.getFilteredAndSortedEmployees(filterBy, filterValue, sortBy, ascending));
                    break;
                case "4":
                    System.out.print("Enter role to filter (MANAGER/EMPLOYEE): ");
                    filterBy = "role";
                    filterValue = scanner.nextLine().trim();
                    displayEmployees(employeeListModel.getFilteredAndSortedEmployees(filterBy, filterValue, sortBy, ascending));
                    break;
                case "5":
                    System.out.print("Enter status to filter (ACTIVE/INACTIVE): ");
                    filterBy = "status";
                    filterValue = scanner.nextLine().trim();
                    displayEmployees(employeeListModel.getFilteredAndSortedEmployees(filterBy, filterValue, sortBy, ascending));
                    break;
                case "6":
                    sortBy = "name";
                    displayEmployees(employeeListModel.getFilteredAndSortedEmployees(filterBy, filterValue, sortBy, ascending));
                    break;
                case "7":
                    sortBy = "email";
                    displayEmployees(employeeListModel.getFilteredAndSortedEmployees(filterBy, filterValue, sortBy, ascending));
                    break;
                case "8":
                    sortBy = "role";
                    displayEmployees(employeeListModel.getFilteredAndSortedEmployees(filterBy, filterValue, sortBy, ascending));
                    break;
                case "9":
                    sortBy = "status";
                    displayEmployees(employeeListModel.getFilteredAndSortedEmployees(filterBy, filterValue, sortBy, ascending));
                    break;
                case "10":
                    sortBy = "created";
                    displayEmployees(employeeListModel.getFilteredAndSortedEmployees(filterBy, filterValue, sortBy, ascending));
                    break;
                case "11":
                    ascending = !ascending;
                    displayEmployees(employeeListModel.getFilteredAndSortedEmployees(filterBy, filterValue, sortBy, ascending));
                    break;
                case "12":
                    filterBy = null;
                    filterValue = null;
                    System.out.println("Filters cleared.");
                    break;
                case "13":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void displayEmployees(List<Employee> employees) {
        System.out.println();
        System.out.println("Employee List");
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("#   Employee Id   Name                        Email                           Role      Status");
            for (int i = 0; i < employees.size(); i++) {
                Employee e = employees.get(i);
                String row = String.format(
                        "%-3d %-13s %-27s %-31s %-9s %s",
                        (i + 1),
                        safe(e.getEmployeeId()),
                        truncate(safe(e.getName()), 27),
                        truncate(safe(e.getEmail()), 31),
                        e.getRole() == null ? "-" : e.getRole().name(),
                        e.getStatus() == null ? "-" : e.getStatus().name());
                System.out.println(row);
            }
        }
        System.out.print("Press Enter to continue: ");
        scanner.nextLine();
    }

    private String safe(String value) {
        return value == null ? "-" : value;
    }

    private String truncate(String value, int max) {
        if (value.length() <= max) return value;
        return value.substring(0, max - 1) + "~";
    }
}

