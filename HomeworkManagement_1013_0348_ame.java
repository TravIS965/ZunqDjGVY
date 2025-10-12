// 代码生成时间: 2025-10-13 03:48:24
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

// Represents a Homework entity
class Homework {
    private int id;
    private String title;
    private String description;
    private int courseID;
    
    // Constructors, getters and setters omitted for brevity
}

// HomeworkManager is a service class that manages homework operations
public class HomeworkManager {
    private static SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println(