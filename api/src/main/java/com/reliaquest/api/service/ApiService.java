package com.reliaquest.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.reliaquest.api.request.EmployeeCreateRequest;
import com.reliaquest.api.request.EmployeeDeleteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Service class to handle interaction with Server API
 */
@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base.uri}")
    private String apiBaseUri;

    /**
     * Makes GET call to Server API endpoint
     */
    public ResponseEntity<JsonNode> get(String endpointUri) {
        return restTemplate.getForEntity(apiBaseUri + endpointUri, JsonNode.class);
    }

    /**
     * Makes post call to Server API endpoint with given post body
     */
    public ResponseEntity<JsonNode> post(String endpointUri, EmployeeCreateRequest employeeRequest) {
        return restTemplate.postForEntity(apiBaseUri + endpointUri, employeeRequest, JsonNode.class);
    }

    /**
     * Makes delete call to Server API endpoint with employee name to be deleted
     */
    public ResponseEntity<JsonNode> delete(String endpointUri, String name) {
        HttpEntity<EmployeeDeleteRequest> employeeDeleteRequest = new HttpEntity<>(new EmployeeDeleteRequest(name));
        return restTemplate.exchange(apiBaseUri + endpointUri, HttpMethod.DELETE, employeeDeleteRequest, JsonNode.class);
    }
}
