package com.example.employee_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeRequest {
    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String department;

    @Positive
    private Double salary;

}
