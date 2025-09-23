// 代码生成时间: 2025-09-23 12:49:33
package com.example.logging;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

// Define the ErrorLog class to store error log information
class ErrorLog {
    private Long id;
    private String message;
    private Date timestamp;
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}

public class ErrorLogCollector {
    private static final Logger logger = LoggerFactory.getLogger(ErrorLogCollector.class);
    private static SessionFactory sessionFactory;

    static {
        // Initialize the Hibernate SessionFactory
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Method to collect and store an error log
    public void collectErrorLog(String errorMessage) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                // Create a new ErrorLog instance
                ErrorLog errorLog = new ErrorLog();
                errorLog.setMessage(errorMessage);
                errorLog.setTimestamp(new Date());

                // Persist the error log to the database
                session.save(errorLog);
                transaction.commit();
                logger.info("Error log collected successfully");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                logger.error("Error collecting error log", e);
            }
        } catch (Exception e) {
            logger.error("Error accessing session factory", e);
        }
    }

    // Method to retrieve error logs from the database
    public List<ErrorLog> getErrorLogs() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from ErrorLog", ErrorLog.class).list();
        } catch (Exception e) {
            logger.error("Error retrieving error logs", e);
            return null;
        }
    }

    // Main method for demonstration purposes
    public static void main(String[] args) {
        ErrorLogCollector collector = new ErrorLogCollector();
        collector.collectErrorLog("Sample error message");
        List<ErrorLog> logs = collector.getErrorLogs();
        if (logs != null) {
            for (ErrorLog log : logs) {
                System.out.println("Error: " + log.getMessage() + ", Time: " + log.getTimestamp());
            }
        }
    }
}