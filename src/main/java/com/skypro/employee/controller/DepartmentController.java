package com.skypro.employee.controller;

import com.skypro.employee.model.Employee;
import com.skypro.employee.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@RestController
public class DepartmentController {
    DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("employees/departments")
    public Map<Integer, List<Employee>> getEmployeesGroupedByDepartment () {
        return this.departmentService.getEmployeesGroupedByDepartment();
    }

    @GetMapping("employees/departments/department")
    public Collection<Employee> getEmployeesOfDepartment (@RequestParam ("dpt") int idOfDepartment){
        return this.departmentService.getEmployeesOfDepartment(idOfDepartment);
    }

    @GetMapping("employees/departments/department/salary/sum")
    public int getSalarySumOfDepartment (@RequestParam ("dpt") int idOfDepartment) {
        return this.departmentService.getSalarySumOfDepartment(idOfDepartment);
    }

    @GetMapping("employees/departments/department/salary/max")
    public int getSalaryMaxOfDepartment(@RequestParam ("dpt") int idOfDepartment) {
        return this.departmentService.getSalaryMaxOfDepartment(idOfDepartment);
    }


    @GetMapping("employees/departments/department/salary/min")
    public int getSalaryMinOfDepartment(@RequestParam ("dpt") int idOfDepartment) {
        return this.departmentService.getSalaryMinOfDepartment(idOfDepartment);
    }


}
