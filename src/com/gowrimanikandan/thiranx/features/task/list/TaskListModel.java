package com.gowrimanikandan.thiranx.features.task.list;

import com.gowrimanikandan.thiranx.data.dto.Task;
import com.gowrimanikandan.thiranx.data.repository.ThiranXDB;

import java.util.List;

class TaskListModel {

    private final TaskListView taskListView;

    TaskListModel(TaskListView taskListView) {
        this.taskListView = taskListView;
    }

    List<Task> getMyTasks(Long employeeId) {
        return ThiranXDB.getInstance().getTasksAssignedTo(employeeId);
    }

    String getEmployeeName(Long id) {
        if (id == null) return "-";
        com.gowrimanikandan.thiranx.data.dto.Employee employee = ThiranXDB.getInstance().getEmployeeById(id);
        return (employee == null || employee.getName() == null) ? "-" : employee.getName();
    }
}

