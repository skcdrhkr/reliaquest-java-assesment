package com.reliaquest.api.request;

/**
 * Request record for Employee create endpoint
 */
public record EmployeeCreateRequest(String name, Integer salary, Integer age, String title, String email) {
}
