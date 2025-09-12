// 代码生成时间: 2025-09-13 03:04:17
package com.example.api;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiResponseFormatter {

    // Persistence Context for Hibernate
    @PersistenceContext
    private EntityManager entityManager;

    private static final String DEFAULT_SUCCESS_MESSAGE = "Operation was successful";
    private static final String DEFAULT_ERROR_MESSAGE = "An error occurred during the operation";

    public Map<String, Object> formatSuccessResponse(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", DEFAULT_SUCCESS_MESSAGE);
        response.put("data", data);
        return response;
    }

    public Map<String, Object> formatErrorResponse(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", DEFAULT_ERROR_MESSAGE + ": " + e.getMessage());
        return response;
    }

    // Example method that performs a database operation and formats the response
    public Map<String, Object> executeQuery(String hql) {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query<?> query = session.createQuery(hql);
            List<?> results = query.getResultList();
            return formatSuccessResponse(results);
        } catch (Exception e) {
            return formatErrorResponse(e);
        }
    }

    // Additional methods for API response formatting can be added here
    // ...
}
