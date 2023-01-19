package com.skypro.employee;

import com.skypro.employee.exception.EmployeeNotFoundException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.DepartmentService;
import com.skypro.employee.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    private final List<Employee> employees = List.of(
            new Employee("TestFirst", "TestFirst", 1, 3000),
            new Employee("TestSecond", "TestSecond", 1, 4000),
            new Employee("TestThird", "TestThird", 3, 5000),
            new Employee("TestFourth", "TestFourth", 2, 6000),
            new Employee("TestFifth", "TestFifth", 3, 7000),
            new Employee("TestSixth", "TestSixth", 2, 2000)
    );
    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setup() {
        when(employeeService.getAllEmployees())
                .thenReturn(employees);
    }


    @Test
    void getEmployeesOfDepartment() {
        Collection<Employee> employeeList = this.departmentService.getEmployeesOfDepartment(2);
        assertThat(employeeList)
                .hasSize(2)
                .contains(employees.get(3),
                        employees.get(5));

    }

    @Test
    void getSalarySumOfDepartment() {
        int salarySum = this.departmentService.getSalarySumOfDepartment(1);
        assertEquals(salarySum, 7_000);
    }

    @Test
    void getSalaryMaxOfDepartment() {
        int salarySum = this.departmentService.getSalaryMaxOfDepartment(3);
        assertEquals(salarySum, 7_000);
    }

    @Test
    void getSalaryMinOfDepartment() {
        int salarySum = this.departmentService.getSalaryMinOfDepartment(2);
        assertEquals(salarySum, 2_000);
    }

    @Test
    void getEmployeesGroupedByDepartment() {
        Map<Integer, List<Employee>> employeesByDepartments = departmentService.getEmployeesGroupedByDepartment();
        assertThat(employeesByDepartments)
                .hasSize(3)
                .containsEntry(1, List.of(employees.get(0), employees.get(1)))
                .containsEntry(2, List.of(employees.get(3), employees.get(5)))
                .containsEntry(3, List.of(employees.get(2), employees.get(4)));
    }

    @Test
    void WhenNoEmployeesThenReturnGroupByEmptyCase(){
         when(employeeService.getAllEmployees()).thenReturn(List.of());
         Map<Integer, List<Employee>> groupedEmployees = this.departmentService
                 .getEmployeesGroupedByDepartment();
         assertThat(groupedEmployees).isEmpty();
     }


     @Test
    void WhenNoEmployeesThenEmployeeMaxSalaryThrowsException(){
         when(employeeService.getAllEmployees()).thenReturn(List.of());
         assertThrows(EmployeeNotFoundException.class,
                 () -> departmentService.getSalaryMaxOfDepartment(1));
     }

    @Test
    void WhenNoEmployeesThenEmployeeMinSalaryThrowsException(){
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        assertThrows(EmployeeNotFoundException.class,
                () -> departmentService.getSalaryMinOfDepartment(1));
    }

}
