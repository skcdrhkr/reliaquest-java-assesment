package com.reliaquest.api.controller;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.request.EmployeeCreateRequest;
import com.reliaquest.api.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class that provides definition to all required endpoints
 */
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController implements IEmployeeController<Employee, EmployeeCreateRequest> {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        List<Employee> employeeList = employeeService.getEmployeesByNameSearch(searchString);
        return ResponseEntity.ok(employeeList);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        List<String> top10HighlyPaidEmployees = employeeService.getTopTenHighestEarningEmployeeNames();
        return ResponseEntity.ok(top10HighlyPaidEmployees);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(EmployeeCreateRequest employeeRequest) {
        Employee employee = employeeService.createEmployee(employeeRequest);
        return ResponseEntity.ok(employee);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        String name = employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok(name);
    }
}
