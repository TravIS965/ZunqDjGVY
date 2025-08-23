// 代码生成时间: 2025-08-23 09:36:24
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Properties;

public class DatabaseConnectionPoolManager {

    // 定义数据库连接池配置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Hibernate配置文件路径
    private static final String HIBERNATE_CFG_XML = "hibernate.cfg.xml";

    // 使用ThreadLocal来保证每个线程获取自己的Session
    private static ThreadLocal<SessionFactory> sessionFactoryThreadLocal = new ThreadLocal<>();

    /**
     * 获取SessionFactory实例
     * @return SessionFactory实例
     */
    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = sessionFactoryThreadLocal.get();

        if (sessionFactory == null) {
            try {
                // 构建ServiceRegistry
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .configure(HIBERNATE_CFG_XML)
                        .applySettings(getProperties())
                        .build();

                // 通过ServiceRegistry建立SessionFactory
                sessionFactory = new Configuration().configure(HIBERNATE_CFG_XML).buildSessionFactory(serviceRegistry);
                sessionFactoryThreadLocal.set(sessionFactory);
            } catch (Throwable ex) {
                // 记录日志
                System.err.println("Initial SessionFactory creation failed." + "Exception: " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }

        return sessionFactory;
    }

    /**
     * 获取数据库连接属性
     * @return Properties对象
     */
    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.connection.url", DB_URL);
        properties.put("hibernate.connection.username", DB_USER);
        properties.put("hibernate.connection.password", DB_PASSWORD);
        properties.put("hibernate.connection.driver_class", DB_DRIVER);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        return properties;
    }
}