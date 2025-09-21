// 代码生成时间: 2025-09-21 09:12:12
package com.example.errorlogcollector;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Date;
import java.util.List;

// Define the ErrorLog class to store the error logs
class ErrorLog {
    private Long id;
    private String message;
    private Date timestamp;
    // Default constructor
    public ErrorLog() {
    }
    // Parameterized constructor
    public ErrorLog(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
    // Getter and setter methods
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

// Define the ErrorLogDao class to handle database operations
class ErrorLogDao {
    private SessionFactory sessionFactory;
    // Constructor to initialize the session factory
    public ErrorLogDao() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    // Method to save error log
    public void saveErrorLog(ErrorLog errorLog) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(errorLog);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    // Method to retrieve all error logs
    public List<ErrorLog> getAllErrorLogs() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM ErrorLog", ErrorLog.class).list();
        } finally {
            session.close();
        }
    }
}

// Define the ErrorLogCollector class
public class ErrorLogCollector {
    private ErrorLogDao errorLogDao;
    // Constructor to initialize the DAO
    public ErrorLogCollector() {
        errorLogDao = new ErrorLogDao();
    }
    // Method to collect an error log
    public void collectErrorLog(String message) {
        ErrorLog errorLog = new ErrorLog(message, new Date());
        errorLogDao.saveErrorLog(errorLog);
    }
    // Method to get all error logs
    public List<ErrorLog> getAllErrorLogs() {
        return errorLogDao.getAllErrorLogs();
    }
}
