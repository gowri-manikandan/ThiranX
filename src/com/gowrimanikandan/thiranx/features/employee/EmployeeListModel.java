package com.gowrimanikandan.thiranx.features.employee;

import com.gowrimanikandan.thiranx.data.dto.Employee;
import com.gowrimanikandan.thiranx.data.repository.ThiranXDB;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class EmployeeListModel {

    private final EmployeeListView employeeListView;

    EmployeeListModel(EmployeeListView employeeListView) {
        this.employeeListView = employeeListView;
    }

    List<Employee> getAllEmployees() {
        return ThiranXDB.getInstance().getEmployees();
    }

    List<Employee> getFilteredAndSortedEmployees(String filterBy, String filterValue, String sortBy, boolean ascending) {
        List<Employee> employees = ThiranXDB.getInstance().getEmployees();

        // Apply filtering
        if (filterBy != null && !filterBy.isEmpty() && filterValue != null && !filterValue.isEmpty()) {
            employees = employees.stream()
                    .filter(emp -> matchesFilter(emp, filterBy, filterValue))
                    .collect(Collectors.toList());
        }

        // Apply sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            Comparator<Employee> comparator = getComparator(sortBy);
            if (!ascending) {
                comparator = comparator.reversed();
            }
            employees.sort(comparator);
        }

        return employees;
    }

    private boolean matchesFilter(Employee emp, String filterBy, String filterValue) {
        switch (filterBy.toLowerCase()) {
            case "name":
                return emp.getName() != null && emp.getName().toLowerCase().contains(filterValue.toLowerCase());
            case "email":
                return emp.getEmail() != null && emp.getEmail().toLowerCase().contains(filterValue.toLowerCase());
            case "role":
                return emp.getRole() != null && emp.getRole().name().toLowerCase().contains(filterValue.toLowerCase());
            case "status":
                return emp.getStatus() != null && emp.getStatus().name().toLowerCase().contains(filterValue.toLowerCase());
            default:
                return true;
        }
    }

    private Comparator<Employee> getComparator(String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "name":
                return Comparator.comparing(emp -> emp.getName() != null ? emp.getName() : "", String.CASE_INSENSITIVE_ORDER);
            case "email":
                return Comparator.comparing(emp -> emp.getEmail() != null ? emp.getEmail() : "", String.CASE_INSENSITIVE_ORDER);
            case "role":
                return Comparator.comparing(emp -> emp.getRole() != null ? emp.getRole().name() : "", String.CASE_INSENSITIVE_ORDER);
            case "status":
                return Comparator.comparing(emp -> emp.getStatus() != null ? emp.getStatus().name() : "", String.CASE_INSENSITIVE_ORDER);
            case "created":
                return Comparator.comparing(emp -> emp.getCreatedAt() != null ? emp.getCreatedAt() : 0L);
            default:
                return Comparator.comparing(emp -> emp.getName() != null ? emp.getName() : "", String.CASE_INSENSITIVE_ORDER);
        }
    }
}

