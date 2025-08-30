// 代码生成时间: 2025-08-31 01:56:08
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class PreventSQLInjection {

    // Get Session Factory
    private static SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    // Method to demonstrate SQL injection prevention
    public static void preventSQLInjection() {
        SessionFactory factory = getSessionFactory();
        Session session = null;
        Transaction transaction = null;
        try {
            // Open a session
            session = factory.openSession();

            // Begin a transaction
            transaction = session.beginTransaction();

            // Use parameterized HQL to prevent SQL injection
            String hql = "FROM User WHERE username = :username AND password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("username", "exampleUser");
            query.setParameter("password", "examplePassword");

            // Execute the query and get the result
            List result = query.list();
            for (Object obj : result) {
                System.out.println(obj);
            }

            // Commit the transaction
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

    // User entity class for demonstration
    public static class User {
        private int id;
        private String username;
        private String password;

        // Constructors, getters and setters
        public User() {}

        public User(int id, String username, String password) {
            this.id = id;
            this.username = username;
            this.password = password;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static void main(String[] args) {
        preventSQLInjection();
    }
}
