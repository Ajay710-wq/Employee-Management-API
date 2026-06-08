package com.example.employee_management.service;

import com.example.employee_management.Exception.EmployeeNotFoundException;
import com.example.employee_management.dto.EmployeeFilterRequest;
import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.dto.EmployeeResponse;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.specification.EmployeeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
@Service
@RequiredArgsConstructor
public class EmployeeServiceimpl implements EmployeeService{

    private final EmployeeRepository repository;

    @Override
    public EmployeeResponse create(EmployeeRequest request) {
        Employee employee=
                Employee.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .department(request.getDepartment())
                        .salary(request.getSalary())
                        .build();
        Employee saved=repository.save(employee);
        return mapToResponse(saved);
    }



    @Override
    public EmployeeResponse getById(Long id) {
        Employee employee=
                repository.findById(id)
                        .orElseThrow(()->
                                new EmployeeNotFoundException(id));
        return mapToResponse(employee);
    }

    @Override
    public Page<EmployeeResponse> getAll(int page, int size , String sortBy , String direction) {

        Sort sort=direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable=PageRequest.of(page,size,sort);

        Page<Employee> employeePage=repository.findAll(pageable);

        return employeePage.map(this::mapToResponse);
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee=repository.findById(id)
                .orElseThrow(()->
                        new EmployeeNotFoundException(id));

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());

        Employee updated=repository.save(employee);

        return mapToResponse(updated);
    }

    @Override
    public void delete(Long id) {
        Employee employee=
                repository.findById(id)
                        .orElseThrow(()->
                                new EmployeeNotFoundException(id));
        repository.delete(employee);

    }

    @Override
    public List<EmployeeResponse> searchByName(String name) {
        return repository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EmployeeResponse> getEmployeesByDepartment(String department) {
        return repository
                .findEmployeesByDepartment(department)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EmployeeResponse> filterEmployees(EmployeeFilterRequest request) {
        Specification<Employee> specification=Specification.where((Specification<Employee>) null);

        if (request.getName() != null){
            specification=
                    specification.and(
                            EmployeeSpecification.nameContains(
                                    request.getName()
                            )
                    );
        }
        if (request.getDepartment() != null){
            specification=specification.and(
                    EmployeeSpecification.hasDepartment(request.getDepartment())
            );
        }
        if (request.getMinSalary() != null){
            specification=specification.and(
                    EmployeeSpecification.salaryGreaterThan(
                            request.getMinSalary()
                    )
            );
        }
        if (request.getMaxSalary() != null){
            specification=specification.and(
                    EmployeeSpecification
                            .salaryLessThan(request.getMaxSalary())
            );
        }
        return repository
                .findAll(specification)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .salary(employee.getSalary())
                .build();
    }
}