// 代码生成时间: 2025-09-02 20:30:39
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.Properties;

// 用户实体类
class User {
    private int id;
    private String username;
    private String password;
    // Getters and Setters
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

// 用户认证服务
public class UserAuthenticationService {

    private SessionFactory sessionFactory;

    public UserAuthenticationService() {
        // 初始化Hibernate SessionFactory
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // 认证用户
    public boolean authenticateUser(String username, String password) {
        boolean authenticated = false;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // 查询用户
            User user = session.get(User.class, username);
            if (user != null && user.getPassword().equals(password)) {
                authenticated = true;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return authenticated;
    }

    // 关闭Session工厂
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
