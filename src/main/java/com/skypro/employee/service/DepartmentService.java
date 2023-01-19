package com.skypro.employee.service;

import com.skypro.employee.exception.EmployeeNotFoundException;
import com.skypro.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DepartmentService {
    EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Collection<Employee> getEmployeesOfDepartment (int department) {
        return getEmployeesByDepartmentStream(department)
                .collect(Collectors.toList());
    }

    public int getSalarySumOfDepartment (int department) {
        return getEmployeesByDepartmentStream(department)
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public int getSalaryMaxOfDepartment (int department) {
        return getEmployeesByDepartmentStream(department)
                .mapToInt(Employee::getSalary)
                .max()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public int getSalaryMinOfDepartment (int department) {
        return getEmployeesByDepartmentStream(department)
                .mapToInt(Employee::getSalary)
                .min()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public Map<Integer, List<Employee>> getEmployeesGroupedByDepartment () {
        return employeeService
                .getAllEmployees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    private Stream<Employee> getEmployeesByDepartmentStream (int department) {
        return employeeService.getAllEmployees()
                .stream()
                .filter(e -> e.getDepartment() == department);
    }



}
