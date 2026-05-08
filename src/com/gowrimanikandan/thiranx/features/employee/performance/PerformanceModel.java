package com.gowrimanikandan.thiranx.features.employee.performance;

import com.gowrimanikandan.thiranx.data.dto.Employee;
import com.gowrimanikandan.thiranx.data.dto.Performance;
import com.gowrimanikandan.thiranx.data.repository.ThiranXDB;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class PerformanceModel {

    private final PerformanceView performanceView;

    PerformanceModel(PerformanceView performanceView) {
        this.performanceView = performanceView;
    }

    List<Performance> getAllPerformances() {
        return ThiranXDB.getInstance().getAllPerformances();
    }

    List<Performance> getPerformancesWithEmployeeDetails() {
        List<Performance> performances = getAllPerformances();
        return performances.stream()
                .map(this::enrichWithEmployeeDetails)
                .collect(Collectors.toList());
    }

    private Performance enrichWithEmployeeDetails(Performance performance) {
        Employee employee = ThiranXDB.getInstance().getEmployeeById(performance.getEmployeeId());
        // For now, we'll keep it simple and just return the performance
        // In a real app, we might create a combined DTO
        return performance;
    }

    List<Performance> getTopPerformers(int limit) {
        return getAllPerformances().stream()
                .sorted(Comparator.comparing(Performance::getCompletionRate).reversed()
                        .thenComparing(Comparator.comparing(Performance::getAverageRating).reversed()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    Performance getEmployeePerformance(Long employeeId) {
        return ThiranXDB.getInstance().getPerformanceByEmployeeId(employeeId);
    }
}