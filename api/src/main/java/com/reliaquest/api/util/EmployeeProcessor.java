package com.reliaquest.api.util;

import com.reliaquest.api.model.Employee;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Employee Processor class that helps in fetching Employees based on required criteria
 */
@Slf4j
@Component
public class EmployeeProcessor {

    /**
     * Filters Employee whose name contains the provided string ignoring case.
     *
     * @param employeeList
     * @param searchStringLowerCase
     * @return
     */
    public List<Employee> getAllEmployeesWithMatchingName(List<Employee> employeeList, String searchStringLowerCase) {
        log.info("Filtering Employees with name containing: {}", searchStringLowerCase);
        return employeeList.stream()
                .filter(emp ->
                        emp.getName() != null && emp.getName().toLowerCase().contains(searchStringLowerCase))
                .collect(Collectors.toList());
    }

    /**
     * Filters highest salary from complete employee list
     *
     * @param employeeList
     * @return
     */
    public Integer getHighestSalaryOfAllEmployees(List<Employee> employeeList) {
        log.info("Extracting highest salary from employee list");
        return employeeList.stream()
                .map(Employee::getSalary)
                .max(Comparator.comparingInt(x -> x))
                .get();
    }

    /**
     * Extracts employee names sorted by salary in descending order
     *
     * @param employeeList
     * @return
     */
    public List<String> getEmployeesNamesSortedBySalary(List<Employee> employeeList) {
        log.info("Sorting names of employees by highest salary to lowest");
        return employeeList.stream()
                .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                .map(Employee::getName)
                .collect(Collectors.toList());
    }
}
