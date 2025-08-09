// 代码生成时间: 2025-08-09 13:12:12
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.UUID;

// Order entity class
class Order {
    private String orderId;
    private String customerName;
    private double amount;

    // Constructor, getters and setters
    public Order(String orderId, String customerName, double amount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.amount = amount;
    }

    // Getters and setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

// OrderService class for business logic
class OrderService {
    private SessionFactory sessionFactory;

    public OrderService() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Method to create a new order
    public String createOrder(String customerName, double amount) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String orderId = UUID.randomUUID().toString();
                Order order = new Order(orderId, customerName, amount);
                session.save(order);
                transaction.commit();
                return orderId;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println("Error creating order: " + e.getMessage());
                return null;
            }
        }
    }

    // Method to get all orders
    public List<Order> getAllOrders() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Order", Order.class).list();
        } catch (Exception e) {
            System.err.println("Error retrieving orders: " + e.getMessage());
            return null;
        }
    }

    // Close the session factory
    public void close() {
        sessionFactory.close();
    }
}

// Main class to run the program
public class OrderProcessing {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();

        try {
            // Create a new order
            String orderId = orderService.createOrder("John Doe", 100.00);
            if (orderId != null) {
                System.out.println("Order created with ID: " + orderId);
            }

            // Get all orders
            List<Order> orders = orderService.getAllOrders();
            if (orders != null) {
                for (Order order : orders) {
                    System.out.println("Order ID: " + order.getOrderId() + ", Customer: " + order.getCustomerName() + ", Amount: " + order.getAmount());
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing orders: " + e.getMessage());
        } finally {
            // Close the order service
            orderService.close();
        }
    }
}