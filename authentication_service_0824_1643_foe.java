// 代码生成时间: 2025-08-24 16:43:41
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Properties;

// 用户实体类
class User {
    private Long id;
    private String username;
    private String password;
    
    // 省略getter和setter方法
}

// 用户身份认证服务
public class AuthenticationService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    // 认证用户
    public boolean authenticate(String username, String password) {
        try {
            // 开启事务
            Transaction transaction = entityManager.unwrap(Session.class).beginTransaction();
            
            // 构建HQL查询语句
            String hql = "FROM User WHERE username = :username AND password = :password";
            Query query = entityManager.createQuery(hql, User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            
            // 执行查询
            List<User> users = query.getResultList();
            
            // 提交事务
            transaction.commit();
            
            // 如果用户列表不为空，则认证成功
            return !users.isEmpty();
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
            return false;
        } finally {
            // 关闭EntityManager
            entityManager.close();
        }
    }
}
