package com.example.employee_management.specification;

import com.example.employee_management.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {
    public static Specification<Employee> hasDepartment(String department){
        return ((root, query, cb) ->
                cb.equal(
                        cb.lower(root.get("department")),
                        department.toLowerCase()
                ));
    }
    public static Specification<Employee> nameContains(String name){
        return ((root, query, cb) ->
                cb.like(
                        cb.lower(root.get("name")),
                        "%"+name.toLowerCase()+"%"
                ));
    }

    public static Specification<Employee> salaryGreaterThan(Double salary){
        return ((root, query, cb) ->
                cb.greaterThan(root.get("salary"),
                        salary));
    }

    public static Specification<Employee> salaryLessThan(Double salary){
        return ((root, query, cb) ->
                cb.lessThan(root.get("salary"),
                        salary));
    }
}
