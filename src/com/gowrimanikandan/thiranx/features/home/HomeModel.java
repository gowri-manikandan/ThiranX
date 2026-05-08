package com.gowrimanikandan.thiranx.features.home;

import com.gowrimanikandan.thiranx.data.dto.Employee;

class HomeModel {

    private final HomeView homeView;

    HomeModel(HomeView homeView) {
        this.homeView = homeView;
    }

    void init(Employee employee) {
        if (employee == null || employee.getRole() == null) {
            homeView.showUnauthorized();
            return;
        }
        if (employee.getRole() == Employee.Role.MANAGER) {
            homeView.showManagerMenu();
        } else {
            homeView.showEmployeeMenu();
        }
    }
}
