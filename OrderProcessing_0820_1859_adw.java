// 代码生成时间: 2025-08-20 18:59:40
 * It demonstrates how to interact with the database to create, update, and retrieve order information.
 *
 * @author Your Name
 * @version 1.0
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class OrderProcessing {

    // Instance of the SessionFactory
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + "Exception: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void main(String[] args) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            // Example usage of the OrderProcessing class
            try {
                // Create a new order
                Order newOrder = new Order();
                newOrder.setOrderDate(new java.util.Date());
                newOrder.setStatus("Pending");
                newOrder.setTotalAmount(0.0);
                session.save(newOrder);

                // Update an existing order
                Order existingOrder = session.get(Order.class, 1L);
                existingOrder.setStatus("Shipped");
                session.update(existingOrder);

                // Retrieve order details
                List<Order> orders = session.createQuery("FROM Order", Order.class).list();
                for (Order order : orders) {
                    System.out.println("Order ID: " + order.getId() +
                            ", Status: " + order.getStatus() +
                            ", Total Amount: " + order.getTotalAmount());
                }

                transaction.commit();
            } catch (RuntimeException e) {
                transaction.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * Order.java
 *
 * Entity class representing an Order in the database.
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date orderDate;
    private String status;
    private Double totalAmount;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
