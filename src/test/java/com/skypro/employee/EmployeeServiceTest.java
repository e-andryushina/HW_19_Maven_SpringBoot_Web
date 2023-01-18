package com.skypro.employee;

import com.skypro.employee.exception.EmployeeNotFoundException;
import com.skypro.employee.exception.InvalidEmployeeRequestException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmployeeServiceTest {

    private EmployeeService employeeService;


    @BeforeEach
    public void setup() {
        this.employeeService = new EmployeeService();
        Stream.of(
                new EmployeeRequest("TestFirst", "TestFirst", 1, 3000),
                new EmployeeRequest("TestSecond", "TestSecond", 1, 4000),
                new EmployeeRequest("TestThird", "TestThird", 3, 5000),
                new EmployeeRequest("TestFourth", "TestFourth", 2, 6000),
                new EmployeeRequest("TestFifth", "TestFifth", 3, 7000),
                new EmployeeRequest("TestSixth", "TestSixth", 2, 2000)
        ).forEach(employeeService::addEmployee);
    }

    @Test
    public void addEmployee() {
        EmployeeRequest request = new EmployeeRequest(
                "Request", "Request", 2, 8000);
        Employee result = employeeService.addEmployee(request);
        assertEquals(request.getFirstName(), result.getFirstName());
        assertEquals(request.getLastName(), result.getLastName());
        assertEquals(request.getDepartment(), result.getDepartment());
        assertEquals(request.getSalary(), result.getSalary());

        Assertions.assertThat(employeeService.getAllEmployees())
                .contains(result);
    }

    @Test
    public void listOfEmployees() {
        Collection<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(6);
        Assertions.assertThat(employees)
                .elements(0, 1)
                .extracting(Employee::getFirstName)
                .contains("TestFirst", "TestSecond");
    }

    @Test
    public void sumOfSalaries() {
        int salarySum = employeeService.getSalarySum();
        Assertions.assertThat(salarySum).isEqualTo(27_000);
    }

    @Test
    public void employeeWithMaxSalary() {
        Employee employee = employeeService.getEmployeeWithMaxSalary();
        Assertions.assertThat(employee)
                .isNotNull()
                .extracting(Employee::getFirstName)
                .isEqualTo("TestFifth");
    }

    @Test
    public void employeeWithMinSalary() {
        Employee employee = employeeService.getEmployeeWithMinSalary();
        Assertions.assertThat(employee)
                .isNotNull()
                .extracting(Employee::getLastName)
                .isEqualTo("TestSixth");
    }

    @Test
    public void throwInvalidEmployeeRequestException() {
        EmployeeRequest request = new EmployeeRequest(
                "Request111", "Request111", 2, 8000);
        assertThrows(InvalidEmployeeRequestException.class,
                () -> employeeService.addEmployee(request));
    }

    @Test
    public void throwEmployeeNotFoundException() {
        EmployeeService employeeService1 = new EmployeeService();
        assertThrows(EmployeeNotFoundException.class,
                employeeService1::getEmployeeWithMaxSalary);
    }

    @Test
    public void employeeWithSalaryMoreThanAverage() {
        Collection<Employee> request = employeeService.getEmployeeWithSalaryMoreThanAverage();
        Assertions.assertThat(request)
                .hasSize(3)
                .extracting(Employee::getFirstName)
                .contains("TestThird", "TestFourth", "TestFifth");
    }

    @Test
    public void removeEmployee() {
        employeeService.removeEmployee(employeeService
                .getAllEmployees()
                .iterator()
                .next()
                .getId());

        Collection<Employee> employees = employeeService.getAllEmployees();

        Assertions.assertThat(employees)
                .hasSize(5)
                .extracting(Employee::getFirstName)
                .first()
                .isNotEqualTo("TestFirst");
    }

    @Test
    public void WhenSymbolIsNotCapitalThenItBecomesCapital() {
        EmployeeRequest request = new EmployeeRequest(
                "request", "request", 2, 8000);
        Employee result = employeeService.addEmployee(request);
        Assertions.assertThat(result.getFirstName())
                .isMixedCase();
        Assertions.assertThat(result.getLastName())
                .isMixedCase();
    }
}





