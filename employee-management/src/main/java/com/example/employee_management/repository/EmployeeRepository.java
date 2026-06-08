package com.example.employee_management.repository;

import com.example.employee_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee , Long>, JpaSpecificationExecutor<Employee> {
    List<Employee> findByNameContainingIgnoreCase(String name);
    @Query("""
select e from Employee e where lower(e.department)=lower(:department)""")
    List<Employee> findEmployeesByDepartment(@Param("department") String department);
}
