package com.reliaquest.api.service;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.request.EmployeeCreateRequest;
import java.util.List;

public interface IEmployeeService {
    List<Employee> getAllEmployees();

    List<Employee> getEmployeesByNameSearch(String searchString);

    Employee getEmployeeById(String id);

    Integer getHighestSalaryOfEmployees();

    List<String> getTopTenHighestEarningEmployeeNames();

    Employee createEmployee(EmployeeCreateRequest employeeInput);

    String deleteEmployeeById(String id);
}
