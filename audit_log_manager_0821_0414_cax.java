// 代码生成时间: 2025-08-21 04:14:10
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.Interceptor;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;

import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Properties;

public class AuditLogManager {
    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;

    static {
        // Create the SessionFactory from hibernate.cfg.xml
        try {
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
           MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            sessionFactory = new SessionFactoryBuilder()
                .applySettings(configuration.getProperties())
                .build(metadataSources);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + "Exception: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Open a new Session
    public static Session openSession() throws Exception {
        if (sessionFactory == null) {
            rebuildSessionFactory();
        }
        session = sessionFactory.openSession();
        return session;
    }

    // Close the Session
    public static void closeSession() throws Exception {
        if (session != null) {
            session.close();
        }
    }

    // Rebuild SessionFactory if it doesn't currently exist or is closed
    public static void rebuildSessionFactory() throws Exception {
        // Make sure you clean up the old session factory!
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Exception in constructing SessionFactory" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /*
     * Create an audit log
     * @param action The action being audited
     * @param user The user performing the action
     * @param timestamp The time the action was performed
     */
    public static void createAuditLog(String action, String user, Timestamp timestamp) {
        try (Session session = openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                AuditLog auditLog = new AuditLog();
                auditLog.setAction(action);
                auditLog.setUser(user);
                auditLog.setTimestamp(timestamp);

                session.save(auditLog);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            } finally {
                closeSession();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // AuditLog entity class
    // This class should be annotated with JPA annotations and should correspond to the audit_log table in the database
    public static class AuditLog {

        @Id
        private Long id;
        private String action;
        private String user;
        private Timestamp timestamp;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }
        public Timestamp getTimestamp() { return timestamp; }
        public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
    }
}
