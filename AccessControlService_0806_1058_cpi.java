// 代码生成时间: 2025-08-06 10:58:17
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

// 定义用户类
class User {
    private int id;
    private String username;
    private String password;
    private String role;

    // 省略getter和setter方法
}

// 定义用户DAO类
class UserDAO {
    private SessionFactory sessionFactory;

    public UserDAO() {
        Properties properties = new Properties();
        properties.put(