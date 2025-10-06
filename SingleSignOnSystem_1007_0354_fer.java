// 代码生成时间: 2025-10-07 03:54:24
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.Properties;

// 单点登录系统的核心类
public class SingleSignOnSystem {
    // Hibernate Session Factory
    private static SessionFactory sessionFactory;

    // 静态初始化块，用于初始化SessionFactory
    static {
        try {
            // 从hibernate.cfg.xml文件加载配置并创建SessionFactory
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // 打印异常信息并退出程序
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // 获取SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // 登录方法
    public User login(String username, String password) {
        Session session = null;
        Transaction tx = null;
        try {
            // 获取Session
            session = getSessionFactory().openSession();
            tx = session.beginTransaction();
            // 查询用户
            User user = session.get(User.class, username);
            if (user == null || !user.getPassword().equals(password)) {
                throw new Exception("Invalid username or password."