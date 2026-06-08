package com.example.employee_management.service;

import com.example.employee_management.EmployeeManagementApplication;
import com.example.employee_management.dto.EmployeeFilterRequest;
import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.dto.EmployeeResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse create(EmployeeRequest request);
    EmployeeResponse getById(Long id);
    Page<EmployeeResponse> getAll(int page, int size, String sortBy , String direction);
    EmployeeResponse update(Long id, EmployeeRequest request);

    void delete(Long id);

    List<EmployeeResponse> searchByName(String name);
    List<EmployeeResponse> getEmployeesByDepartment(String department);
    List<EmployeeResponse> filterEmployees(EmployeeFilterRequest request);
}
