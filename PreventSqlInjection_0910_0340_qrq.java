// 代码生成时间: 2025-09-10 03:40:58
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * 使用Hibernate和JDBC防止SQL注入
 */
public class PreventSqlInjection {
    private static final Logger logger = LoggerFactory.getLogger(PreventSqlInjection.class);
    private static final SessionFactory sessionFactory;

    static {
        // 配置Hibernate
        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/mydatabase");
        properties.put("hibernate.connection.username", "root");
        properties.put("hibernate.connection.password", "password");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");

        sessionFactory = new Configuration().configure().addProperties(properties).buildSessionFactory();
    }

    /**
     * 获取SessionFactory实例
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * 通过预编译的SQL查询防止SQL注入
     *
     * @param userId 要查询的用户的ID
     * @return 返回查询结果的用户列表
     */
    public List<User> findUsersByUserId(String userId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // 使用命名查询来防止SQL注入
            Query<User> query = session.getNamedQuery("User.findByUserId");
            query.setParameter("userId", userId);
            List<User> users = query.list();
            transaction.commit();
            return users;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error finding users by user ID", e);
        } finally {
            if (session != null) session.close();
        }
        return null;
    }

    public static void main(String[] args) {
        PreventSqlInjection dao = new PreventSqlInjection();
        try {
            List<User> users = dao.findUsersByUserId("123");
            // 打印查询结果
            for (User user : users) {
                System.out.println("User ID: " + user.getId() + ", Name: " + user.getName());
            }
        } catch (Exception e) {
            logger.error("Error running main method", e);
        }
    }
}

/**
 * 用户实体类
 */
class User {
    private Long id;
    private String name;
    private String userId;

    // 省略其他属性和方法

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

/**
 * Hibernate命名查询
 */
/*
 * <hibernate-mapping>
 *     <class name="User" table="users">
 *         <id name="id" type="long">
 *             <generator class="native"/>
 *         </id>
 *         <property name="name" type="string"/>
 *         <property name="userId" type="string"/>
 *     </class>
 * </hibernate-mapping>
 */

/*
 * <query name="User.findByUserId">
 *     select u from User u where u.userId = :userId
 * </query>
 */