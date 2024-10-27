package com.reliaquest.api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.api.common.Constants;
import com.reliaquest.api.exception.ExceptionHandler;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.Response;
import com.reliaquest.api.request.EmployeeCreateRequest;
import com.reliaquest.api.util.EmployeeProcessor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final ApiService apiService;

    private final EmployeeProcessor employeeProcessor;

    private final ExceptionHandler exceptionHandler;

    private final ObjectMapper objectMapper;

    /**
     * Fetches complete employee list from server API.
     *
     * @return List of Employees
     */
    @Override
    public List<Employee> getAllEmployees() {
        log.info("Fetching All employees from server API");
        ResponseEntity<JsonNode> responseEntity = apiService.get(Constants.REST_EMPLOYEE_URI);
        return processResponse(responseEntity, new TypeReference<List<Employee>>() {});
    }

    /**
     * Fetches Employee with name containing provided string
     *
     * @param searchString String to search in employee names
     * @return List of Employees
     */
    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        String searchStringLowerCase = searchString.toLowerCase();
        List<Employee> employeeList = getAllEmployees();
        return employeeProcessor.getAllEmployeesWithMatchingName(employeeList, searchStringLowerCase);
    }

    /**
     * Fetches Employee with given Id
     *
     * @param id Employee ID
     * @return Employee
     */
    @Override
    public Employee getEmployeeById(String id) {
        log.info("Fetching Employee with given ID: {}", id);
        ResponseEntity<JsonNode> responseEntity = apiService.get(Constants.REST_EMPLOYEE_URI + "/" + id);
        return processResponse(responseEntity, new TypeReference<>() {});
    }

    /**
     * Fetches highest salary out of all Employees
     *
     * @return Highest salary
     */
    @Override
    public Integer getHighestSalaryOfEmployees() {
        List<Employee> employeeList = getAllEmployees();
        return employeeProcessor.getHighestSalaryOfAllEmployees(employeeList);
    }

    /**
     * Fetches top 10 employees with Highest salary
     *
     * @return List of Employee names
     */
    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        List<Employee> employeeList = getAllEmployees();
        List<String> sortedEmployeeNames = employeeProcessor.getEmployeesNamesSortedBySalary(employeeList);
        return sortedEmployeeNames.subList(0, Math.min(sortedEmployeeNames.size(), 10));
    }

    /**
     * Create employee given the necessary parameters
     *
     * @param employeeRequest Employee parameters to create the new Employee with
     * @return Employee
     */
    @Override
    public Employee createEmployee(EmployeeCreateRequest employeeRequest) {
        log.info("Creating employee with provided parameters: {}", employeeRequest);
        ResponseEntity<JsonNode> responseEntity = apiService.post(Constants.REST_EMPLOYEE_URI, employeeRequest);
        return processResponse(responseEntity, new TypeReference<>() {});
    }

    /**
     * Delete employee given its Employee ID
     *
     * @param id Employee ID
     * @return Employee name
     */
    @Override
    public String deleteEmployeeById(String id) {
        Employee employee = getEmployeeById(id);
        String employeeName = employee.getName();

        log.info("Deleting employee record with given ID: {}", id);
        apiService.delete(Constants.REST_EMPLOYEE_URI, employeeName);
        return employeeName;
    }

    /**
     * Helps in processing response sent from server API
     *
     * @param responseEntity Entity returned by Server API
     * @param responseType   Object Type of Entity returned
     * @return Parsed entity
     */
    private <T> T processResponse(ResponseEntity<JsonNode> responseEntity, TypeReference<T> responseType) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Response response = objectMapper.convertValue(responseEntity.getBody(), Response.class);
            if (Constants.SUCCESS.equalsIgnoreCase(response.status())) {
                return objectMapper.convertValue(response.data(), responseType);
            } else {
                throw new RuntimeException("Internal Server Error");
            }
        } else {
            throw exceptionHandler.exceptionByStatus(responseEntity);
        }
    }
}
