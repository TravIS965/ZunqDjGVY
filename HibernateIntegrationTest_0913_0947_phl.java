// 代码生成时间: 2025-09-13 09:47:56
package com.example.hibernatetest;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Properties;

public class HibernateIntegrationTest {
    // 创建SessionFactory对象
    private static SessionFactory sessionFactory;
    
    // 初始化SessionFactory对象
    @BeforeEach
    public void setUp() {
        try {
            // 读取hibernate配置文件
            Configuration configuration = new Configuration().configure();
            
            // 设置数据库连接属性
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/your_database?useSSL=false");
            properties.setProperty("hibernate.connection.username", "your_username");
            properties.setProperty("hibernate.connection.password", "your_password");
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperties(properties);
            
            // 构建SessionFactory对象
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 关闭SessionFactory对象
    @AfterEach
    public void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
    
    // 测试保存数据
    @Test
    public void testSave() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            
            // 保存数据到数据库
            YourEntity entity = new YourEntity();
            entity.setSomeField("some_value");
            session.save(entity);
            
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 测试查询数据
    @Test
    public void testFind() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            
            // 查询数据库
            YourEntity entity = (YourEntity) session.get(YourEntity.class, 1L);
            if (entity != null) {
                System.out.println("Found entity: " + entity.getSomeField());
            } else {
                System.out.println("No entity found for id: 1");
            }
            
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 测试更新数据
    @Test
    public void testUpdate() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            
            // 更新数据库
            YourEntity entity = (YourEntity) session.get(YourEntity.class, 1L);
            if (entity != null) {
                entity.setSomeField("new_value");
                session.update(entity);
            }
            
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 测试删除数据
    @Test
    public void testDelete() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            
            // 删除数据库
            YourEntity entity = (YourEntity) session.get(YourEntity.class, 1L);
            if (entity != null) {
                session.delete(entity);
            }
            
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
