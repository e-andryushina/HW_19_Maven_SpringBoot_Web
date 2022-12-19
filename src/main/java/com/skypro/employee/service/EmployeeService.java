package com.skypro.employee.service;

import com.skypro.employee.exception.EmployeeNotFoundException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();

    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee (EmployeeRequest employeeRequest) {
        if(employeeRequest.getFirstName() == null
                || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Пожалуйста, введите данные");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());

        this.employees.put(employee.getId(), employee);
        return employee;
    }

    public int getSalarySum() {
        return employees.values().stream()
                .mapToInt(Employee :: getSalary)
                .sum();
    }



}
