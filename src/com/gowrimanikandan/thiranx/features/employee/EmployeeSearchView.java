package com.gowrimanikandan.thiranx.features.employee;

import com.gowrimanikandan.thiranx.data.dto.Employee;
import com.gowrimanikandan.thiranx.util.ConsoleInput;

import java.util.List;
import java.util.Scanner;

public class EmployeeSearchView {

    private final EmployeeSearchModel employeeSearchModel;
    private final Scanner scanner;

    public EmployeeSearchView() {
        this.employeeSearchModel = new EmployeeSearchModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        System.out.println();
        System.out.println("Search Employees");
        System.out.print("Enter name, email or employee id: ");
        String query = scanner.nextLine();

        List<Employee> employees = employeeSearchModel.searchEmployees(query);
        System.out.println();
        if (employees.isEmpty()) {
            System.out.println("No employees found for '" + (query == null ? "" : query.trim()) + "'.");
        } else {
            System.out.println("Search Results");
            System.out.println(
                    "#   Employee Id   Name                        Email                           Role      Status");
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
        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }

    private String safe(String value) {
        return value == null ? "-" : value;
    }

    private String truncate(String value, int max) {
        if (value.length() <= max)
            return value;
        return value.substring(0, max - 1) + "~";
    }
}

