package com.example.employee_management.service;

import com.example.employee_management.Exception.EmployeeNotFoundException;
import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.dto.EmployeeResponse;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceimpl service;

    @Test
    void shouldReturnEmployeeWhenIdExists(){
        Employee employee=
                Employee.builder()
                        .id(1L)
                        .name("Ajay")
                        .email("ajay@test.com")
                        .department("IT")
                        .salary(320000.0)
                        .build();

        when(repository.findById(1L))
                .thenReturn(Optional.of(employee));
        EmployeeResponse response=service.getById(1L);
        assertEquals(
                "Ajay",
                response.getName()
        );
        assertEquals("IT",response.getDepartment());

    }
    @Test
    void shouldThrowExceptionWhenEmployeeNotFound(){
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                ()->service.getById(1L)
        );
    }
    @Test
    void shouldCreateEmployee(){
        EmployeeRequest request=EmployeeRequest.builder()
                .name("Ajay")
                .email("ajay@test.com")
                .department("IT")
                .salary(320000.0)
                .build();
        Employee savedEmployee=
                Employee.builder()
                        .id(1L)
                        .name("Ajay")
                        .email("ajay@test.com")
                        .department("IT")
                        .salary(320000.0)
                        .build();
        when(repository.save(any(Employee.class)))
                .thenReturn(savedEmployee);
        EmployeeResponse response=
                service.create(request);
        verify(repository)
                .save(any(Employee.class));
        assertEquals(
                1L,
                response.getId()
        );

        assertEquals(
                "Ajay",
                request.getName()
        );
    }
    @Test
    void shouldDeleteEmployee(){
        Employee employee=
                Employee.builder()
                        .id(1L)
                        .build();
        when(repository.findById(1L))
                .thenReturn(Optional.of(employee));
        service.delete(1L);

        verify(repository)
                .delete(employee);
    }
    @Test
    void shouldUpdateEmployee() {

        Employee employee =
                Employee.builder()
                        .id(1L)
                        .name("Old Name")
                        .email("old@test.com")
                        .department("HR")
                        .salary(10000.0)
                        .build();

        EmployeeRequest request =
                EmployeeRequest.builder()
                        .name("Ajay")
                        .email("ajay@test.com")
                        .department("IT")
                        .salary(320000.0)
                        .build();

        when(repository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(repository.save(any(Employee.class)))
                .thenReturn(employee);

        EmployeeResponse response =
                service.update(1L, request);

        assertEquals(
                "Ajay",
                response.getName()
        );

        assertEquals(
                "IT",
                response.getDepartment()
        );

        verify(repository)
                .save(employee);
    }
    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingEmployee() {

        EmployeeRequest request =
                EmployeeRequest.builder()
                        .name("Ajay")
                        .email("ajay@test.com")
                        .department("IT")
                        .salary(320000.0)
                        .build();

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                () -> service.update(1L, request)
        );
    }
}
