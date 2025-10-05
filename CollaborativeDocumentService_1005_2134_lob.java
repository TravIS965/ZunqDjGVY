// 代码生成时间: 2025-10-05 21:34:57
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Properties;

// 定义文档协作平台服务类
public class CollaborativeDocumentService {
    // Hibernate核心配置文件路径
    private static final String HIBERNATE_CONFIG = "hibernate.cfg.xml";

    // Hibernate 配置属性
    private static final Properties HIBERNATE_PROPERTIES = new Properties();

    static {
        HIBERNATE_PROPERTIES.setProperty("hibernate.hbm2ddl.auto", "update");
        HIBERNATE_PROPERTIES.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        HIBERNATE_PROPERTIES.setProperty("hibernate.show_sql", "true");
    }

    // Hibernate 工厂对象
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // 创建服务注册对象
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure(HIBERNATE_CONFIG)
                    .applySettings(HIBERNATE_PROPERTIES)
                    .build();
            
            // 根据服务注册对象创建会话工厂
            return new Configuration().configure(serviceRegistry).buildSessionFactory();
        } catch (Throwable ex) {
            // 异常处理
            System.err.println("Initial SessionFactory creation failed." + "Exception: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // 获取Session对象
    public static Session getSession() throws Exception {
        return sessionFactory.openSession();
    }

    // 关闭SessionFactory
    public static void shutdown() {
        getSessionFactory().close();
    }

    // 获取SessionFactory对象
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // 添加文档方法
    public void addDocument(Document document) {
        Session session = null;
        Transaction transaction = null;
        try {
            // 开启Session和事务
            session = getSession();
            transaction = session.beginTransaction();

            // 添加文档
            session.save(document);
            transaction.commit();
        } catch (Exception e) {
            // 异常处理
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    // 更新文档方法
    public void updateDocument(Document document) {
        Session session = null;
        Transaction transaction = null;
        try {
            // 开启Session和事务
            session = getSession();
            transaction = session.beginTransaction();

            // 更新文档
            session.update(document);
            transaction.commit();
        } catch (Exception e) {
            // 异常处理
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    // 删除文档方法
    public void deleteDocument(Document document) {
        Session session = null;
        Transaction transaction = null;
        try {
            // 开启Session和事务
            session = getSession();
            transaction = session.beginTransaction();

            // 删除文档
            session.delete(document);
            transaction.commit();
        } catch (Exception e) {
            // 异常处理
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    // 查询文档方法
    public Document getDocumentById(int id) {
        Session session = null;
        try {
            // 开启Session
            session = getSession();

            // 根据ID查询文档
            return (Document) session.get(Document.class, id);
        } finally {
            if (session != null) session.close();
        }
    }

    // 查询所有文档方法
    public List<Document> getAllDocuments() {
        Session session = null;
        try {
            // 开启Session
            session = getSession();

            // 查询所有文档
            Query query = session.createQuery("from Document");
            return query.list();
        } finally {
            if (session != null) session.close();
        }
    }
}