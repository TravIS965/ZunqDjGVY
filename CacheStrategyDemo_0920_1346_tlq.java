// 代码生成时间: 2025-09-20 13:46:23
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.cache.spi.UpdateTimestampsCache;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;

import java.util.Properties;

public class CacheStrategyDemo {

    private static SessionFactory sessionFactory;

    // 初始化SessionFactory
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                final Configuration configuration = new Configuration().configure();
                // 创建服务注册表
                final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
                // 创建SessionFactory
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed." + "Exception: " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public static void main(String[] args) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            // 实体类（例如User）
            // User user = new User();
            // user.setUsername("user1");
            // user.setPassword("password1");
            // session.save(user);
            
            // 测试缓存策略
            // 假设已经有一个User实体，id为1
            // User user = session.get(User.class, 1);
            // System.out.println("Retrieved from DB: " + user.getUsername());
            
            // 模拟更新操作，触发缓存更新
            // user.setUsername("newUsername");
            // session.update(user);
            // transaction.commit();
            
            // 获取缓存统计信息
            Statistics stats = sessionFactory.getStatistics();
            SecondLevelCacheStatistics slcs = stats.getSecondLevelCacheStatistics("User");
            System.out.println("Second Level Cache Hit Count: " + slcs.getHitCount());
            System.out.println("Second Level Cache Miss Count: " + slcs.getMissCount());
            System.out.println("Second Level Cache Put Count: " + slcs.getPutCount());
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}