// 代码生成时间: 2025-09-10 10:13:27
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class UserLoginSystem {

    // Method to authenticate a user
    public boolean authenticateUser(String username, String password) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        try (Session session = factory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                // HQL to find the user by username and password
                String hql = "FROM User WHERE username = :username AND password = :password";
                Query query = session.createQuery(hql);
                query.setParameter("username", username);
                query.setParameter("password", password);
                User user = (User) query.uniqueResult();

                if (user != null) {
                    transaction.commit();
                    return true;
                } else {
                    transaction.rollback();
                    return false;
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                return false;
            }
        } finally {
            factory.close();
        }
    }

    // Main method for demonstration
    public static void main(String[] args) {
        UserLoginSystem loginSystem = new UserLoginSystem();
        String username = "testUser";
        String password = "testPassword";
        boolean isAuthenticated = loginSystem.authenticateUser(username, password);
        if (isAuthenticated) {
            System.out.println("User authenticated successfully.");
        } else {
            System.out.println("Authentication failed.");
        }
    }
}

/**
 * User.java
 * 
 * Represents a user entity with username and password.
 */
public class User {
    private int id;
    private String username;
    private String password;

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
