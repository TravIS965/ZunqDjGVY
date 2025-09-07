// 代码生成时间: 2025-09-07 19:38:16
package com.example.audit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 实体类代表安全审计日志
 */
@Entity
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private String username;
    private LocalDateTime timestamp;
    private String details;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

/**
 * 提供安全审计日志记录的服务类
 */
public class AuditLogService {

    private static final Logger logger = LoggerFactory.getLogger(AuditLogService.class);

    private final SessionFactory sessionFactory;

    public AuditLogService() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    /**
     * 记录一个安全审计日志条目
     */
    public void log(String action, String username, String details) {
        try (Session session = sessionFactory.openSession()) {
            AuditLog auditLog = new AuditLog();
            auditLog.setAction(action);
            auditLog.setUsername(username);
            auditLog.setTimestamp(LocalDateTime.now());
            auditLog.setDetails(details);
            session.save(auditLog);
            logger.info("Audit log entry created: {}", auditLog);
        } catch (Exception e) {
            logger.error("Error creating audit log entry", e);
        }
    }

    // 其他方法，如查询审计日志等，可以在这里添加
}
