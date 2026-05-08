package com.gowrimanikandan.thiranx.features.employee;

import com.gowrimanikandan.thiranx.data.dto.Employee;
import com.gowrimanikandan.thiranx.data.repository.ThiranXDB;

import java.util.List;

class EmployeeSearchModel {

    private final EmployeeSearchView employeeSearchView;

    EmployeeSearchModel(EmployeeSearchView employeeSearchView) {
        this.employeeSearchView = employeeSearchView;
    }

    List<Employee> searchEmployees(String query) {
        return ThiranXDB.getInstance().searchEmployees(query);
    }
}

