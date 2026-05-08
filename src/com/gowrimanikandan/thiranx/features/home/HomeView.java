package com.gowrimanikandan.thiranx.features.home;

import com.gowrimanikandan.thiranx.data.dto.Employee;
import com.gowrimanikandan.thiranx.features.employee.EmployeeListView;
import com.gowrimanikandan.thiranx.features.employee.EmployeeSearchView;
import com.gowrimanikandan.thiranx.features.employee.add.EmployeeAddView;
import com.gowrimanikandan.thiranx.features.employee.performance.PerformanceView;
import com.gowrimanikandan.thiranx.features.employee.reportee.ReporteeListView;
import com.gowrimanikandan.thiranx.features.notification.NotificationView;
import com.gowrimanikandan.thiranx.features.report.ReportView;
import com.gowrimanikandan.thiranx.features.task.assign.AssignMode;
import com.gowrimanikandan.thiranx.features.task.assign.TaskAssignView;
import com.gowrimanikandan.thiranx.features.task.create.TaskCreateView;
import com.gowrimanikandan.thiranx.features.task.detail.TaskDetailView;
import com.gowrimanikandan.thiranx.features.task.list.TaskListView;
import com.gowrimanikandan.thiranx.features.task.status.TaskStatusUpdateView;
import com.gowrimanikandan.thiranx.features.task.team.TeamStatusView;
import com.gowrimanikandan.thiranx.util.ConsoleInput;

import java.util.Scanner;

public class HomeView {

    private final HomeModel homeModel;
    private final Employee employee;
    private final Scanner scanner;

    public HomeView(Employee employee) {
        this.homeModel = new HomeModel(this);
        this.employee = employee;
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        homeModel.init(employee);
    }

    void showUnauthorized() {
        System.out.println("Your account role is not set. Contact your administrator.");
    }

    void showManagerMenu() {
        while (true) {
            System.out.println();
            System.out.println("Manager Home");
            System.out.println("1.  View all employees");
            System.out.println("2.  Search employees");
            System.out.println("3.  View reportees");
            System.out.println("4.  Add employee");
            System.out.println("5.  Add new task");
            System.out.println("6.  Assign a task");
            System.out.println("7.  View team status");
            System.out.println("8.  Update my task status");
            System.out.println("9.  View task details");
            System.out.println("10. Notifications");
            System.out.println("11. View reports");
            System.out.println("12. Performance tracking");
            System.out.println("13. Sign out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    new EmployeeListView().init();
                    break;
                case "2":
                    new EmployeeSearchView().init();
                    break;
                case "3":
                    new ReporteeListView(employee).init();
                    break;
                case "4":
                    new EmployeeAddView(employee).init();
                    break;
                case "5":
                    new TaskCreateView(employee).init();
                    break;
                case "6":
                    new TaskAssignView(employee, AssignMode.MANAGER_ASSIGN).init();
                    break;
                case "7":
                    new TeamStatusView(employee).init();
                    break;
                case "8":
                    new TaskStatusUpdateView(employee).init();
                    break;
                case "9":
                    new TaskDetailView(employee).init();
                    break;
                case "10":
                    new NotificationView(employee).init();
                    break;
                case "11":
                    new ReportView().init();
                    break;
                case "12":
                    new PerformanceView().init();
                    break;
                case "13":
                    System.out.println("You have been signed out.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    void showEmployeeMenu() {
        while (true) {
            System.out.println();
            System.out.println("Employee Home");
            System.out.println("1. My tasks");
            System.out.println("2. Update task status");
            System.out.println("3. Reassign a task");
            System.out.println("4. View task details");
            System.out.println("5. Notifications");
            System.out.println("6. Sign out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    new TaskListView(employee).init();
                    break;
                case "2":
                    new TaskStatusUpdateView(employee).init();
                    break;
                case "3":
                    new TaskAssignView(employee, AssignMode.EMPLOYEE_REASSIGN).init();
                    break;
                case "4":
                    new TaskDetailView(employee).init();
                    break;
                case "5":
                    new NotificationView(employee).init();
                    break;
                case "6":
                    System.out.println("You have been signed out.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

