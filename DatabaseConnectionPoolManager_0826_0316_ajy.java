// 代码生成时间: 2025-08-26 03:16:29
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Properties;

/**
 * DatabaseConnectionPoolManager is a class that manages a connection pool
 * using Hibernate framework. It provides methods to get a session and close the session.
 */
public class DatabaseConnectionPoolManager {

    private static SessionFactory sessionFactory;

    // Static block to initialize the sessionFactory at class loading time
    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml or another resource file
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Returns a new Session instance, which is a lightweight and clean Hierarchy of
     * Session objects.
     *
     * @return Session a new Session instance
     */
    public static Session getSessionFactoryInstance() {
        return sessionFactory.openSession();
    }

    /**
     * Closes the SessionFactory instance, effectively releasing resources.
     * This should be called when the application is shutting down.
     */
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    // Example usage of the DatabaseConnectionPoolManager
    public static void main(String[] args) {
        try {
            Session session = DatabaseConnectionPoolManager.getSessionFactoryInstance();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                // Your business logic here
                // ...

                // Commit the transaction
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
