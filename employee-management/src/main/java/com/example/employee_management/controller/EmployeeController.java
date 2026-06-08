package com.example.employee_management.controller;

import com.example.employee_management.dto.EmployeeFilterRequest;
import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.dto.EmployeeResponse;
import com.example.employee_management.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    public EmployeeResponse create(@Valid @RequestBody EmployeeRequest request){
        return service.create(request);
    }

    @GetMapping("/{id}")
    public EmployeeResponse getById(@PathVariable Long id){
        return service.getById(id);
    }

    @GetMapping
    public Page<EmployeeResponse> getAll(@RequestParam(defaultValue = "0")int page ,
                                         @RequestParam(defaultValue = "5")int size,
                                         @RequestParam(defaultValue = "id")String sortBy,
                                         @RequestParam(defaultValue = "asc")String direction){
        return service.getAll(page, size , sortBy ,direction);

    }
    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable Long id , @Valid @RequestBody EmployeeRequest request){
        return service.update(id, request);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("/search")
    public List<EmployeeResponse> searchByName(@RequestParam String name){
        return service.searchByName(name);
    }

    @GetMapping("/department-query")
    public List<EmployeeResponse> getEmployeesByDepartment(@RequestParam String department){
        return service.getEmployeesByDepartment(department);
    }

    @GetMapping("/filter")
    public List<EmployeeResponse> filterEmployees(@RequestParam(required = false)String name,
                                                  @RequestParam(required = false)String department,
                                                  @RequestParam(required = false)Double minSalary,
                                                  @RequestParam(required = false)Double maxSalary){
        EmployeeFilterRequest request=new EmployeeFilterRequest();
        request.setName(name);
        request.setDepartment(department);
        request.setMinSalary(minSalary);
        request.setMaxSalary(maxSalary);
        return service.filterEmployees(request);
    }
}
