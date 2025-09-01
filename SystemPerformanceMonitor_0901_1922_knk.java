// 代码生成时间: 2025-09-01 19:22:16
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Properties;

/**
 * SystemPerformanceMonitor is a utility class that provides system performance monitoring features.
 * It uses Hibernate to interact with the database, where performance data is stored and retrieved.
 */
public class SystemPerformanceMonitor {

    private static SessionFactory sessionFactory;

    static {
        try {
            // Configure Hibernate properties
            Properties properties = new Properties();
            properties.setProperty(