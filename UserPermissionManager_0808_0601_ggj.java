// 代码生成时间: 2025-08-08 06:01:49
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import java.util.Properties;

public class UserPermissionManager {
    // Hibernate utility for creating SessionFactory object
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    // Initialize the SessionFactory
    static {
        try {
            // Create the ServiceRegistry
            serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            // Create the SessionFactory
            sessionFactory = new Configuration().configure().buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            // The registry would be destroyed by the SessionFactory, but we had it already as a field
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
            System.err.println("Initial SessionFactory creation failed." + " Possible reason: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws Exception {
        return sessionFactory.openSession();
    }

    // Method to add a new user permission
    public static void addPermission(String userId, String permission) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Assuming a UserPermission class exists with userId and permission fields
            UserPermission userPermission = new UserPermission(userId, permission);
            session.save(userPermission);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Method to remove a user permission
    public static void removePermission(String userId, String permission) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Assuming a UserPermission class exists with userId and permission fields
            UserPermission userPermission = session.get(UserPermission.class, userId + "#" + permission);
            if (userPermission != null) {
                session.delete(userPermission);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Method to check if a user has a specific permission
    public static boolean hasPermission(String userId, String permission) {
        Transaction transaction = null;
        Session session = null;
        boolean hasPermission = false;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Assuming a UserPermission class exists with userId and permission fields
            UserPermission userPermission = session.get(UserPermission.class, userId + "#" + permission);
            hasPermission = userPermission != null;
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return hasPermission;
    }
}

/*
 * UserPermission.java
 * This class represents a user's permission.
 */
public class UserPermission {
    private String userId;
    private String permission;

    public UserPermission() {}

    public UserPermission(String userId, String permission) {
        this.userId = userId;
        this.permission = permission;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}