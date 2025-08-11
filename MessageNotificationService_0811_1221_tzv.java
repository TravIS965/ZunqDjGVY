// 代码生成时间: 2025-08-11 12:21:47
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MessageNotificationService {

    private SessionFactory sessionFactory;

    // Constructor to initialize SessionFactory
    public MessageNotificationService() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    /**
     * Sends a notification to all subscribers.
     * 
     * @param message The message to be sent to subscribers.
     */
    public void sendNotification(String message) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            
            // Obtain all subscribers
            Query query = session.createQuery("FROM Subscriber WHERE active = true", Subscriber.class);
            List<Subscriber> subscribers = query.getResultList();

            for (Subscriber subscriber : subscribers) {
                // Send message to subscriber
                subscriber.receiveMessage(message);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the SessionFactory when the service is no longer needed.
     */
    public void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

/**
 * Subscriber.java
 * 
 * Represents a subscriber to the message notification system.
 */
class Subscriber {
    private String name;
    private boolean active;
    
    // Constructor, getters, and setters
    public Subscriber(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Receives a message from the notification system.
     * 
     * @param message The message received.
     */
    public void receiveMessage(String message) {
        System.out.println("Received message for subscriber " + name + ": " + message);
    }
}
