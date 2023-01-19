package com.skypro.employee.controller;

import com.skypro.employee.exception.InvalidEmployeeRequestException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public Collection<Employee> getAllEmployees() {
        return this.employeeService.getAllEmployees();
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee (@RequestBody EmployeeRequest employeeRequest) {
        try {
        return ResponseEntity.ok (this.employeeService.addEmployee(employeeRequest));
    } catch (InvalidEmployeeRequestException e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping ("/employees/salary/sum")
    public int getSalarySum() {
        return this.employeeService.getSalarySum();

    }


    @GetMapping ("/employees/salary/max")
    public Employee getEmployeeWithMaxSalary () {
        return this.employeeService.getEmployeeWithMaxSalary();
    }

    @GetMapping ("/employees/salary/min")
    public Employee getEmployeeWithMinSalary () {
        return this.employeeService.getEmployeeWithMinSalary();
    }

    @GetMapping ("/employees/salary/high")
    public List<Employee> getEmployeeWithSalaryHigh () {
        return this.employeeService.getEmployeeWithSalaryMoreThanAverage();
    }

}
