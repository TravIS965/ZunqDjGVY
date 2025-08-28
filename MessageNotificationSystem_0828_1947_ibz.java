// 代码生成时间: 2025-08-28 19:47:23
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.cfg.Environment;
import java.util.Properties;

// MessageNotificationSystem class
public class MessageNotificationSystem {
    
    // Hibernate Session Factory
    private static SessionFactory sessionFactory;
    
    // Static block to create SessionFactory
    static {
        try {
            // Create a SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, for simplicity, we are just printing it
            System.err.println("Initial SessionFactory creation failed." + "" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    // Method to add a message to the system
    public void addMessage(String message) {
        Session session = null;
        Transaction transaction = null;
        try {
            // Get current Hibernate session
            session = sessionFactory.getCurrentSession();
            
            // Begin transaction
            transaction = session.beginTransaction();
            
            // Save the message to the database
            session.save(message);
            
            // Commit transaction
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
    
    // Main method to test the MessageNotificationSystem
    public static void main(String[] args) {
        MessageNotificationSystem system = new MessageNotificationSystem();
        system.addMessage("Hello, this is a test message!");
    }
}
