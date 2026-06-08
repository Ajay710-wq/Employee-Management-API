package com.example.employee_management.dto;

import lombok.Data;

@Data
public class EmployeeFilterRequest {
    private String name;
    private String department;
    private Double minSalary;
    private Double maxSalary;
}
