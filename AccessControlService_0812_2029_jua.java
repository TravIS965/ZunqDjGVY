// 代码生成时间: 2025-08-12 20:29:05
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

// 导入自定义实体类，假设有一个User实体类，包含用户信息和角色信息
import your.package.User;

public class AccessControlService {
    // 使用SessionFactory来管理Hibernate的配置和会话
    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    // 私有静态方法，用于创建SessionFactory
    private static SessionFactory buildSessionFactory() {
        try {
            // 从hibernate.cfg.xml配置文件加载配置
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // 日志记录异常信息
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    // 获取SessionFactory实例
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    // 检查用户是否具有指定权限的方法
    public boolean checkAccess(User user, String requiredRole) {
        if (user == null || requiredRole == null) {
            // 空值检查
            return false;
        }
        
        // 检查用户角色是否包含所需权限
        return user.getRoles().contains(requiredRole);
    }
    
    // 关闭SessionFactory，释放资源
    public static void shutdown() {
        getSessionFactory().close();
    }
    
    // 主方法，用于演示
    public static void main(String[] args) {
        // 创建用户和会话
        Session session = AccessControlService.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            // 假设User是一个实体类，包含用户信息和角色信息
            User user = new User();
            user.setUsername("testUser");
            user.setRoles(Collections.singletonList("admin"));
            
            // 检查用户是否具有admin权限
            boolean hasAccess = new AccessControlService().checkAccess(user, "admin");
            
            if (hasAccess) {
                System.out.println("User has access.");
            } else {
                System.out.println("User does not have access.");
            }
            
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}