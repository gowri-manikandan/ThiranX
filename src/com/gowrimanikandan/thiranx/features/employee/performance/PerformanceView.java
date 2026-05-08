package com.gowrimanikandan.thiranx.features.employee.performance;

import com.gowrimanikandan.thiranx.data.dto.Employee;
import com.gowrimanikandan.thiranx.data.dto.Performance;
import com.gowrimanikandan.thiranx.data.repository.ThiranXDB;
import com.gowrimanikandan.thiranx.util.ConsoleInput;

import java.util.List;
import java.util.Scanner;

public class PerformanceView {

    private final PerformanceModel performanceModel;
    private final Scanner scanner;

    public PerformanceView() {
        this.performanceModel = new PerformanceModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        showPerformanceMenu();
    }

    private void showPerformanceMenu() {
        while (true) {
            System.out.println();
            System.out.println("Employee Performance Tracking");
            System.out.println("1. View all performance metrics");
            System.out.println("2. View top performers");
            System.out.println("3. View employee performance details");
            System.out.println("4. Return to home");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    displayAllPerformances();
                    break;
                case "2":
                    displayTopPerformers();
                    break;
                case "3":
                    viewEmployeePerformance();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void displayAllPerformances() {
        List<Performance> performances = performanceModel.getPerformancesWithEmployeeDetails();
        System.out.println();
        System.out.println("Employee Performance Metrics");
        if (performances.isEmpty()) {
            System.out.println("No performance data available.");
        } else {
            System.out.println("Employee Name              Total Tasks   Completed   Completion %   Avg Rating");
            System.out.println("---------------------------------------------------------------------");
            for (Performance p : performances) {
                Employee emp = ThiranXDB.getInstance().getEmployeeById(p.getEmployeeId());
                String name = emp != null ? safe(emp.getName()) : "Unknown";
                String row = String.format(
                        "%-25s %-12d %-11d %-13.1f %.1f",
                        truncate(name, 25),
                        p.getTotalTasks(),
                        p.getCompletedTasks(),
                        p.getCompletionRate(),
                        p.getAverageRating());
                System.out.println(row);
            }
        }
        System.out.print("Press Enter to continue: ");
        scanner.nextLine();
    }

    private void displayTopPerformers() {
        List<Performance> topPerformers = performanceModel.getTopPerformers(5);
        System.out.println();
        System.out.println("Top 5 Performers");
        if (topPerformers.isEmpty()) {
            System.out.println("No performance data available.");
        } else {
            System.out.println("Rank  Employee Name              Completion %   Avg Rating   Total Tasks");
            System.out.println("-------------------------------------------------------------------------");
            int rank = 1;
            for (Performance p : topPerformers) {
                Employee emp = ThiranXDB.getInstance().getEmployeeById(p.getEmployeeId());
                String name = emp != null ? safe(emp.getName()) : "Unknown";
                String row = String.format(
                        "%-5d %-25s %-13.1f %-12.1f %d",
                        rank++,
                        truncate(name, 25),
                        p.getCompletionRate(),
                        p.getAverageRating(),
                        p.getTotalTasks());
                System.out.println(row);
            }
        }
        System.out.print("Press Enter to continue: ");
        scanner.nextLine();
    }

    private void viewEmployeePerformance() {
        System.out.print("Enter employee ID or name to search: ");
        String query = scanner.nextLine().trim();
        if (query.isEmpty()) {
            System.out.println("Invalid input.");
            return;
        }

        // Try to find by ID first
        Long employeeId = null;
        try {
            employeeId = Long.parseLong(query);
        } catch (NumberFormatException e) {
            // Not a number, search by name
            List<Employee> employees = ThiranXDB.getInstance().searchEmployees(query);
            if (employees.isEmpty()) {
                System.out.println("Employee not found.");
                return;
            } else if (employees.size() == 1) {
                employeeId = employees.get(0).getId();
            } else {
                System.out.println("Multiple employees found:");
                for (int i = 0; i < employees.size(); i++) {
                    System.out.println((i + 1) + ". " + employees.get(i).getName() + " (" + employees.get(i).getEmployeeId() + ")");
                }
                System.out.print("Select employee number: ");
                try {
                    int selection = Integer.parseInt(scanner.nextLine().trim());
                    if (selection >= 1 && selection <= employees.size()) {
                        employeeId = employees.get(selection - 1).getId();
                    } else {
                        System.out.println("Invalid selection.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input.");
                    return;
                }
            }
        }

        Performance performance = performanceModel.getEmployeePerformance(employeeId);
        Employee employee = ThiranXDB.getInstance().getEmployeeById(employeeId);

        if (performance == null || employee == null) {
            System.out.println("Performance data not found for this employee.");
        } else {
            System.out.println();
            System.out.println("Performance Details for " + employee.getName());
            System.out.println("Employee ID: " + employee.getEmployeeId());
            System.out.println("Total Tasks: " + performance.getTotalTasks());
            System.out.println("Completed Tasks: " + performance.getCompletedTasks());
            System.out.println("Completion Rate: " + String.format("%.1f%%", performance.getCompletionRate()));
            System.out.println("Average Rating: " + String.format("%.1f", performance.getAverageRating()));
            System.out.println("Last Updated: " + new java.util.Date(performance.getLastUpdated()));
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