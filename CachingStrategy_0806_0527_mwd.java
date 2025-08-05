// 代码生成时间: 2025-08-06 05:27:51
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Properties;

public class CachingStrategy {
    
    // 创建SessionFactory实例，用于管理Hibernate缓存
    private static SessionFactory sessionFactory;
    
    // 静态代码块，用于初始化Hibernate配置和SessionFactory
    static {
        try {
            // 配置Hibernate
            Configuration configuration = new Configuration().configure();
            
            // 设置Hibernate属性
            Properties settings = new Properties();
            settings.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
            settings.setProperty("hibernate.connection.url", "jdbc:h2:~/test");
            settings.setProperty("hibernate.connection.username", "sa");
            settings.setProperty("hibernate.connection.password", "");
            settings.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            settings.setProperty("hibernate.show_sql", "true");
            settings.setProperty("hibernate.hbm2ddl.auto", "update");
            settings.setProperty("hibernate.cache.use_second_level_cache", "true");
            settings.setProperty("hibernate.cache.use_query_cache", "true");
            settings.setProperty("hibernate.generate_statistics", "true");
            
            // 构建ServiceRegistry
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();
            
            // 构建SessionFactory
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            // 处理Hibernate初始化错误
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    // 获取SessionFactory实例
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    // 获取Session实例
    public static Session openSession() {
        return sessionFactory.openSession();
    }
    
    // 主函数，用于演示缓存策略
    public static void main(String[] args) {
        System.out.println("CachingStrategy Demo Started.");
        
        // 演示使用缓存
        try (Session session = openSession()) {
            // 启用事务
            session.beginTransaction();
            
            // 查询数据并缓存结果
            // 假设QueryData是一个实体类，需要根据实际情况替换
            QueryData data = session.get(QueryData.class, 1L);
            System.out.println("Data Retrieved from DB: " + data);
            
            // 再次查询相同的数据，将从缓存中获取
            data = session.get(QueryData.class, 1L);
            System.out.println("Data Retrieved from Cache: " + data);
            
            // 提交事务
            session.getTransaction().commit();
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
        }
        
        System.out.println("CachingStrategy Demo Finished.");
    }
}